#!/usr/bin/env bash
#
# Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

###############################
#        Configuration        #
###############################

# Path to your java 17 executable
JAVA_PATH="/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home/bin/java"
# Path to your java 8 or java 11 java executable
LEGACY_JAVA_PATH="/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin/java"

###############################
# Do not edit below this line #
###############################

#################################################################################################

readonly LEGACY_VERSIONS=(1.16.5 1.16.3 1.16.1)
readonly VERSIONS=(1.20.1 1.20 1.19.4 1.19.3 1.19.2 1.19.1 1.19 1.18.2 1.18.1 1.17.1)

readonly TRUE=0
readonly FALSE=1

readonly BT_URL="https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"

readonly NOT_AN_EXECUTABLE=-1
readonly FILE_NOT_FOUND=-2
readonly NO_JAVA_VERSION_REQUIREMENT=-3

readonly BT_RETURN_VALUE_FILE="buildtools_return_value.txt"
readonly BT_ERROR_FILE="$HOME/buildtools_error.txt"
readonly BT_OUTPUT_FILE="buildtools_output.txt"

BUILT_VERSIONS=()
ALREADY_BUILT_VERSIONS=()

#################################################################################################

function banner() {
  local width

  echo
  width=$(tput cols)
  if [[ $width -lt 60 ]]; then
    width=60
  fi
  echo "┌$(printf "%$((width - 2))s" | tr " " "-")┐"
  printf "| %-$((width - 4))s |\n" "$(date)"
  printf "| %-$((width - 4))s |\n" " "
  printf "|$(tput bold) %-$((width - 4))s $(tput sgr0)|\n" "$@"
  echo "└$(printf "%$((width - 2))s" | tr " " "-")┘"
  echo
}

function stringJoin() {
  local separator="$1"
  shift
  local first="$1"
  shift
  printf "%s" "$first" "${@/#/$separator}"
}

show_spinner() {
  local -r pid="${1}"
  shift
  local -r delay='0.25'
  local spinstr='\|/-'
  local temp
  local waitingForBt="$*"
  local waitingFotBtLength=${#waitingForBt}
  while ps a | awk '{print $1}' | grep -q "${pid}"; do
    temp="${spinstr#?}"
    printf " [%c]  $waitingForBt" "${spinstr}"
    spinstr=${temp}${spinstr%"${temp}"}
    sleep "${delay}"
    #printf "\b\b\b\b\b\b"
    for ((i = 0; i <= $((waitingFotBtLength + 6)); i++)); do
      printf "\b"
    done
  done
  printf "    \b\b\b\b"
}

function error {
  echo >&2 "Error: $*"
}

function cleanup {
  if [[ -d "$TEMP_FILE" ]]; then
    if [[ "$1" != "silent" ]]; then
      banner "Deleting temporary directory"
    fi
    if rm -rf "$TEMP_FILE"; then
      if [[ "$1" != "silent" ]]; then
        echo "Deleted temp file successfully"
      fi
    else
      error "Failed to delete temporary directory: $TEMP_FILE"
    fi
  fi
}

function error_exit {
  error "$*"
  cleanup
  exit 1
}

function runBuildTools {
  "$javaPath" -jar buildtools.jar --rev "$version" "$remappedParam"
  echo "$?" >$BT_RETURN_VALUE_FILE
}

function isInRepo {
  local version=$1
  local remapped=$2
  local classifier=""
  if [ "$remapped" == $TRUE ]; then
    classifier="-remapped-mojang"
  fi
  local file="$HOME/.m2/repository/org/spigotmc/spigot/${version}/spigot-${version}${classifier}.jar"
  #echo "Checking whether file exists: $file"
  if [[ -f "$file" ]]; then
    return $TRUE
  else
    return $FALSE
  fi
}

function buildVersion {
  local version=$1
  local javaPath=$2
  local remapped=$3
  local fullVersion=$version-R0.1-SNAPSHOT
  local classifier=""
  local versionWithClassifier=$version

  if [[ $remapped == "$TRUE" ]]; then
    #buildVersion "$1" "$2" $FALSE
    classifier=":remapped-mojang"
    versionWithClassifier="${version} (remapped)"
  fi

  banner "Building version ${fullVersion}${classifier}"

  if isInRepo "$fullVersion" "$remapped" == $TRUE; then
    echo "Skipped building version $fullVersion$classifier as it is already in your local repository"
    ALREADY_BUILT_VERSIONS+=("$versionWithClassifier")

        if [[ $remapped == "$TRUE" ]]; then
          buildVersion "$1" "$2" $FALSE
        fi

    return $TRUE
  fi

  local remappedParam=""
  if [[ $remapped == "$TRUE" ]]; then
    remappedParam="--remapped"
  fi

  local label="Building version $fullVersion$classifier ..."
  #echo "Debug: $version"
  (runBuildTools "$version" "$javaPath" "$remappedParam") >"$BT_OUTPUT_FILE" 2>&1 &
  show_spinner $! "$label"
  local BT_RETURN_VALUE
  BT_RETURN_VALUE=$(cat $BT_RETURN_VALUE_FILE)
  rm "$BT_RETURN_VALUE_FILE"
  if [[ $BT_RETURN_VALUE -eq 0 ]]; then
    echo "Successfully built version $fullVersion$classifier"
  else
    error "Failed to build version $fullVersion$classifier. See below for the full error message. The error message will also be saved to $BT_ERROR_FILE"
    echo
    cat "$BT_OUTPUT_FILE"
    cp "$BT_OUTPUT_FILE" "$BT_ERROR_FILE"
    rm "$BT_OUTPUT_FILE"
    echo
    banner "Failed!"
    cleanup silent
    error_exit "Failed to build version $fullVersion$classifier: Java exited with return code $BT_RETURN_VALUE. See above for the full error message. The error message has also been saved to $BT_ERROR_FILE"
  fi
  BUILT_VERSIONS+=("$versionWithClassifier")
  return $TRUE
}

function commandExists() {
  if ! command -v "$1" &>/dev/null; then
    return $FALSE
  else
    return $TRUE
  fi
}

function exitWhenCommandNotExists() {
  if ! commandExists "$1"; then
    echo "$1 could not be found"
    exit
  fi
}

function checkJavaExec() {
  local varName
  varName=$1
  echo "Please adjust the variable \"$varName\" at the top of this script to point to your java executable."
}

function getJavaVersion() {
  local javaPath
  javaPath=$1
  if [[ ! -f "$javaPath" ]]; then
    echo $FILE_NOT_FOUND
  fi
  if [[ ! -x "$javaPath" ]]; then
    echo $NOT_AN_EXECUTABLE
  fi
  "$javaPath" -version 2>&1 | sed -n ';s/.* version "\(.*\)\.\(.*\)\..*".*/\1\2/p;'
}

function checkSelf() {
  local javaVersion
  local legacyJavaVersion

  javaVersion=$(getJavaVersion "$JAVA_PATH")
  legacyJavaVersion=$(getJavaVersion "$LEGACY_JAVA_PATH")

  echo "Java version: $javaVersion"
  echo "Legacy Java version: $legacyJavaVersion"

  ## Java 17

  if [[ $javaVersion == "$FILE_NOT_FOUND" ]]; then
    error_exit "Could not find java executable at $JAVA_PATH. Please adjust the variable \"JAVA_PATH\" at the top of this script to point to your java 17 executable."
  fi

  if [[ $javaVersion == "$NOT_AN_EXECUTABLE" ]]; then
    error_exit "The java executable at $JAVA_PATH is not an executable. Please adjust the variable \"JAVA_PATH\" at the top of this script to point to your java 17 executable."
  fi



  ## Java Legacy

  if [[ $legacyJavaVersion == "$FILE_NOT_FOUND" ]]; then
    error_exit "Could not find java executable at $LEGACY_JAVA_PATH. Please adjust the variable \"LEGACY_JAVA_PATH\" at the top of this script to point to your java executable."
  fi

  if [[ $legacyJavaVersion == "$NOT_AN_EXECUTABLE" ]]; then
    error_exit "The java executable at $LEGACY_JAVA_PATH is not an executable. Please adjust the variable \"LEGACY_JAVA_PATH\" at the top of this script to point to your java executable."
  fi


  if (! commandExists curl) && (! commandExists wget); then
    error_exit "Neither curl nor wget could be found"
  fi

  exitWhenCommandNotExists mktemp
  exitWhenCommandNotExists cat
  exitWhenCommandNotExists rm
  exitWhenCommandNotExists cd
  exitWhenCommandNotExists echo
  exitWhenCommandNotExists trap
  exitWhenCommandNotExists date
  exitWhenCommandNotExists grep
  exitWhenCommandNotExists sed
  exitWhenCommandNotExists wc
  exitWhenCommandNotExists printf
  #exitWhenCommandNotExists testCommandLmaoTest

}

#################################################################################################

checkSelf

START_TIME=$(date +%s)

banner "Creating temporary directory"
TEMP_FILE=$(mktemp -d) || error_exit "Failed to create temporary directory"

echo "Temporary directory: $TEMP_FILE"
trap cleanup EXIT

cd "$TEMP_FILE" || error_exit "Could not change to temporary directory"

banner "Downloading latest BuildTools"
curl -o buildtools.jar $BT_URL || wget -O buildtools.jar $BT_URL || {
  error_exit "Could not download BuildTools!"
}

for version in "${VERSIONS[@]}"; do
  buildVersion ${version} "$JAVA_PATH" $TRUE
done

for version in "${LEGACY_VERSIONS[@]}"; do
  buildVersion ${version} "$LEGACY_JAVA_PATH" $FALSE
done

cleanup

banner "Summary"
END_TIME=$(date +%s)
RUNTIME=$((END_TIME - START_TIME))
NUMBER_OF_BUILT_VERSIONS=${#BUILT_VERSIONS[@]}
NUMBER_OF_ALREADY_BUILT_VERSIONS=${#ALREADY_BUILT_VERSIONS[@]}
echo "Finished successfully!"
echo
if [[ $NUMBER_OF_BUILT_VERSIONS -gt 0 ]]; then
  echo "Built versions ($NUMBER_OF_BUILT_VERSIONS): $(stringJoin ', ' "${BUILT_VERSIONS[@]}")"
fi
if [[ $NUMBER_OF_ALREADY_BUILT_VERSIONS -gt 0 ]]; then
  echo "Skipped already built versions ($NUMBER_OF_ALREADY_BUILT_VERSIONS): $(stringJoin ', ' "${ALREADY_BUILT_VERSIONS[@]}")"
fi
echo
echo "Total runtime: $RUNTIME seconds"

banner "Success!"

#################################################################################################

# Unused stuff

function byteCodeToJavaVersion() {
  case $1 in
  50) echo "6" ;;
  51) echo "7" ;;
  52) echo "8" ;;
  53) echo "9" ;;
  54) echo "10" ;;
  55) echo "11" ;;
  56) echo "12" ;;
  57) echo "13" ;;
  58) echo "14" ;;
  59) echo "15" ;;
  60) echo "16" ;;
  61) echo "17" ;;
  62) echo "18" ;;
  63) echo "19" ;;
  64) echo "20" ;;
  65) echo "21" ;;
  *) echo "Unknown" ;;
  esac
}

function getRequiredJavaVersions() {
  local spigotVersion
  local json
  local split
  local minJavaVersion
  local maxJavaVersion
  local javaVersionsLine
  local line
  spigotVersion="$1"
  json=$(curl -s "https://hub.spigotmc.org/versions/$spigotVersion.json")
  javaVersionsLine=$(echo "$json" | grep '"javaVersions":')
  if [[ $(echo "$javaVersionsLine" | wc -l) -eq 0 ]]; then
    echo "$NO_JAVA_VERSION_REQUIREMENT"
    return
  fi
  split=()
  while IFS='' read -r line; do split+=("$line"); done < <(echo "$javaVersionsLine" | grep -o "[[:digit:]]\{1,\}")
  minJavaVersion=$(byteCodeToJavaVersion "${split[0]}")
  maxJavaVersion=$(byteCodeToJavaVersion "${split[1]}")
  echo "$minJavaVersion-$maxJavaVersion"
}

function isJavaVersionInRange() {
  local javaVersion
  local minJavaVersion
  local maxJavaVersion
  javaVersion="$1"
  minJavaVersion="$2"
  maxJavaVersion="$3"
  if [[ $javaVersion -ge $minJavaVersion && $javaVersion -le $maxJavaVersion ]]; then
    echo "$TRUE"
  else
    echo "$FALSE"
  fi
}

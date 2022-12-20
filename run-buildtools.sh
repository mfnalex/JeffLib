#!/bin/bash

# Path to your java 17 executable
JAVA17_PATH="/c/Program Files/Java/jdk-17.0.1/bin/java.exe"
# Path to your java 8 or java 11 java executable
JAVA_PATH="/c/Program Files/Java/jdk1.8.0_271/bin/java.exe"

###############################
# Do not edit below this line #
###############################
BT_URL="https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"
readonly TRUE=0
readonly FALSE=1

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

function error {
  echo >&2 "Error: $*"
}

function cleanup {
  if [[ -d "$TEMP_FILE" ]]; then
    banner "Deleting temporary directory"
    if rm -rf "$TEMP_FILE"; then
      echo "Deleted temp file successfully"
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
  if [[ $remapped == "true" ]]; then
    classifier=":remapped-mojang"
  fi

  if isInRepo "$fullVersion" "$remapped" == $TRUE; then
    echo "Version $fullVersion$classifier is already in the repository"
    ALREADY_BUILT_VERSIONS+=("$version")
    return $TRUE
  fi

  local remappedParam=""
  if [[ $remapped == "$TRUE" ]]; then
    remappedParam="--remapped"
  fi

  echo "Building version $fullVersion$classifier ... (this might take a while)"
  echo "Debug: $version"
  "$javaPath" -jar buildtools.jar --rev "$version" $remappedParam || {
    cat buildtools.log
    rm buildtools.log
    error_exit "Failed to build version $fullVersion$classifier. See above for the BuildTools error message."
  }
  BUILT_VERSIONS+=("$version")
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

function checkSelf() {
  if [[ ! -f "$JAVA_PATH" ]]; then
    error_exit "Java 8 or 11 not found at $JAVA_PATH"
  fi

  if [[ ! -f "$JAVA17_PATH" ]]; then
    error_exit "Java 17 not found at $JAVA17_PATH"
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

for version in 1.19.3 1.19.2 1.19.1 1.19 1.18.2 1.18.1 1.17.1; do
  banner "Building version ${version}-R0.1-SNAPSHOT:remapped-mojang"
  buildVersion ${version} "$JAVA17_PATH" $TRUE
done

for version in 1.16.5 1.16.3 1.16.1; do
  banner "Building version ${version}-R0.1-SNAPSHOT"
  buildVersion ${version} "$JAVA_PATH" $FALSE
done

cleanup

banner "Summary"
END_TIME=$(date +%s)
RUNTIME=$((END_TIME-START_TIME))
NUMBER_OF_BUILT_VERSIONS=${#BUILT_VERSIONS[@]}
NUMBER_OF_ALREADY_BUILT_VERSIONS=${#ALREADY_BUILT_VERSIONS[@]}
echo "Finished successfully!"
echo
if [[ $NUMBER_OF_BUILT_VERSIONS -gt 0 ]]; then
  echo "Built versions ($NUMBER_OF_BUILT_VERSIONS): ${BUILT_VERSIONS[*]}"
fi
if [[ $NUMBER_OF_ALREADY_BUILT_VERSIONS -gt 0 ]]; then
  echo "Skipped already built versions ($NUMBER_OF_ALREADY_BUILT_VERSIONS): ${ALREADY_BUILT_VERSIONS[*]}"
fi
echo
echo "Total runtime: $RUNTIME seconds"

banner "Done!"
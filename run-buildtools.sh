#!/bin/bash

# Path to your java 17 executable
JAVA17_PATH=/opt/java/jdk17/bin/java
# Path to your java 8 or java 11 java executable
JAVA_PATH=/opt/java/jdk8/bin/java

###############################
# Do not edit below this line #
###############################
BT_URL="https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar2"

function error {
    >&2 echo "Error: $@"
}

function cleanup {
    rm -rf $TEMP_FILE
}

function error_exit {
    error "$@"
    cleanup
    exit 1
}


if ! TEMP_FILE=$(mktemp -d -t jefflib_buildtools_); then
    echo "Failed to create temporary directory: $TEMP_FILE"
    exit 1
fi

echo "Temporary directory: $TEMP_FILE"

trap cleanup EXIT

mkdir -p $TEMP_FILE || error_exit "Could not create temporary directory"
cd $TEMP_FILE || error_exit "Could not change to temporary directory"

curl -o buildtools.jar $BT_URL || wget -O buildtools.jar $BT_URL || {
  error_exit "Could not download BuildTools!"
}

for VERSION in 1.19.3 1.19.2 1.19.1 1.19 1.18.2 1.18.1 1.17.1; do
  $JAVA17_PATH -jar buildtools.jar --rev $VERSION --remapped
done

for VERSION in 1.16.5 1.16.3 1.16.1; do
  $JAVA_PATH -jar buildtools.jar --rev $VERSION --remapped
done
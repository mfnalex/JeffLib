#!/bin/bash
# Path to your java 17 executable
JAVA17_PATH=/opt/java/jdk17/bin/java
# Path to your java 8 or java 11 java executable
JAVA_PATH=/opt/java/jdk8/bin/java

BT_URL="https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"

if [ -f buildtools.jar ]; then
  rm "buildtools.jar"
fi

curl -o buildtools.jar $BT_URL || wget -O buildtools.jar $BT_URL || {
  1>&2 echo "Could not download BuildTools!"
  exit 1
}

for VERSION in 1.19.2 1.19.1 1.19 1.18.2 1.18.1 1.17.1; do
  $JAVA17_PATH -jar buildtools.jar --rev $VERSION --remapped
done

for VERSION in 1.16.5 1.16.3 1.16.1; do
  $JAVA_PATH -jar buildtools.jar --rev $VERSION --remapped
done
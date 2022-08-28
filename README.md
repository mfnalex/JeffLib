# JeffLib Spigot Library

<!--- Buttons start -->
<p align="center">
  <a href="https://hub.jeff-media.com/javadocs/jefflib/">
    <img src="https://static.jeff-media.com/img/button_javadocs.png?3" alt="Javadocs">
  </a>
  <a href="https://discord.jeff-media.com/">
    <img src="https://static.jeff-media.com/img/button_discord.png?3" alt="Discord">
  </a>
  <a href="https://paypal.me/mfnalex">
    <img src="https://static.jeff-media.com/img/button_donate.png?3" alt="Donate">
  </a>
</p>
<!--- Buttons end -->

<p align="center">
<a href="https://hub.jeff-media.com/nexus/#browse/browse:jeff-media-public:com%2Fjeff_media%2FJeffLib">
  <img src="https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fhub.jeff-media.com%2Fnexus%2Frepository%2Fjeff-media-public%2Fcom%2Fjeff_media%2FJeffLib%2Fmaven-metadata.xml" alt="Maven" /></a>
<img src="https://img.shields.io/github/last-commit/jeff-media-gbr/jefflib" />
</p>

JeffLib includes a ton of useful methods that you would normally have to write yourself. Check the Javadocs for an
overview.

## Maven

### Repository

```xml
<repository>
    <id>jeff-media-gbr</id>
    <url>https://hub.jeff-media.com/nexus/repository/jeff-media-public/</url>
</repository>
```

### Dependency

```xml
<dependency>
    <groupId>com.jeff_media</groupId>
    <artifactId>JeffLib</artifactId>
    <version><!-- Check latest verion in pom.xml --></version>
    <scope>compile</scope>
</dependency>
```

Latest version: ![Latest Version](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fhub.jeff-media.com%2Fnexus%2Frepository%2Fjeff-media-public%2Fcom%2Fjeff_media%2FJeffLib%2Fmaven-metadata.xml) 

### Relocation

```xml

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.3.0</version>
    <configuration>
        <minimizeJar>true</minimizeJar>
        <relocations>
            <relocation>
                <pattern>com.jeff_media.jefflib</pattern>
                <shadedPattern>YOUR.PACKAGE.jefflib</shadedPattern>
            </relocation>
        </relocations>
        <filters>
            <filter>
                <artifact>*:*</artifact>
                <excludeDefaults>false</excludeDefaults>
                <includes>
                    <include>com/jeff_media/jefflib/internal/nms/**</include>
                </includes>
            </filter>
        </filters>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Usage

Just include JeffLib as dependency in your pom.xml.

**Note:** Some methods require an instance of your plugin. JeffLib tries to get it automatically, however this only
works if your plugin has already been enabled. If you need to access methods that need an instance of your plugin (for
example PDCUtils), then please pass your plugin instance to JeffLib.init(Plugin) as soon as possible:

```java
public class MyPlugin extends JavaPlugin {
    {
        // Only needed if you access JeffLib before your onEnable() gets called
        JeffLib.init();
    }
}
```

**Note:** If you use methods annotated with "@RequiresNMS", you have to enable NMS support:

```java
public class MyPlugin extends JavaPlugin {
    {
        // Only needed if you use methods annotated with @RequiresNMS
        JeffLib.enableNMS();
    }
}
```

### Obfuscation settings

If you're using allatori to obfuscate your plugin, you need the following additional settings:

```xml
<ignore-classes>
  <class template="class **.nms.**" />
</ignore-classes>
```

### Building from source
To build JeffLib from source, you need the following Spigot versions installed in your local repository:

  - 1.19.2-R0.1-SNAPSHOT:remapped-mojang (Java 17+)
  - 1.18.2-R0.1-SNAPSHOT:remapped-mojang (Java 17+)
  - 1.18.1-R0.1-SNAPSHOT:remapped-mojang (Java 17+)
  - 1.17.1-R0.1-SNAPSHOT:remapped-mojang (Java 16+)
  - 1.16.5-R0.1-SNAPSHOT (Java 8 - Java 16)
  - 1.16.3-R0.1-SNAPSHOT (Java 8 - Java 16)
  - 1.16.1-R0.1-SNAPSHOT (Java 8 - Java 16)

You can use this tiny bash script to compile them all at once. Please adjust the JAVA17_PATH and JAVA_PATH.

```shell
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

for VERSION in 1.19.2 1.18.2 1.18.1 1.17.1; do
  $JAVA17_PATH -jar buildtools.jar --rev $VERSION --remapped
done

for VERSION in 1.16.5 1.16.3 1.16.1; do
  $JAVA_PATH -jar buildtools.jar --rev $VERSION --remapped
done
```

## JavaDocs

https://hub.jeff-media.com/javadocs/jefflib/

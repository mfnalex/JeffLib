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


[//]: # (<p align="center">)

[//]: # (<a href="https://hub.jeff-media.com/nexus/#browse/browse:jeff-media-public:com%2Fjeff_media%2FJeffLib">)

[//]: # (  <img src="https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fhub.jeff-media.com%2Fnexus%2Frepository%2Fjeff-media-public%2Fcom%2Fjeff_media%2FJeffLib%2Fmaven-metadata.xml" alt="Maven" /></a>)

[//]: # (<img src="https://img.shields.io/github/last-commit/jeff-media-gbr/jefflib" />)

[//]: # (</p>)

JeffLib includes a ton of useful methods that you would normally have to write yourself. Check the Javadocs for an
overview.

## Dependency Information

### Maven

Repository:

```xml
<repository>
    <id>jeff-media-gbr</id>
    <url>https://hub.jeff-media.com/nexus/repository/jeff-media-public/</url>
</repository>
```

Dependency:

```xml
<dependency>
    <groupId>com.jeff-media.jefflib</groupId>
    <artifactId>jefflib</artifactId>
    <version><!-- changeme -->14.2.3</version>
    <scope>compile</scope>
</dependency>
```

Check the latest version in the [repository explorer](https://repo.jeff-media.com/#/public/com/jeff-media/jefflib/jefflib/).

Shading (add this to ```<build><plugins>```) if you don't already have it:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.5.0</version>
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

Optionally you can adjust your shade configuration to relocate JeffLib and to make the NMS classes work while using
the "minizeJar" option by adding this to your maven-shade-plugin's `<configuration>`:

```xml
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
```

### Gradle

Plugin:

```groovy
plugins {
    id 'java'
    id "com.github.johnrengelman.shadow" version "7.1.0"
}
```

Repository:

```groovy
repositories {
    maven {
        name 'jeff-media-gbr'
        url 'https://hub.jeff-media.com/nexus/repository/jeff-media-public/'
    }
}
```

Dependency:

```groovy
dependencies {
    implementation 'com.jeff-media.jefflib:jefflib:14.2.3' // Check the latest version in the repository explorer: https://repo.jeff-media.com/#/public/com/jeff-media/jefflib/jefflib/
} 
```

Shadow with re-location

```groovy
shadowJar {
    relocate 'com.jeff_media.jefflib', 'YOUR.PACKAGE.jefflib'
    minimize() {
        exclude("com/jeff_media/jefflib/internal/nms/**")
    }
}
```

You also need an additional third-party plugin to shade JeffLib into your .jar as Gradle doesn't have any official
plugin for this. You'll furthermore want to tell your shading plugin to minimize your .jar after that to remove unused
classes from JeffLib, except for the classes in `com.jeff_media.jefflib.internal.nms`, and to relocate those classes to
your own package name. I have no clue how to make the Gradle Shadow plugin be able to both, relocate classes, while also
using filters in the minimize() block, so you'll have to figure this out yourself, or simply use Maven instead (see
above).

## Usage

**Note:** Some methods require an instance of your plugin. JeffLib tries to get it automatically, however this only
works if your plugin has already been enabled. If you need to access methods that need an instance of your plugin (for
example PDCUtils) before it got enabled, then please pass your plugin instance to JeffLib.init(Plugin) as soon as
possible:

```java
public class MyPlugin extends JavaPlugin {
    {
        // Only needed if you access JeffLib before your onEnable() gets called
        JeffLib.init();
    }
}
```

**Note:** If you use methods annotated with "@RequiresNMS", you also have to enable NMS support:

```java
public class MyPlugin extends JavaPlugin {
    {
        // Only needed if you use methods annotated with @RequiresNMS
        JeffLib.enableNMS();
    }
}
```

## Building from source

To build JeffLib from source, just clone this repo, then run the `run-buildtools.sh`file, which will automatically build
all needed Spigot versions that you don't already have in your local repo. After that, you can build JeffLib by running
`mvn clean install` in the root directory of this repo. The final .jar will be in the `dist/target` folder.

```shell
git clone https://github.com/JEFF-Media-GbR/JeffLib/
cd JeffLib
bash run-buildtools.sh
mvn clean install
```

**Note**: On Windows, you need to use "git bash" or similar to run the `run-buildtools.sh` script. If it complains about
missing java versions, edit the script and change the `JAVA_VERSION` and `LEGACY_JAVA_VERSION` variables to point at
your
java 17 and java 8/11 installations.

## JavaDocs

https://hub.jeff-media.com/javadocs/jefflib/

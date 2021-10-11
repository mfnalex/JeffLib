# JeffLib Spigot Library

JeffLib includes a ton of useful methods that you would normally have to write yourself.

## Usage

Just include JeffLib as dependency in your pom.xml.

**Note:** JeffLib has to be initialized for some methods to work. Just add this to your plugin's `onLoad()` method:

```java
JeffLib.init(this);
```

## Maven

### Repository

```xml
<repository>
    <id>jeff-media-gbr</id>
    <url>https://repo.jeff-media.de/maven2</url>
</repository>
```

### Dependency

```xml
<dependency>
    <groupId>de.jeff_media</groupId>
    <artifactId>JeffLib</artifactId>
    <version>6.1.0-SNAPSHOT</version> <!-- The version will only change when there are breaking changes -->
    <scope>compile</scope>
</dependency>
```

### Relocation

```xml

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <relocations>
            <relocation>
                <pattern>de.jeff_media.jefflib</pattern>
                <shadedPattern>YOUR.PACKAGE.jefflib</shadedPattern>
            </relocation>
        </relocations>
    </configuration>
</plugin>
```

## JavaDocs

Coming soon

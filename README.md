# JeffLib Spigot Library

JeffLib includes a ton of useful methods that you would normally have to write yourself.

## Usage

Just include JeffLib as dependency in your pom.xml.

**Note:** JeffLib has to be initialized for some methods to work. The best way to do this is in your plugin's Instance Initialization Block:

```java
public class MyPlugin extends JavaPlugin {
    {
        JeffLib.init(this);
    }
}
```

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
    <groupId>de.jeff_media</groupId>
    <artifactId>JeffLib</artifactId>
    <version><!-- Check latest verion in pom.xml --></version>
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

https://hub.jeff-media.com/javadocs/jefflib/

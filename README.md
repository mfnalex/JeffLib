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

JeffLib includes a ton of useful methods that you would normally have to write yourself.

## Usage

Just include JeffLib as dependency in your pom.xml.

**Note:** If you use methods annotated with "@RequiresNMS", you have to enable NMS support:

```java
public class MyPlugin extends JavaPlugin {
    {
        JeffLib.enableNMS();
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
    <version>3.3.0</version>
    <configuration>
        <minimizeJar>true</minimizeJar>
        <relocations>
            <relocation>
                <pattern>de.jeff_media.jefflib</pattern>
                <shadedPattern>YOUR.PACKAGE</shadedPattern>
            </relocation>
        </relocations>
        <filters>
            <filter>
                <artifact>*:*</artifact>
                <excludeDefaults>false</excludeDefaults>
                <includes>
                    <include>de/jeff_media/jefflib/internal/nms/**</include>
                </includes>
            </filter>
            <filter>
                <artifact>*:*</artifact>
                <excludes>
                    <exclude>META-INF/**</exclude>
                </excludes>
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

### Obfuscation settings

If you're using allatori to obfuscate your plugin, you need the following additional settings:

```xml
<ignore-classes>
  <class template="class **.nms.**" />
</ignore-classes>
```

## How to build yourself

Coming soon

## JavaDocs

https://hub.jeff-media.com/javadocs/jefflib/

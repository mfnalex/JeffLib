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
                <shadedPattern>YOUR.PACKAGE</shadedPattern>
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

## JavaDocs

https://hub.jeff-media.com/javadocs/jefflib/

JeffLib includes a ton of useful methods that you would normally have to write yourself, as well as already
shaded and relocated other libraries like

- my UpdateChecker
- my CustomBlockData library
- PaperLib
- Google Gson
- Apache Commons Lang3

## Usage
Just include JeffLib as dependency in your pom.xml.

**Note:** JeffLib has to be initialized for some methods to work.
Just add this to your plugin's `onLoad()` method:

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
    <version>3.0.0-SNAPSHOT</version> <!-- The version will only change when there are breaking changes -->
    <scope>compile</scope>
</dependency>
```

## JavaDocs

Coming soon
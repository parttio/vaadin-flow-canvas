# Canvas Component for Vaadin 10+

This is a Java integration of the HTML5 `<canvas>` for Vaadin.

Currently, this add-on provides a subset of the [client-side JavaScript API](https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API) to Java users.

## Usage Example

Add the dependency to your Vaadin 10+ project's `pom.xml`:
```xml
<dependency>
    <groupId>org.parttio</groupId>
    <artifactId>canvas-java</artifactId>
    <version>2.0.0</version>
</dependency>
```

The API is similar to its client-side counterpart:
```java
Canvas canvas = new Canvas(800, 500);
CanvasRenderingContext2D ctx = canvas.getContext();

// Draw a red line from point (10,10) to (100,100):
ctx.setStrokeStyle("red");
ctx.beginPath();
ctx.moveTo(10, 10);
ctx.lineTo(100, 100);
ctx.closePath();
ctx.stroke();
```

More examples can be found in the [demo sources](https://github.com/parttio/vaadin-flow-canvas/blob/master/src/test/java/org/vaadin/pekkam/DemoView.java).

## Development Instructions

Starting the test/demo server:
```
mvn jetty:run
```

This deploys the demo at `http://localhost:8080`

## Cutting new release

Before cutting a release, make sure the build passes properly locally and in GitHub Actions based verification build.

To tag a release and increment versions, go the line-awesome subdirectory and issue:

    mvn release:prepare release:clean

Answer questions, defaults most often fine. Note that release:perform is not needed as there is a GitHub Action is set up build and to push release to Maven Central automatically.

Directory will automatically pick up new releases within about half an hour, but if browser or Vaadin version support change, be sure to adjust the metadata in Vaadin Directory UI.

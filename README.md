# Canvas Component for Vaadin 10+

This is a Java integration of the HTML5 `<canvas>` for Vaadin Platform.

Currently this add-on provides a subset of the [client-side JavaScript API](https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API) to Java users.

## Usage Example

Add the dependency to your Vaadin 10+ project's `pom.xml`:
```xml
<dependency>
    <groupId>org.vaadin.pekkam</groupId>
    <artifactId>canvas-java</artifactId>
    <version>1.0.0</version>
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

More examples can be found in the [demo sources](https://github.com/pekam/vaadin-flow-canvas/blob/master/src/test/java/org/vaadin/pekkam/DemoView.java).

## Development Instructions

Starting the test/demo server:
```
mvn jetty:run
```

This deploys the demo at `http://localhost:8080`

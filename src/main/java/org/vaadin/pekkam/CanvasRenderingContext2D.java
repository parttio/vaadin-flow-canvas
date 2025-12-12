package org.vaadin.pekkam;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The context for rendering shapes and images on a canvas.
 * <p>
 * This is a Java wrapper for the <a href=
 * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D">same
 * client-side API</a>.
 */
public class CanvasRenderingContext2D {

    private final Canvas canvas;

    protected CanvasRenderingContext2D(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setFillStyle(String fillStyle) {
        setProperty("fillStyle", fillStyle);
    }

    /**
     * Set a pattern to use as a fill style. Must reference an image source previously loaded
     * with Canvas.loadImage().
     *
     * @param src  the path to the image resource
     * @param type the pattern repeat type (see the Canvas API)
     */
    public void setPatternFillStyle(String src, String type) {
        runScript(
                """
                        if ($0.images) {
                           var img = $0.images['%s'];
                           if (img) {
                             var ctx = $0.getContext('2d');
                             var pat = ctx.createPattern(img, '%s');
                             ctx.fillStyle = pat;
                           }
                        }""".formatted(src, type));
    }

    public void setStrokeStyle(String strokeStyle) {
        setProperty("strokeStyle", strokeStyle);
    }

    /**
     * Set a pattern to use as a strok -style. Must reference an image source previously loaded
     * with Canvas.loadImage().
     *
     * @param src  the path to the image resource
     * @param type the pattern repeat type (see the Canvas API)
     */
    public void setPatternStrokeStyle(String src, String type) {
        runScript("""
                 if ($0.images) {
                   var img = $0.images['%s'];
                   if (img) {
                     var ctx = $0.getContext('2d');
                     var pat = ctx.createPattern(img, '%s');
                     ctx.strokeStyle = pat;
                   }
                 }
                """.formatted(src, type));
    }

    public void setLineWidth(double lineWidth) {
        setProperty("lineWidth", lineWidth);
    }

    public void setFont(String font) {
        setProperty("font", font);
    }

    public void arc(double x, double y, double radius, double startAngle,
                    double endAngle, boolean antiClockwise) {
        callJsMethod("arc", x, y, radius, startAngle, endAngle, antiClockwise);
    }

    /**
     * <p>The <code>arcTo()</code> method adds an arc/curve between two tangents to the path.</p>
     * <p>Use the {@link CanvasRenderingContext2D#stroke()} or {@link CanvasRenderingContext2D#fill()} method to draw the path.</p>
     *
     * @param x1     The x-coordinate of the beginning of the arc
     * @param y1     The y-coordinate of the beginning of the arc
     * @param x2     The x-coordinate of the end of the arc
     * @param y2     The y-coordinate of the end of the arc
     * @param radius The radius of the arc
     * @see <a href="https://www.w3schools.com/tags/canvas_arcto.asp">Canvas arcTo() Method</a>
     */
    public void arcTo(double x1, double y1, double x2, double y2, double radius) {
        callJsMethod("arcTo", x1, y1, x2, y2, radius);
    }

    public void beginPath() {
        callJsMethod("beginPath");
    }

    /**
     * <p>The bezierCurveTo() method adds a curve to the path by using the control points that represent a cubic Bézier curve.</p>
     * <p>Use the {@link CanvasRenderingContext2D#stroke()} or {@link CanvasRenderingContext2D#fill()} method to draw the path.</p>
     * <p>A cubic bezier curve requires three points. The first two points are control points that are used in the cubic
     * Bézier calculation and the last point is the ending point for the curve.  The starting point for the curve is the
     * last point in the current path. If a path does not exist, use the {@link CanvasRenderingContext2D#beginPath()}
     * and {@link CanvasRenderingContext2D#moveTo(double, double)} methods to define a starting point.</p>
     *
     * @param cp1x The x-coordinate of the first Bézier control point
     * @param cp1y The y-coordinate of the first Bézier control point
     * @param cp2x The x-coordinate of the second Bézier control point
     * @param cp2y The y-coordinate of the second Bézier control point
     * @param x    The x-coordinate of the ending point
     * @param y    The y-coordinate of the ending point
     * @see <a href="https://www.w3schools.com/tags/canvas_beziercurveto.asp">Canvas bezierCurveTo() Method</a>
     */
    public void bezierCurveTo(double cp1x, double cp1y, double cp2x, double cp2y, double x, double y) {
        callJsMethod("bezierCurveTo", cp1x, cp1y, cp2x, cp2y, x, y);
    }

    public void clearRect(double x, double y, double width, double height) {
        callJsMethod("clearRect", x, y, width, height);
    }

    public void closePath() {
        callJsMethod("closePath");
    }

    public void ellipse(double x, double y, double radiusX, double radiusY, double rotation, double startAngle, double endAngle) {
        callJsMethod("ellipse", x, y, radiusX, radiusY, rotation, startAngle, endAngle, true);
    }

    public void ellipse(double x, double y, double radiusX, double radiusY, double rotation, double startAngle, double endAngle, boolean clockwise) {
        callJsMethod("ellipse", x, y, radiusX, radiusY, rotation, startAngle, endAngle, clockwise);
    }

    /**
     * Fetches the image from the given location and draws it on the canvas.
     * <p>
     * <b>NOTE:</b> Unless you have previously loaded the image with Canvas.loadImage, the
     * drawing will happen asynchronously after the browser has retrieved the image.
     *
     * @param src the url of the image to draw
     * @param x   the x-coordinate of the top-left corner of the image
     * @param y   the y-coordinate of the top-left corner of the image
     */
    public void drawImage(String src, double x, double y) {
        runScript("""
                var img = null;
                if ($0.images) img = $0.images['%s'];
                if (img != null)
                  $0.getContext('2d').drawImage(img, %s, %s);
                else {
                  img = new Image();
                  img.onload = function () {
                    $0.getContext('2d').drawImage(img, %s, %s);
                  };
                  img.src='%s';
                }""".formatted(src, x, y, x, y, src));
    }

    /**
     * Fetches the image from the given location and draws it on the canvas.
     * <p>
     * <b>NOTE:</b> Unless you have previously loaded the image with Canvas.loadImage, the
     * drawing will happen asynchronously after the browser has retrieved the image.
     *
     * @param src    the url of the image to draw
     * @param x      the x-coordinate of the top-left corner of the image
     * @param y      the y-coordinate of the top-left corner of the image
     * @param width  the width for the image
     * @param height the height for the image
     */
    public void drawImage(String src, double x, double y, double width,
                          double height) {
        runScript("""
                var img = null;
                if ($0.images) img = $0.images['%s'];
                if (img != null)
                  $0.getContext('2d').drawImage(img, %s, %s, %s, %s);
                else {
                  img = new Image();
                  img.onload = function () {
                    $0.getContext('2d').drawImage(img, %s, %s, %s, %s);
                  };
                  img.src='%s';
                }
                """.formatted(src, x, y, width, height, x, y, width, height, src));
    }

    public void fill() {
        callJsMethod("fill");
    }

    public void fillRect(double x, double y, double width, double height) {
        callJsMethod("fillRect", x, y, width, height);
    }

    public void fillText(String text, double x, double y) {
        callJsMethod("fillText", text, x, y);
    }

    public void lineTo(double x, double y) {
        callJsMethod("lineTo", x, y);
    }

    public void moveTo(double x, double y) {
        callJsMethod("moveTo", x, y);
    }

    /**
     * <p>The <code>quadraticCurveTo()</code> method adds a curve to the current path by using the control points that
     * represent a quadratic Bézier curve.</p>
     * <p>Use the {@link CanvasRenderingContext2D#stroke()} or {@link CanvasRenderingContext2D#fill()} method to draw the path.</p>
     * <p>A quadratic Bézier curve requires two points. The first point is a control point that is used in the quadratic
     * Bézier calculation and the second point is the ending point for the curve. The starting point for the curve is the
     * last point in the current path. If a path does not exist, use the {@link CanvasRenderingContext2D#beginPath()}
     * and {@link CanvasRenderingContext2D#moveTo(double, double)} methods to define a starting point.</p>
     *
     * @param cpx The x-coordinate of the Bézier control point
     * @param cpy The y-coordinate of the Bézier control point
     * @param x   The x-coordinate of the ending point
     * @param y   The y-coordinate of the ending point
     * @see <a href="https://www.w3schools.com/tags/canvas_quadraticcurveto.asp">Canvas quadraticCurveTo() Method</a>
     */
    public void quadraticCurveTo(double cpx, double cpy, double x, double y) {
        callJsMethod("quadraticCurveTo", cpx, cpy, x, y);
    }

    public void rect(double x, double y, double width, double height) {
        callJsMethod("rect", x, y, width, height);
    }

    public void restore() {
        callJsMethod("restore");
    }

    public void rotate(double angle) {
        callJsMethod("rotate", angle);
    }

    public void save() {
        callJsMethod("save");
    }

    public void scale(double x, double y) {
        callJsMethod("scale", x, y);
    }

    public void setLineDash(double... segments) {
        var sequence = Arrays.stream(segments).mapToObj(Double::toString).collect(Collectors.joining(","));
        runScript("var ctx = $0.getContext('2d'); var pat = ctx.setLineDash([%s]);".formatted(sequence));
    }

    public void stroke() {
        callJsMethod("stroke");
    }

    public void strokeRect(double x, double y, double width, double height) {
        callJsMethod("strokeRect", x, y, width, height);
    }

    public void strokeText(String text, double x, double y) {
        callJsMethod("strokeText", text, x, y);
    }

    public void translate(double x, double y) {
        callJsMethod("translate", x, y);
    }

    public void clip() {
        this.callJsMethod("clip");
    }

    protected void setProperty(String propertyName, Serializable value) {
        runScript("$0.getContext('2d').%s='%s'".formatted(propertyName,
                value));
    }

    /**
     * Runs the given js so that the execution order works with callJsMethod().
     * Any $0 in the script will refer to the canvas element.
     */
    private void runScript(String script) {
        canvas.getElement().getNode().runWhenAttached(
                // This structure is needed to make the execution order work
                // with Element.callFunction() which is used in callJsMethod()
                ui -> ui.getInternals().getStateTree().beforeClientResponse(
                        canvas.getElement().getNode(),
                        context -> ui.getPage().executeJs(script,
                                canvas.getElement())));
    }

    protected void callJsMethod(String methodName, Serializable... parameters) {
        canvas.getElement().callJsFunction("getContext('2d')." + methodName,
                parameters);
    }

}

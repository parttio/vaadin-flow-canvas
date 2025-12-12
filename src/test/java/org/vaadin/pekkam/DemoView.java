package org.vaadin.pekkam;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.Route;
import org.vaadin.pekkam.event.MouseEvent;

import java.awt.geom.Point2D;

@Route("")
public class DemoView extends Div {

    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 500;

    private final Canvas canvas;
    private final CanvasRenderingContext2D ctx;

    public DemoView() {
        Div label = new Div();
        label.setText("The quick brown fox.");

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.getStyle().set("border", "1px solid");

        ctx = canvas.getContext();

        Div buttons = new Div();
        buttons.add(new NativeButton("Draw random circle",
                e -> drawRandomCircle()));
        buttons.add(new NativeButton("Draw ellipse", e -> drawEllipse()));
        buttons.add(new NativeButton("Draw house", e -> drawHouse()));
        buttons.add(new NativeButton("Draw curves", e -> drawCurves()));
        buttons.add(new NativeButton("Clear canvas",
                e -> ctx.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT)));

        add(label, canvas, buttons);

        Input input = new Input();
        input.setValue("resources/vaadin-logo.svg");
        NativeButton loadImageButton = new NativeButton("Load image", e -> canvas.loadImage(input.getValue()));
        NativeButton drawImageButton = new NativeButton("Draw image",
                e -> ctx.drawImage(input.getValue(), 0, 0));
        NativeButton drawPatButton = new NativeButton("Fill pattern", e -> drawPattern(input.getValue()));
        NativeButton drawClipButton = new NativeButton("Clip image", e -> drawClip(input.getValue()));
        add(new Span("Image src: "), input, loadImageButton, drawImageButton, drawPatButton, drawClipButton);

        canvas.getElement().setAttribute("tabindex", "1");
        canvas.addMouseDownListener(e -> logEvent("down", e));
        canvas.addMouseUpListener(e -> logEvent("up", e));
        canvas.addMouseMoveListener(e -> logEvent("move", e));
        canvas.addMouseClickListener(e -> logEvent("click", e));
        canvas.addMouseDblClickListener(e -> logEvent("dblClick", e));
        canvas.addKeyDownListener(e -> {
            System.out.println("key :" + e.getKey().getKeys().get(0));
        });
        canvas.addImageLoadListener(e -> {
            System.out.println("image loaded: " + e.getSrc());
        });

        NativeButton showAsRasterImageBtn = new NativeButton("Show as raster image", event -> {
            canvas.toDataURL("image/png", 1.0).thenAccept(str -> {
                Image image = new Image();
                image.setSrc(str);
                add(image);
            });
        });

        add(showAsRasterImageBtn);

        NativeButton downloadButton = new NativeButton("Download as PNG");
        downloadButton.addClickListener(event -> {
            canvas.toImage("image/png", 1.0).thenAccept(bb -> {
                DynamicDownload.download(de -> {
                    de.setFileName("canvas.png");
                    de.getOutputStream().write(bb);
                });
            });
        });
        add(new Div(downloadButton));
    }

    private void logEvent(String eventType, MouseEvent me) {
        System.out.println("mouse " + eventType + ": x=" + me.getOffsetX() + ", y=" + me.getOffsetY() + ", btn=" + me.getButton());
    }

    private void drawPattern(String src) {
        ctx.save();
        ctx.setPatternFillStyle(src, "repeat");
        ctx.fillRect(200, 200, 100, 100);
        ctx.restore();
    }

    private void drawHouse() {
        ctx.save();

        ctx.setFillStyle("yellow");
        ctx.strokeRect(200, 200, 100, 100);
        ctx.fillRect(200, 200, 100, 100);

        ctx.beginPath();
        ctx.moveTo(180, 200);
        ctx.lineTo(250, 150);
        ctx.lineTo(320, 200);
        ctx.closePath();
        ctx.stroke();
        ctx.setFillStyle("orange");
        ctx.fill();

        ctx.restore();
    }

    private void drawRandomCircle() {
        ctx.save();
        ctx.setLineWidth(2);
        ctx.setFillStyle(getRandomColor());
        ctx.beginPath();
        ctx.arc(Math.random() * CANVAS_WIDTH, Math.random() * CANVAS_HEIGHT,
                10 + Math.random() * 90, 0, 2 * Math.PI, false);
        ctx.closePath();
        ctx.stroke();
        ctx.fill();
        ctx.restore();
    }

    private void drawClip(String src) {
        ctx.save();

        ctx.beginPath();

        ctx.setLineWidth(2);
        ctx.setStrokeStyle(getRandomColor());
        ctx.arc(450, 250, 45, 0, 2 * Math.PI, false);
        ctx.stroke();

        ctx.setFillStyle(getRandomColor());
        ctx.fill();

        ctx.clip();
        ctx.drawImage(src, 350, 233, 150, 34);

        ctx.restore();
    }

    private void drawCurves() {
        var p1 = new Point2D.Double(100, 50);
        var p2 = new Point2D.Double(150, 50);
        var p3 = new Point2D.Double(150, 100);
        var p4 = new Point2D.Double(150, 150);
        var p5 = new Point2D.Double(100, 150);
        var p6 = new Point2D.Double(50, 150);
        var p7 = new Point2D.Double(50, 100);
        var p8 = new Point2D.Double(50, 50);

        ctx.save();
        ctx.setLineWidth(1);

        ctx.beginPath();
        ctx.moveTo(p1.x, p1.y);
        ctx.arcTo(p2.x, p2.y, p3.x, p3.y, 25);
        ctx.arcTo(p4.x, p4.y, p5.x, p5.y, 25);
        ctx.arcTo(p6.x, p6.y, p7.x, p7.y, 25);
        ctx.arcTo(p8.x, p8.y, p1.x, p1.y, 25);
        ctx.closePath();

        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(p1.x, p1.y);
        ctx.quadraticCurveTo(p7.x, p7.y, p5.x, p5.y);
        ctx.quadraticCurveTo(p3.x, p3.y, p1.x, p1.y);
        ctx.bezierCurveTo(p3.x, p3.y, p7.x, p7.y, p5.x, p5.y);

        ctx.stroke();

        ctx.restore();
    }

    private void drawEllipse() {
        // Draw the ellipse
        ctx.beginPath();
        ctx.ellipse(100, 100, 50, 75, Math.PI / 4, 0, 2 * Math.PI);
        ctx.stroke();

        // Draw the ellipse's line of reflection
        ctx.beginPath();
        ctx.setLineDash(10, 5, 1, 5);
        ctx.moveTo(0, 200);
        ctx.lineTo(200, 0);
        ctx.stroke();

        ctx.setLineDash();
    }

    private String getRandomColor() {
        return String.format("rgb(%s, %s, %s)", (int) (Math.random() * 256),
                (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

}

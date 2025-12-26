package org.vaadin.pekkam;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.Route;
import in.virit.color.Color;
import in.virit.color.RgbColor;
import in.virit.color.NamedColor;
import org.vaadin.pekkam.event.MouseEvent;
import org.vaadin.pekkam.model.Font;
import org.vaadin.pekkam.model.Repetition;

import java.awt.geom.Point2D;

import static org.vaadin.pekkam.CanvasRenderingContext2D.degreeToRadian;

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
        buttons.add(new NativeButton("Draw Text", e -> drawText()));
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
        ctx.setFillStyle(src, Repetition.REPEAT);
        ctx.fillRect(new Point2D.Double(200, 200), 100, 100);
        ctx.restore();
    }

    private void drawHouse() {
        ctx.save();
        ctx.setFillStyle(NamedColor.YELLOW);
        ctx.strokeRect(new Point2D.Double(200, 200), 100, 100);
        ctx.fillRect(new Point2D.Double(200, 200), 100, 100);

        ctx.beginPath();
        ctx.moveTo(new Point2D.Double(180, 200));
        ctx.lineTo(new Point2D.Double(250, 150));
        ctx.lineTo(new Point2D.Double(320, 200));
        ctx.closePath();
        ctx.stroke();
        ctx.setFillStyle(NamedColor.ORANGE);
        ctx.fill();

        ctx.restore();
    }

    private void drawRandomCircle() {
        ctx.save();
        ctx.setLineWidth(2);
        ctx.setFillStyle(getRandomColor());
        ctx.beginPath();
        ctx.arc(new Point2D.Double(Math.random() * CANVAS_WIDTH, Math.random() * CANVAS_HEIGHT),
                10 + Math.random() * 90, degreeToRadian(0), degreeToRadian(360), false);
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
        ctx.arc(new Point2D.Double(450, 250), 45, degreeToRadian(0), degreeToRadian(360), false);
        ctx.stroke();

        ctx.setFillStyle(getRandomColor());
        ctx.fill();

        ctx.clip();
        ctx.drawImage(src, new Point2D.Double(350, 233), 150, 34);

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
        ctx.arcTo(p2, p3, 25);
        ctx.arcTo(p4, p5, 25);
        ctx.arcTo(p6, p7, 25);
        ctx.arcTo(p8, p1, 25);
        ctx.closePath();

        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(p1);
        ctx.quadraticCurveTo(p7, p5);
        ctx.quadraticCurveTo(p3, p1);
        ctx.bezierCurveTo(p3, p7, p5);

        ctx.stroke();

        ctx.restore();
    }

    private void drawEllipse() {
        // Draw the ellipse
        ctx.beginPath();
        ctx.ellipse(new Point2D.Double(100, 100), 50, 75, degreeToRadian(45), 0, degreeToRadian(360), true);
        ctx.stroke();

        // Draw the ellipse's line of reflection
        ctx.beginPath();
        ctx.setLineDash(10, 5, 1, 5);
        ctx.moveTo(new Point2D.Double(0, 200));
        ctx.lineTo(new Point2D.Double(200, 0));
        ctx.stroke();

        ctx.setLineDash();
    }

    private void drawText() {
        ctx.beginPath();

        ctx.setFont(Font.builder()
                .size(24)
                .variant(Font.Variant.SMALL_CAPS)
                .family(Font.Family.MONOSPACE)
                .build());
        ctx.setLineWidth(2.0);
        ctx.setStrokeStyle(getRandomColor());
        ctx.strokeText("Stroke Text", new Point2D.Double(300, 300));
    }

    private Color getRandomColor() {
        return new RgbColor((int) (Math.random() * 256),
                (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

}

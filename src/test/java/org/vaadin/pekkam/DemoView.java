package org.vaadin.pekkam;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import org.vaadin.pekkam.event.MouseEvent;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        buttons.add(new NativeButton("Draw house", e -> drawHouse()));
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
        add(new Label("Image src: "), input, loadImageButton, drawImageButton, drawPatButton, drawClipButton);

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

        NativeButton downloadButton = new NativeButton("Download as PNG");
        Anchor downloadAnchor = new Anchor();
        downloadAnchor.add(downloadButton);
        downloadAnchor.setHref(DownloadHandler.fromInputStream(downloadEvent -> downloadAsPNG(downloadEvent.getUI())));

        add(new Div(downloadAnchor));
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

    private String getRandomColor() {
        return String.format("rgb(%s, %s, %s)", (int) (Math.random() * 256),
                (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    private DownloadResponse downloadAsPNG(UI ui) {
        try {
            var downloadResponse = new CompletableFuture<DownloadResponse>();

            ui.access(() ->
                    canvas.toDataURL("image/png", 1.0)
                            .whenComplete((dataUrl, throwable) -> {
                                var data = Base64.getDecoder().decode(dataUrl.split(",")[1]);
                                downloadResponse.completeAsync(() -> new DownloadResponse(
                                        new ByteArrayInputStream(data),
                                        "Download_%d.png".formatted(System.currentTimeMillis()),
                                        "image/png",
                                        data.length
                                ));
                            })
            );

            return downloadResponse.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.vaadin.pekkam;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import org.vaadin.pekkam.event.ImageLoadEvent;
import org.vaadin.pekkam.event.MouseClickEvent;
import org.vaadin.pekkam.event.MouseDblClickEvent;
import org.vaadin.pekkam.event.MouseDownEvent;
import org.vaadin.pekkam.event.MouseMoveEvent;
import org.vaadin.pekkam.event.MouseUpEvent;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;

/**
 * Canvas component that you can draw shapes and images on. It's a Java wrapper
 * for the
 * <a href="https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API">HTML5
 * canvas</a>.
 * <p>
 * Use {@link #getContext()} to get API for rendering shapes and images on the
 * canvas.
 * <p>
 */
@Tag("canvas")
@SuppressWarnings("serial")
public class Canvas extends Component implements HasStyle, HasSize, KeyNotifier {
    private final CanvasRenderingContext2D context;

    /**
     * Creates a new canvas component with the given size.
     * <p>
     * Use the API provided by {@link #getContext()} to render graphics on the
     * canvas.
     * <p>
     * The width and height parameters will be used for the canvas' coordinate
     * system. They will determine the size of the component in pixels, unless
     * you explicitly set the component's size with {@link #setWidth(String)} or
     * {@link #setHeight(String)}.
     * 
     * @param width
     *            the width of the canvas
     * @param height
     *            the height of the canvas
     */
    public Canvas(int width, int height) {
        context = new CanvasRenderingContext2D(this);

        getElement().setAttribute("width", String.valueOf(width));
        getElement().setAttribute("height", String.valueOf(height));
    }

    /**
     * Gets the context for rendering shapes and images in the canvas.
     * <p>
     * It is a Java wrapper for the <a href=
     * "https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D">same
     * client-side API</a>.
     * 
     * @return the 2D rendering context of this canvas
     */
    public CanvasRenderingContext2D getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>NOTE:</b> Canvas has an internal coordinate system that it uses for
     * drawing, and it uses the width and height provided in the constructor.
     * This coordinate system is independent of the component's size. Changing
     * the component's size with this method may scale/stretch the rendered
     * graphics.
     */
    @Override
    public void setWidth(String width) {
        HasSize.super.setWidth(width);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>NOTE:</b> Canvas has an internal coordinate system that it uses for
     * drawing, and it uses the width and height provided in the constructor.
     * This coordinate system is independent of the component's size. Changing
     * the component's size with this method may scale/stretch the rendered
     * graphics.
     */
    @Override
    public void setHeight(String height) {
        HasSize.super.setHeight(height);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <b>NOTE:</b> Canvas has an internal coordinate system that it uses for
     * drawing, and it uses the width and height provided in the constructor.
     * This coordinate system is independent of the component's size. Changing
     * the component's size with this method may scale/stretch the rendered
     * graphics.
     */
    @Override
    public void setSizeFull() {
        HasSize.super.setSizeFull();
    }

    /**
     * Load an image resource an prepare it for use as a fill or stroke style. Since images are loaded
     * asyncronously, you need to wait until you receive the associated ImageLoadEvent. Register an
     * event listener using addImageLoadListener().
     *
     * @param src the path to the image resource
     */
    public void loadImage(String src)
    {
        getElement().executeJs("""
        var img = new Image();
        var self = this;
        img.onload = function () {
            if (!self.images) self.images = {};
            self.images[$0] = img;
            self.$server.imageLoaded($0);
        };
        img.src=$0;""",
           src
        );
    }

    @ClientCallable
    public void imageLoaded(String src)
    {
        fireEvent(new ImageLoadEvent(this, true, src));
    }

    public Registration addMouseDownListener(ComponentEventListener<MouseDownEvent> listener)
    {
        return addListener(MouseDownEvent.class, listener);
    }

    public Registration addMouseUpListener(ComponentEventListener<MouseUpEvent> listener)
    {
        return addListener(MouseUpEvent.class, listener);
    }

    public Registration addMouseMoveListener(ComponentEventListener<MouseMoveEvent> listener)
    {
        return addListener(MouseMoveEvent.class, listener);
    }

    public Registration addMouseClickListener(ComponentEventListener<MouseClickEvent> listener)
    {
        return addListener(MouseClickEvent.class, listener);
    }

    public Registration addMouseDblClickListener(ComponentEventListener<MouseDblClickEvent> listener)
    {
        return addListener(MouseDblClickEvent.class, listener);
    }

    public Registration addImageLoadListener(ComponentEventListener<ImageLoadEvent> listener)
    {
        return addListener(ImageLoadEvent.class, listener);
    }

    /**
     * <p>The <code>HTMLCanvasElement.toDataURL()</code> method returns a
     * <a href="https://developer.mozilla.org/en-US/docs/Web/URI/Reference/Schemes/data">data URL</a> containing a
     * representation of the image in the format specified by the <code>type</code> parameter.</p>
     * <p>The desired file format and image quality may be specified. If the file format is not specified, or if the given
     * format is not supported, then the data will be exported as <code>image/png</code>. In other words, if the returned
     * value starts with <code>data:image/png</code> for any other requested <code>type</code>, then that format is not
     * supported.</p>
     * <p>Browsers are required to support <code>image/png</code>; many will support additional formats including
     * <code>image/jpeg</code> and <code>image/webp</code>.</p>
     * <p>The created image data will have a resolution of 96dpi for file formats that support encoding resolution metadata.</p>
     * <p>Warning: <code>toDataURL()</code> encodes the whole image in an in-memory string. For larger images, this can
     * have performance implications, and may even overflow browsers' URL length limit when assigned to
     * <code>HTMLImageElement.src</code>. You should generally prefer <code>toBlob()</code> instead, in combination with
     * <code>URL.createObjectURL()</code>.</p>
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/toDataURL">HTMLCanvasElement: toDataURL() method</a>
     *
     * @param type A string indicating the image format. The default type is <code>image/png</code>; this image format
     *             will be also used if the specified type is not supported
     * @param quality A Number between <code>0</code> and <code>1</code> indicating the image quality to be used when
     *                creating images using file formats that support lossy compression (such as <code>image/jpeg</code>
     *                or <code>image/webp</code>). A user agent will use its default quality value if this option is not
     *                specified, or if the number is outside the allowed range.
     * @return CompletableFuture&lt;String&gt; containing the requested data URL. If the height or width of the canvas is <code>0</code> or larger
     *         than the maximum canvas size, the string <code>"data:,"</code> is returned.
     */
    public CompletableFuture<String> toDataURL(String type, Double quality) {
        var dataUrlType = type != null ? type : "image/png";
        var dataUrlQuality = quality != null && quality >= 0.0 && quality <= 1.0 ? quality : 1.0;

        return getElement()
                .callJsFunction("toDataURL", dataUrlType, dataUrlQuality)
                .toCompletableFuture(String.class);
    }

    /**
     * The canvas content as a raster image.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/toDataURL">HTMLCanvasElement: toDataURL() method</a>
     *
     * @param type A string indicating the image format. The default type is <code>image/png</code>; this image format
     *             will be also used if the specified type is not supported
     * @param quality A Number between <code>0</code> and <code>1</code> indicating the image quality to be used when
     *                creating images using file formats that support lossy compression (such as <code>image/jpeg</code>
     *                or <code>image/webp</code>). A user agent will use its default quality value if this option is not
     *                specified, or if the number is outside the allowed range.
     * @return CompletableFuture of the raster image data. If the height or width of the canvas is <code>0</code> or larger
     *         than the maximum canvas size, empty byte[] is returned.
     */
    public CompletableFuture<byte[]> toImage(String type, Double quality) {
        return toDataURL(type, quality).thenApply(dataUrl -> Base64.getDecoder().decode(dataUrl.split(",")[1]));
    }
}

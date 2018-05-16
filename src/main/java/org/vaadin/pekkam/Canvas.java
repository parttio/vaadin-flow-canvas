package org.vaadin.pekkam;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;

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
public class Canvas extends Component implements HasStyle, HasSize {

    private CanvasRenderingContext2D context;

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
}

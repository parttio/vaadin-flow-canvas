package org.vaadin.pekkam.event;

import com.vaadin.flow.component.ComponentEvent;
import org.vaadin.pekkam.Canvas;

/**
 * This event is sent when an Image has completed loading into the browser.
 */
public class ImageLoadEvent extends ComponentEvent<Canvas>
{
   protected String src;

   public ImageLoadEvent(Canvas source, boolean fromClient, String src)
   {
      super(source, fromClient);
      this.src = src;
   }

   /**
    * @return the image resource path
    */
   public String getSrc()
   {
      return src;
   }
}

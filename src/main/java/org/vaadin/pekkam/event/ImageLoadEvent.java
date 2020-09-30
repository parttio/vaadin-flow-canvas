/*
 * $Header$
 *
 * $DateTime$
 * Copyright (c) 2020 Celera Systems LLC, All Rights Reserved.
 */
package org.vaadin.pekkam.event;

import com.vaadin.flow.component.ComponentEvent;
import org.vaadin.pekkam.Canvas;

public class ImageLoadEvent extends ComponentEvent<Canvas>
{
   protected String src;

   public ImageLoadEvent(Canvas source, boolean fromClient, String src)
   {
      super(source, fromClient);
      this.src = src;
   }

   public String getSrc()
   {
      return src;
   }
}

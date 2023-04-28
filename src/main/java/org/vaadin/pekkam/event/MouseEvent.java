package org.vaadin.pekkam.event;

import com.vaadin.flow.component.ComponentEvent;
import org.vaadin.pekkam.Canvas;

public class MouseEvent extends ComponentEvent<Canvas>
{
   private int offsetX;
   private int offsetY;
   private int button;

   public MouseEvent(
      Canvas source,
      boolean fromClient,
      int clientX,
      int clientY,
      int button
   )
   {
      super(source, fromClient);
      this.offsetX = clientX;
      this.offsetY = clientY;
      this.button = button;
   }

   public int getOffsetX()
   {
      return offsetX;
   }

   public void setOffsetX(int offsetX)
   {
      this.offsetX = offsetX;
   }

   public int getOffsetY()
   {
      return offsetY;
   }

   public void setOffsetY(int offsetY)
   {
      this.offsetY = offsetY;
   }

   public int getButton()
   {
      return button;
   }

   public void setButton(int button)
   {
      this.button = button;
   }
}

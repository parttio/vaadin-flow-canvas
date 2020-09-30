package org.vaadin.pekkam.event;

import com.vaadin.flow.component.ComponentEvent;
import org.vaadin.pekkam.Canvas;

public class MouseEvent extends ComponentEvent<Canvas>
{
   private int clientX;
   private int clientY;
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
      this.clientX = clientX;
      this.clientY = clientY;
      this.button = button;
   }

   public int getClientX()
   {
      return clientX;
   }

   public void setClientX(int clientX)
   {
      this.clientX = clientX;
   }

   public int getClientY()
   {
      return clientY;
   }

   public void setClientY(int clientY)
   {
      this.clientY = clientY;
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

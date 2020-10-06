package org.vaadin.pekkam.event;

import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import org.vaadin.pekkam.Canvas;

@DomEvent("mouseup")
public class MouseUpEvent extends MouseEvent
{
   public MouseUpEvent(
      Canvas source,
      boolean fromClient,
      @EventData("event.offsetX") int offsetX,
      @EventData("event.offsetY") int offsetY,
      @EventData("event.button") int button
   )
   {
      super(source, fromClient, offsetX, offsetY, button);
   }
}

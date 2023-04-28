package org.vaadin.pekkam.event;

import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import org.vaadin.pekkam.Canvas;

@DomEvent("dblclick")
public class MouseDblClickEvent extends MouseEvent
{
   public MouseDblClickEvent(
      Canvas source,
      boolean fromClient,
      @EventData("event.offsetX") int offsetX,
      @EventData("event.offsetX") int offsetY,
      @EventData("event.button") int button
   )
   {
      super(source, fromClient, offsetX, offsetY, button);
   }
}

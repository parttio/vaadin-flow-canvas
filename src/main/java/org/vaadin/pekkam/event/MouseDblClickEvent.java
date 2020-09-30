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
      @EventData("event.clientX") int clientX,
      @EventData("event.clientY") int clientY,
      @EventData("event.button") int button
   )
   {
      super(source, fromClient, clientX, clientY, button);
   }
}

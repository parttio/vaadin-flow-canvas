package org.vaadin.pekkam;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.streams.DownloadHandler;

public class DynamicDownload {

    public static void download(DownloadHandler handler) {
        download(UI.getCurrent(), handler);
    }

    public static void download(UI ui, DownloadHandler handler) {
        var hiddenAnchor = new Anchor() {{
            setText("");
            setDownload(true);
            // piggy pack the handler in event
            setHref(event -> {
                handler.handleDownloadRequest(event);
                event.getUI().access(() -> removeFromParent());
            });
            getStyle().setHeight("0");
            getElement().executeJs("this.click();this.parentElement.removeChild(this);");
        }};
        ui.getElement().appendChild(hiddenAnchor.getElement());
    }
}

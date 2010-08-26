package de.csenk.websocket.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import de.csenk.websocket.client.WebSocketClient;
import de.csenk.websocket.client.js.WebSocket;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class De_csenk_websocket_demo implements EntryPoint {

	@Override
	public void onModuleLoad() {
		if (!WebSocket.IsSupported())
			return;
		
		String webSocketURL = GWT.getModuleBaseURL().replace("http", "ws") + "webSocket";
		WebSocketClient webSocketClient = new WebSocketClient(webSocketURL, new WebSocketClientHandler());
	}
	
}
/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.csenk.gwtws.client;

import de.csenk.gwtws.client.js.WebSocket;
import de.csenk.gwtws.client.js.WebSocketCallback;
import de.csenk.gwtws.shared.IoConnection;
import de.csenk.gwtws.shared.IoFilterChain;
import de.csenk.gwtws.shared.IoHandler;
import de.csenk.gwtws.shared.IoService;

/**
 * @author Christian.Senk
 * @date 26.08.2010
 * @time 13:44:45
 *
 */
public class WebSocketClient implements IoService {

	private final IoHandler handler;
	private final WebSocket webSocket;
	private final IoConnection webSocketConnection;
	
	/**
	 * @param url
	 * @param handler
	 */
	public WebSocketClient(final String url, final IoHandler handler) {
		this.handler = handler;
		this.webSocket = createWebSocket(url);
		this.webSocketConnection = createWebSocketConnection();
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoService#getHandler()
	 */
	@Override
	public IoHandler getHandler() {
		return handler;
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoService#getFilterChain()
	 */
	@Override
	public IoFilterChain getFilterChain() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	private void sendMessage(Object message) {
		//TODO Filter message
		
		Object filteredMessage = message;
		assert filteredMessage instanceof String;
		
		webSocket.send((String) filteredMessage);
		handler.onMessageSent(webSocketConnection, message);
	}
	
	/**
	 * @param message
	 */
	private void receiveMessage(String message) {
		//TODO Filter message
		
		Object filteredMessage = message;
		handler.onMessageReceived(webSocketConnection, filteredMessage);
	}
	
	/**
	 * @param url
	 * @return
	 */
	private WebSocket createWebSocket(String url) {
		return new WebSocket(url, new WebSocketCallback() {
			
			@Override
			public void onOpen(WebSocket webSocket) {
				handler.onConnectionOpened(webSocketConnection);
			}
			
			@Override
			public void onMessage(WebSocket webSocket, String message) {
				receiveMessage(message);
			}
			
			@Override
			public void onError(WebSocket webSocket) {
				handler.onExceptionCaught(new IllegalStateException("Some exception occured during the usage of a JavaScript WebSocket."));
			}
			
			@Override
			public void onClose(WebSocket webSocket) {
				handler.onConnectionClosed(webSocketConnection);
			}
			
		});
	}
	
	/**
	 * @return
	 */
	private IoConnection createWebSocketConnection() {
		return new IoConnection() {
			
			@Override
			public void sendMessage(Object message) {
				WebSocketClient.this.sendMessage(message);
			}
			
			@Override
			public void close() {
				webSocket.close();
			}
			
		};
	}
}

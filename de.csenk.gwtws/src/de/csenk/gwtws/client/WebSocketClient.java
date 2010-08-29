/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.csenk.gwtws.client;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;

import de.csenk.gwtws.client.js.WebSocket;
import de.csenk.gwtws.client.js.WebSocketCallback;
import de.csenk.gwtws.shared.IoConnection;
import de.csenk.gwtws.shared.IoFilterChain;
import de.csenk.gwtws.shared.IoHandler;
import de.csenk.gwtws.shared.IoService;

/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 13:44:45
 *
 */
public class WebSocketClient implements IoService {

	private final IoHandler handler;
	private final WebSocket webSocket;
	private final IoConnection webSocketConnection;

	private final Queue<DelayedMessage> outgoingMessages = new LinkedList<DelayedMessage>();
	private final Timer sendDelayedMessageTimer = new Timer() {

		@Override
		public void run() {
			if (outgoingMessages.isEmpty()) {
				cancel();
				return;
			}
			
			if (webSocket.getBufferedAmount() > 0)
				return;
			
			DelayedMessage nextMessage = outgoingMessages.poll();
			if (nextMessage == null)
				return;
			
			try {
				sendMessageImmediatly(nextMessage.getMessage(), nextMessage.getFilteredMessage());
				Log.debug("Sending message with a delay of " + (new Date().getTime() - nextMessage.getSendingTimestamp()) + "ms");
			} catch (Throwable e) {
				handler.onExceptionCaught(e);
			}
		}
		
	};
	
	
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
	 * @throws Exception 
	 * 
	 */
	private void sendMessage(Object message) throws Exception {
		//TODO Filter message
		
		Object filteredMessage = message;
		assert filteredMessage instanceof String;
		
		sendMessageDelayedIfNecessary(message, (String) filteredMessage);
	}

	/**
	 * @param message
	 * @param filteredMessage
	 * @throws Exception 
	 */
	private void sendMessageDelayedIfNecessary(Object message, String filteredMessage) throws Exception {
		if (webSocket.getBufferedAmount() <= 0) {
			sendMessageImmediatly(message, filteredMessage);
			return;
		}
		
		sendMessageDelayed(message, filteredMessage);
	}

	/**
	 * @param message
	 * @param filteredMessage
	 * @throws Exception 
	 */
	private void sendMessageImmediatly(Object message, String filteredMessage) throws Exception {
		webSocket.send(filteredMessage);
		handler.onMessageSent(webSocketConnection, message);
	}
	
	/**
	 * @param message
	 * @param filteredMessage
	 */
	private void sendMessageDelayed(Object message, String filteredMessage) {
		outgoingMessages.add(new DelayedMessage(message, filteredMessage));
		sendDelayedMessageTimer.scheduleRepeating(10);
	}

	/**
	 * @param message
	 * @throws Exception 
	 */
	private void receiveMessage(String message) throws Exception {
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
				try {
					handler.onConnectionOpened(webSocketConnection);
				} catch (Throwable thrown) {
					handler.onExceptionCaught(thrown);
				}
			}
			
			@Override
			public void onMessage(WebSocket webSocket, String message) {	
				try {
					receiveMessage(message);
				} catch (Throwable thrown) {
					handler.onExceptionCaught(thrown);
				}
			}
			
			@Override
			public void onError(WebSocket webSocket) {
				handler.onExceptionCaught(new IllegalStateException("Some exception occured during the usage of a JavaScript WebSocket."));
			}
			
			@Override
			public void onClose(WebSocket webSocket) {
				try {
					handler.onConnectionClosed(webSocketConnection);
				} catch (Throwable thrown) {
					handler.onExceptionCaught(thrown);
				}
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
				try {
					WebSocketClient.this.sendMessage(message);
				} catch (Throwable thrown) {
					handler.onExceptionCaught(thrown);
				}
			}
			
			@Override
			public void close() {
				try {
					webSocket.close();
				} catch (Throwable thrown) {
					handler.onExceptionCaught(thrown);
				}
			}
			
		};
	}
	
}

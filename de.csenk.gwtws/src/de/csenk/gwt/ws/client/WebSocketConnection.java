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

package de.csenk.gwt.ws.client;

import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.FilterChain;
import de.csenk.gwt.ws.shared.Handler;
import de.csenk.gwt.ws.shared.Sender;
import de.csenk.gwt.ws.shared.filter.DefaultFilterChain;

/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 13:44:45
 *
 */
public class WebSocketConnection implements Connection {

	private final Handler handler;
	private final WebSocketFactory socketFactory;
	
	private final WebSocket webSocket;
	private final Sender webSocketSender;
	
	private final FilterChain filterChain;
	
	/**
	 * @param url
	 * @param handler
	 */
	public WebSocketConnection(final String url, final Handler handler, final WebSocketFactory socketFactory) {
		this.handler = handler;
		this.socketFactory = socketFactory;
		
		this.webSocket = createWebSocket(url);
		
		this.webSocketSender = new WebSocketSender(webSocket);
		this.filterChain = new DefaultFilterChain(this);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Connection#close()
	 */
	@Override
	public void close() {
		webSocket.close();
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Connection#getFilterChain()
	 */
	@Override
	public FilterChain getFilterChain() {
		return filterChain;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Connection#getHandler()
	 */
	@Override
	public Handler getHandler() {
		return handler;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Connection#getSender()
	 */
	@Override
	public Sender getSender() {
		return webSocketSender;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Connection#send(java.lang.Object)
	 */
	@Override
	public void send(Object message) {
		filterChain.fireSend(message);
	}
		
	/**
	 * @param url
	 * @return
	 */
	private WebSocket createWebSocket(String url) {
		return socketFactory.createWebSocket(url, new WebSocketCallback() {
			
			@Override
			public void onOpen(WebSocket webSocket) {
				filterChain.fireConnectionOpened();
			}
			
			@Override
			public void onMessage(WebSocket webSocket, String message) {
				filterChain.fireMessageReceived(message);
			}
			
			@Override
			public void onError(WebSocket webSocket) {
				filterChain.fireExceptionCaught(new IllegalStateException("JavaScript implementation of WebSocket thrown an unknown exception."));
			}
			
			@Override
			public void onClose(WebSocket webSocket) {
				filterChain.fireConnectionClosed();
			}
			
		});
	}
	
}

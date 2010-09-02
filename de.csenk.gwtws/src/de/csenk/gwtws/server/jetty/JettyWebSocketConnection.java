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

package de.csenk.gwtws.server.jetty;

import org.eclipse.jetty.websocket.WebSocket;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.FilterChain;
import de.csenk.gwtws.shared.Handler;
import de.csenk.gwtws.shared.Sender;
import de.csenk.gwtws.shared.filter.FilterChainImpl;

/**
 * @author senk.christian@googlemail.com
 * @date 25.08.2010
 * @time 13:56:30
 *
 */
public class JettyWebSocketConnection implements WebSocket, Connection {

	private final Handler handler;
	
	private Outbound outbound;
	private Sender sender;
	
	private final FilterChain filterChain;
	
	/**
	 * @param handler
	 */
	public JettyWebSocketConnection(final Handler handler) {
		this.handler = handler;
		this.filterChain = new FilterChainImpl(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onConnect(org.eclipse.jetty.websocket.WebSocket.Outbound)
	 */
	@Override
	public void onConnect(Outbound arg0) {
		sender = new OutboundSender(arg0);
		filterChain.fireConnectionOpened();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onDisconnect()
	 */
	@Override
	public void onDisconnect() {
		filterChain.fireConnectionClosed();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte, java.lang.String)
	 */
	@Override
	public void onMessage(byte arg0, String arg1) {
		filterChain.fireMessageReceived(arg1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte, byte[], int, int)
	 */
	@Override
	public void onMessage(byte arg0, byte[] arg1, int arg2, int arg3) {
		filterChain.fireMessageReceived(new String(arg1, arg2, arg3));
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#close()
	 */
	@Override
	public void close() {
		outbound.disconnect();
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#getFilterChain()
	 */
	@Override
	public FilterChain getFilterChain() {
		return filterChain;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#getHandler()
	 */
	@Override
	public Handler getHandler() {
		return handler;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#getSender()
	 */
	@Override
	public Sender getSender() {
		return sender;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#send(java.lang.Object)
	 */
	@Override
	public void send(Object message) {
		filterChain.fireSend(message);
	}
		
}

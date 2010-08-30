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

package de.csenk.gwtws.server;

import org.eclipse.jetty.websocket.WebSocket;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.FilterChain;
import de.csenk.gwtws.shared.Handler;
import de.csenk.gwtws.shared.Service;

/**
 * @author senk.christian@googlemail.com
 * @date 25.08.2010
 * @time 13:56:30
 *
 */
public class JettyWebSocket implements WebSocket, Service {

	private final Handler handler;
	
	private Outbound outbound;
	private Connection outboundConnection;
	
	/**
	 * @param handler
	 */
	public JettyWebSocket(final Handler handler) {
		this.handler = handler;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onConnect(org.eclipse.jetty.websocket.WebSocket.Outbound)
	 */
	@Override
	public void onConnect(Outbound outbound) {
		this.outbound = outbound;
		outboundConnection = new OutboundConnection();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onDisconnect()
	 */
	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte, java.lang.String)
	 */
	@Override
	public void onMessage(byte arg0, String message) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte, byte[], int, int)
	 */
	@Override
	public void onMessage(byte arg0, byte[] messageBytes, int messageOffset, int messageLength) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoService#getHandler()
	 */
	@Override
	public Handler getHandler() {
		return handler;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Service#getFilterChainBuilder()
	 */
	@Override
	public FilterChain getFilterChain() {
		// TODO Auto-generated method stub
		return null;
	}
		
}

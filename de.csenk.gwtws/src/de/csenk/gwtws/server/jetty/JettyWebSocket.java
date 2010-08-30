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

/**
 * @author senk.christian@googlemail.com
 * @date 25.08.2010
 * @time 13:56:30
 *
 */
public class JettyWebSocket implements WebSocket, Connection {

	private final Handler handler;
	
	private Outbound outbound;
	private Sender sender;
	
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
	public void onConnect(Outbound arg0) {
		sender = new OutboundSender(arg0);
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
	public void onMessage(byte arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onMessage(byte, byte[], int, int)
	 */
	@Override
	public void onMessage(byte arg0, byte[] arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}
		
}

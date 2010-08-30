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

package de.csenk.gwtws.demo.server;

import de.csenk.gwtws.server.filter.serialization.ServerGWTSerializer;
import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.Handler;


/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 15:42:17
 *
 */
public class WebSocketServerHandler implements Handler {

	private final ServerGWTSerializer serverSerializer = new ServerGWTSerializer();
	
	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionClosed(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionClosed(Connection connection) {
		System.out.println("Connection closed");
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionOpened(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionOpened(Connection connection) {
		System.out.println(Thread.currentThread().getContextClassLoader().toString());
		System.out.println("Connection opened");
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onExceptionCaught(java.lang.Throwable)
	 */
	@Override
	public void onExceptionCaught(Throwable caught) {
		caught.printStackTrace();
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onMessageReceived(de.csenk.websocket.shared.IoConnection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(Connection connection, Object message) throws Exception {
		System.out.println(Thread.currentThread().getContextClassLoader().toString());
		Object obj = serverSerializer.deserialize((String) message);
		System.out.println(obj.getClass().getName() + ": " + obj.toString());
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onMessageSent(de.csenk.websocket.shared.IoConnection, java.lang.Object)
	 */
	@Override
	public void onMessageSent(Connection connection, Object message) {
		// TODO Auto-generated method stub

	}

}

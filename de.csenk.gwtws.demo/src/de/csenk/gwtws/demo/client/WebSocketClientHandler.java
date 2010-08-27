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

package de.csenk.gwtws.demo.client;

import com.google.gwt.core.client.GWT;

import de.csenk.gwtws.shared.IoConnection;
import de.csenk.gwtws.shared.IoHandler;
import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;


/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 15:44:06
 *
 */
public class WebSocketClientHandler implements IoHandler {
	
	private final GWTSerializer stringSerializer = GWT.create(StringSerializer.class);
	
	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionClosed(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionClosed(IoConnection connection) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionOpened(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionOpened(IoConnection connection) {
		for (int i = 0; i < 1000; i++) {
			connection.sendMessage("#" + i);
		}
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onExceptionCaught(java.lang.Throwable)
	 */
	@Override
	public void onExceptionCaught(Throwable caught) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onMessageReceived(de.csenk.websocket.shared.IoConnection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(IoConnection connection, Object message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onMessageSent(de.csenk.websocket.shared.IoConnection, java.lang.Object)
	 */
	@Override
	public void onMessageSent(IoConnection connection, Object message) {
		// TODO Auto-generated method stub

	}

}

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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.Handler;
import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;


/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 15:44:06
 *
 */
public class WebSocketClientHandler implements Handler {
	
	private final GWTSerializer serializer = GWT.create(GWTSerializer.class);
	
	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionClosed(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionClosed(Connection connection) {
		Log.info("Connection closed");
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionOpened(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionOpened(Connection connection) throws Exception {
		Log.info("Connection opened");
		
		for (int i = 0; i < 10; i++) {
			connection.send(serializer.serialize(i));
			connection.send(serializer.serialize("#" + i));
		}
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onExceptionCaught(java.lang.Throwable)
	 */
	@Override
	public void onExceptionCaught(Throwable caught) {
		Log.error("WebSocketClientHandler.onExceptionCaught", caught);
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onMessageReceived(de.csenk.websocket.shared.IoConnection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(Connection connection, Object message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onMessageSent(de.csenk.websocket.shared.IoConnection, java.lang.Object)
	 */
	@Override
	public void onMessageSent(Connection connection, Object message) {
		// TODO Auto-generated method stub

	}

}

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

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;

import de.csenk.gwtws.client.handler.ClientMessageDispatchingHandler;
import de.csenk.gwtws.demo.shared.Ping;
import de.csenk.gwtws.shared.Connection;


/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 15:44:06
 *
 */
public class WebSocketClientHandler extends ClientMessageDispatchingHandler {
	
	private Timer pingTimer;
	
	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionClosed(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionClosed(Connection connection) throws Throwable {
		Log.info("Connection closed");
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoHandler#onConnectionOpened(de.csenk.websocket.shared.IoConnection)
	 */
	@Override
	public void onConnectionOpened(final Connection connection) throws Throwable {
		Log.info("Connection opened");
		
		pingTimer = new Timer() {

			@Override
			public void run() {
				connection.send(new Ping(new Date().getTime()));
			}
			
		};
		pingTimer.scheduleRepeating(1000);
	}

}

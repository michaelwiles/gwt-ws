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

import java.io.IOException;

import de.csenk.gwtws.client.js.WebSocket;
import de.csenk.gwtws.shared.Sender;

/**
 * @author Christian.Senk
 * @date 31.08.2010
 * @time 15:38:55
 *
 */
public class WebSocketSender implements Sender {

	private final WebSocket webSocket;
	
	public WebSocketSender(WebSocket webSocket) {
		this.webSocket = webSocket;
	}
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Sender#send(java.lang.String)
	 */
	@Override
	public void send(String message) throws IOException {
		webSocket.send(message);
	}

}

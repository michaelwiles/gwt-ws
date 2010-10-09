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

package de.csenk.gwtws.client.js;

import de.csenk.gwtws.client.WebSocket;
import de.csenk.gwtws.client.WebSocketCallback;
import de.csenk.gwtws.client.WebSocketFactory;

/**
 * @author senk.christian@googlemail.com
 * @date 09.10.2010
 * @time 11:21:56
 *
 */
public class JavaScriptWebSocketFactory implements WebSocketFactory {

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.client.WebSocketFactory#createWebSocket(java.lang.String, de.csenk.gwtws.client.WebSocketCallback)
	 */
	@Override
	public WebSocket createWebSocket(String url, WebSocketCallback callback) {
		return new JavaScriptWebSocket(url, callback);
	}

}

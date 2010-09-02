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

package de.csenk.gwtws.client;

import de.csenk.gwtws.client.js.JavaScriptWebSocket;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 14:45:42
 *
 */
public interface WebSocket {

	/**
	 * Sends a message.
	 * 
	 * @param message
	 */
	public void send(String message);

	/**
	 * Closes this {@link JavaScriptWebSocket}.
	 */
	public void close();

	/**
	 * @return the bufferedAmount property of the underlying JavaScript WebSocket.
	 */
	public int getBufferedAmount();

	/**
	 * @return the readyState property of the underlying JavaScript WebSocket.
	 */
	public int getReadyState();

	/**
	 * @return the url property of the underlying JavaScript WebSocket.
	 */
	public String getURL();

}
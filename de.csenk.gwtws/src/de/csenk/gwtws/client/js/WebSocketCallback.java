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

/**
 * @author senk.christian@googlemail.com
 * @date 25.08.2010
 * @time 12:08:28
 *
 * Interface definition to create a call-back for a {@link WebSocket}.
 */
public interface WebSocketCallback {

	/**
	 * Called if the {@link WebSocket} has opened.
	 *
	 * @param webSocket
	 */
	void onOpen(WebSocket webSocket);
	
	/**
	 * Called if the {@link WebSocket} was closed.
	 * 
	 * @param webSocket
	 */
	void onClose(WebSocket webSocket);
	
	/**
	 * Called if a message arrived in the {@link WebSocket}.
	 * 
	 * @param webSocket
	 * @param message the delivered message.
	 */
	void onMessage(WebSocket webSocket, String message);
	
	/**
	 * Called if an error occurred during the usage of the {@link WebSocket}
	 *
	 * @param webSocket
	 */
	void onError(WebSocket webSocket);
	
}

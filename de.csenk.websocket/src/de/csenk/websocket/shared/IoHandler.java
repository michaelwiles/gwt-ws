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

package de.csenk.websocket.shared;

/**
 * @author Christian.Senk
 * @date 26.08.2010
 * @time 13:42:05
 *
 */
public interface IoHandler {

	/**
	 * @param connection
	 */
	void onConnectionOpened(IoConnection connection);
	
	/**
	 * @param connection
	 */
	void onConnectionClosed(IoConnection connection);
	
	/**
	 * @param caught
	 */
	void onExceptionCaught(Throwable caught);
	
	/**
	 * @param connection
	 * @param message
	 */
	void onMessageReceived(IoConnection connection, Object message);
	
	/**
	 * @param connection
	 * @param message
	 */
	void onMessageSent(IoConnection connection, Object message);
	
}

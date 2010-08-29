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

package de.csenk.gwtws.shared;

/**
 * @author Christian
 * @date 29.08.2010
 * @time 22:43:06
 *
 */
public interface IoFilter {

	public interface NextFilter {
		
		/**
		 * @param connection
		 */
		void onConnectionOpened(IoConnection connection) throws Exception;
		
		/**
		 * @param connection
		 */
		void onConnectionClosed(IoConnection connection) throws Exception;
		
		/**
		 * @param caught
		 */
		void onExceptionCaught(Throwable caught);
		
		/**
		 * @param connection
		 * @param message
		 */
		void onMessageReceived(IoConnection connection, Object message) throws Exception;
		
		/**
		 * @param connection
		 * @param message
		 */
		void onMessageSent(IoConnection connection, Object message) throws Exception;
		
	}
	
	/**
	 * @param connection
	 */
	void onConnectionOpened(NextFilter nextFilter, IoConnection connection) throws Exception;
	
	/**
	 * @param connection
	 */
	void onConnectionClosed(NextFilter nextFilter, IoConnection connection) throws Exception;
	
	/**
	 * @param caught
	 */
	void onExceptionCaught(NextFilter nextFilter, Throwable caught);
	
	/**
	 * @param connection
	 * @param message
	 */
	void onMessageReceived(NextFilter nextFilter, IoConnection connection, Object message) throws Exception;
	
	/**
	 * @param connection
	 * @param message
	 */
	void onMessageSent(NextFilter nextFilter, IoConnection connection, Object message) throws Exception;
	
}

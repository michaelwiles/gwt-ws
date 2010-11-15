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

package de.csenk.gwt.ws.shared;

/**
 * @author senk.christian@googlemail.com
 * @date 29.08.2010
 * @time 22:43:06
 * 
 */
public interface Filter {

	/**
	 * @param connection
	 */
	void onConnectionOpened(NextFilter nextFilter, Connection connection)
			throws Throwable;

	/**
	 * @param connection
	 */
	void onConnectionClosed(NextFilter nextFilter, Connection connection)
			throws Throwable;

	/**
	 * @param caught
	 */
	void onExceptionCaught(NextFilter nextFilter, Connection connection, Throwable caught);

	/**
	 * @param connection
	 * @param message
	 */
	void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable;

	/**
	 * @param nextFilter
	 * @param connection
	 * @param message
	 * @throws Exception
	 */
	void onSend(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable;

	/**
	 * @author senk.christian@googlemail.com
	 * @date 30.08.2010
	 * @time 10:49:48
	 *
	 */
	public interface NextFilter {

		/**
		 * @param connection
		 */
		void onConnectionOpened(Connection connection) throws Throwable;

		/**
		 * @param connection
		 */
		void onConnectionClosed(Connection connection) throws Throwable;

		/**
		 * @param caught
		 */
		void onExceptionCaught(Connection connection, Throwable caught);

		/**
		 * @param connection
		 * @param message
		 */
		void onMessageReceived(Connection connection, Object message)
				throws Throwable;

		/**
		 * @param connection
		 * @param message
		 * @throws Exception
		 */
		void onSend(Connection connection, Object message)
				throws Throwable;

	}

}

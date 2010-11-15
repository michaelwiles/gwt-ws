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

package de.csenk.gwt.ws.shared.filter;

import de.csenk.gwt.ws.shared.Connection;

/**
 * @author senk.christian@googlemail.com
 * @date 30.08.2010
 * @time 11:17:13
 *
 */
final class TailFilter extends DefaultFilter {

	public static final String NAME = "tail";
	
	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onConnectionClosed(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection)
	 */
	@Override
	public void onConnectionClosed(NextFilter nextFilter, Connection connection)
			throws Throwable {
		connection.getHandler().onConnectionClosed(connection);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onConnectionOpened(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection)
	 */
	@Override
	public void onConnectionOpened(NextFilter nextFilter, Connection connection)
			throws Throwable {
		connection.getHandler().onConnectionOpened(connection);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.filter.FilterImpl#onExceptionCaught(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Throwable)
	 */
	@Override
	public void onExceptionCaught(NextFilter nextFilter, Connection connection,
			Throwable caught) {
		connection.getHandler().onExceptionCaught(connection, caught);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.filter.FilterImpl#onMessageReceived(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		connection.getHandler().onMessageReceived(connection, message);
	}

}

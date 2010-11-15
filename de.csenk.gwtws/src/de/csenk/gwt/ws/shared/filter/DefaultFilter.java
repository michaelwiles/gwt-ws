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
import de.csenk.gwt.ws.shared.Filter;

/**
 * @author senk.christian@googlemail.com
 * @date 31.08.2010
 * @time 15:41:34
 *
 */
public class DefaultFilter implements Filter {

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onConnectionClosed(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection)
	 */
	@Override
	public void onConnectionClosed(NextFilter nextFilter, Connection connection)
			throws Throwable {
		nextFilter.onConnectionClosed(connection);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onConnectionOpened(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection)
	 */
	@Override
	public void onConnectionOpened(NextFilter nextFilter, Connection connection)
			throws Throwable {
		nextFilter.onConnectionOpened(connection);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onExceptionCaught(de.csenk.gwt.ws.shared.Filter.NextFilter, java.lang.Throwable)
	 */
	@Override
	public void onExceptionCaught(NextFilter nextFilter, Connection connection, Throwable caught) {
		nextFilter.onExceptionCaught(connection, caught);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onMessageReceived(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		nextFilter.onMessageReceived(connection, message);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onSendMessage(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onSend(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		nextFilter.onSend(connection, message);
	}

}

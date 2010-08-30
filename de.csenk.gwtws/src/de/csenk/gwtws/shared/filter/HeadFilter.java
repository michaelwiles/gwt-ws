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

package de.csenk.gwtws.shared.filter;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.Filter;
import de.csenk.gwtws.shared.Sender;

/**
 * @author Christian.Senk
 * @date 30.08.2010
 * @time 11:16:41
 *
 */
public final class HeadFilter implements Filter {

	public static final String NAME = "head";
	
	/**
	 * @param socketSender
	 */
	public HeadFilter(Sender socketSender) {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Filter#onConnectionClosed(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection)
	 */
	@Override
	public void onConnectionClosed(NextFilter nextFilter, Connection connection)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Filter#onConnectionOpened(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection)
	 */
	@Override
	public void onConnectionOpened(NextFilter nextFilter, Connection connection)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Filter#onExceptionCaught(de.csenk.gwtws.shared.Filter.NextFilter, java.lang.Throwable)
	 */
	@Override
	public void onExceptionCaught(NextFilter nextFilter, Throwable caught) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Filter#onMessageReceived(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Filter#onMessageSent(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageSent(NextFilter nextFilter, Connection connection,
			Object message) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Filter#onSendMessage(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onSendMessage(NextFilter nextFilter, Connection connection,
			Object message) throws Exception {
		// TODO Auto-generated method stub

	}

}

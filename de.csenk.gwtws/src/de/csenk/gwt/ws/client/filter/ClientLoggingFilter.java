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

package de.csenk.gwt.ws.client.filter;

import com.allen_sauer.gwt.log.client.Log;

import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.filter.DefaultFilter;

/**
 * @author senk.christian@googlemail.com
 * @date 01.09.2010
 * @time 14:33:31
 *
 */
public class ClientLoggingFilter extends DefaultFilter {

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.filter.FilterImpl#onMessageReceived(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		Log.info(message.toString());
		
		super.onMessageReceived(nextFilter, connection, message);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.filter.FilterImpl#onSend(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onSend(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		Log.info(message.toString());
		
		super.onSend(nextFilter, connection, message);
	}

}

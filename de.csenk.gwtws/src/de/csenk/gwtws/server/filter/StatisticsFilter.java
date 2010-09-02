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

package de.csenk.gwtws.server.filter;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.filter.FilterImpl;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 10:56:57
 *
 */
public class StatisticsFilter extends FilterImpl {

	private long receivedByteCount;
	private long sentByteCount;
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.filter.FilterImpl#onMessageReceived(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		assert message instanceof String;
		
		String strMsg = (String) message;
		int byteCount = strMsg.getBytes().length;
		receivedByteCount += byteCount;
		
		//TODO gwtws: Use a logging facade
		System.out.println("Received " + byteCount + " bytes and " + receivedByteCount + " in total");
		nextFilter.onMessageReceived(connection, message);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.filter.FilterImpl#onSend(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onSend(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		assert message instanceof String;
		
		String strMsg = (String) message;
		int byteCount = strMsg.getBytes().length;
		sentByteCount += byteCount;
		
		//TODO gwtws: Use a logging facade
		System.out.println("Sent " + byteCount + " bytes and " + sentByteCount + " in total");
		nextFilter.onSend(connection, message);
	}

}

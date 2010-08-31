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
import de.csenk.gwtws.shared.Sender;

/**
 * @author Christian.Senk
 * @date 30.08.2010
 * @time 11:16:41
 *
 */
final class HeadFilter extends FilterImpl {

	public static final String NAME = "head";
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Filter#onSendMessage(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onSend(NextFilter nextFilter, Connection connection,
			Object message) throws Exception {
		assert message instanceof String;
		
		Sender sender = connection.getSender();
		sender.send((String) message);
	}

}

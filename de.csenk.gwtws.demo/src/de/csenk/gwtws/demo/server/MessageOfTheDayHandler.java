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

package de.csenk.gwtws.demo.server;

import de.csenk.gwtws.demo.shared.MessageOfTheDayRequest;
import de.csenk.gwtws.demo.shared.MessageOfTheDayResponse;
import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.MessageHandler;

/**
 * @author Christian
 * @date 11.10.2010
 * @time 21:17:20
 *
 */
public class MessageOfTheDayHandler implements
		MessageHandler<MessageOfTheDayRequest> {

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.MessageHandler#handleMessage(de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void handleMessage(Connection connection,
			MessageOfTheDayRequest requestMessage) {
		connection.send(new MessageOfTheDayResponse(requestMessage, "Hello World!"));
	}

}

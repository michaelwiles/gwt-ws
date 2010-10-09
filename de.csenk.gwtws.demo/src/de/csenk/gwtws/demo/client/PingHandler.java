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

package de.csenk.gwtws.demo.client;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;

import de.csenk.gwtws.demo.shared.Ping;
import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.MessageHandler;

/**
 * @author senk.christian@googlemail.com
 * @date 09.09.2010
 * @time 20:01:48
 *
 */
public class PingHandler implements MessageHandler<Ping> {

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.MessageHandler#handleMessage(de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void handleMessage(Connection connection, Ping message) {
		long delay = new Date().getTime() - message.getTimestamp();
		Log.info("Actual ping is " + delay + "ms");
	}

}

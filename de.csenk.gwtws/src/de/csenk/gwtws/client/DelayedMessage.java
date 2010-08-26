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

package de.csenk.gwtws.client;

import java.util.Date;

/**
 * @author Christian
 * @date 26.08.2010
 * @time 22:17:42
 *
 */
public class DelayedMessage {

	private final long sendingTimestamp;
	
	private final Object message;
	private final String filteredMessage;
	
	/**
	 * @param sendingTimestamp
	 * @param message
	 * @param filteredMessage
	 */
	public DelayedMessage(Object message,
			String filteredMessage) {
		this.sendingTimestamp = new Date().getTime();
		this.message = message;
		this.filteredMessage = filteredMessage;
	}

	/**
	 * @return the sendingTimestamp
	 */
	public long getSendingTimestamp() {
		return sendingTimestamp;
	}

	/**
	 * @return the message
	 */
	public Object getMessage() {
		return message;
	}

	/**
	 * @return the filteredMessage
	 */
	public String getFilteredMessage() {
		return filteredMessage;
	}
	
}

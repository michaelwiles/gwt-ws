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

package de.csenk.gwtws.demo.shared;

import de.csenk.gwtws.shared.filter.requestresponse.ResponseMessage;

/**
 * @author Christian
 * @date 11.10.2010
 * @time 20:37:49
 *
 */
@SuppressWarnings("serial")
public class MessageOfTheDayResponse extends ResponseMessage {

	private String messageOfTheDay;
	
	public MessageOfTheDayResponse() {
		super(null);
	}
	
	/**
	 * @param requestID
	 */
	public MessageOfTheDayResponse(MessageOfTheDayRequest motdRequest, String messageOfTheDay) {
		super(motdRequest.getRequestID());
		
		this.messageOfTheDay = messageOfTheDay;
	}

	/**
	 * @return the messageOfTheDay
	 */
	public String getMessageOfTheDay() {
		return messageOfTheDay;
	}

	/**
	 * @param messageOfTheDay the messageOfTheDay to set
	 */
	public void setMessageOfTheDay(String messageOfTheDay) {
		this.messageOfTheDay = messageOfTheDay;
	}

}

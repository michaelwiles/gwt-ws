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

package de.csenk.gwt.ws.demo.shared;

import de.csenk.gwt.ws.shared.filter.requestresponse.RequestMessage;
import de.csenk.gwt.ws.shared.filter.requestresponse.ResponseCallback;

/**
 * @author Christian
 * @date 11.10.2010
 * @time 20:37:13
 *
 */
@SuppressWarnings("serial")
public class MessageOfTheDayRequest extends RequestMessage<MessageOfTheDayResponse> {

	/**
	 * 
	 */
	public MessageOfTheDayRequest() {
		super(null);
	}
	
	/**
	 * @param responseCallback
	 */
	public MessageOfTheDayRequest(ResponseCallback<MessageOfTheDayResponse> responseCallback) {
		super(responseCallback);
	}

}

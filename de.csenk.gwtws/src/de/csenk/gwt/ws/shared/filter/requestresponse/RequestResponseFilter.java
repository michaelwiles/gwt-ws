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

package de.csenk.gwt.ws.shared.filter.requestresponse;

import java.util.HashMap;
import java.util.Map;

import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.filter.DefaultFilter;

/**
 * @author Christian
 * @date 10.10.2010
 * @time 13:04:24
 *
 */
public class RequestResponseFilter extends DefaultFilter {
	
	private int requestCounter = Integer.MIN_VALUE;
	private final Map<Integer, ResponseCallback<?>> requestMap = new HashMap<Integer, ResponseCallback<?>>();
	
	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onMessageReceived(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		if (!(message instanceof ResponseMessage))
			nextFilter.onMessageReceived(connection, message);
		
		ResponseMessage responseMessage = (ResponseMessage) message;	
		if (responseMessage.getRequestID() != null) {
			if (requestMap.containsKey(responseMessage.getRequestID())) {
				ResponseCallback<ResponseMessage> callback = (ResponseCallback<ResponseMessage>) requestMap.get(responseMessage.getRequestID());
				requestMap.remove(responseMessage.getRequestID());
				
				if (callback != null) 
					callback.onMessage(responseMessage);
			}
		}
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.Filter#onSend(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onSend(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		if (!(message instanceof RequestMessage))
			nextFilter.onSend(connection, message);
		
		RequestMessage<?> requestMessage = (RequestMessage<?>) message;
		if (requestMessage.getResponseCallback() != null) {
			requestMessage.setRequestID(generateRequestID());
			
			requestMap.put(requestMessage.getRequestID(), requestMessage.getResponseCallback());
		}
		
		nextFilter.onSend(connection, requestMessage);
	}

	/**
	 * @return
	 */
	private int generateRequestID() {
		if (requestCounter >= Integer.MAX_VALUE) {
			requestCounter = Integer.MIN_VALUE;
			return requestCounter;
		}
		
		return requestCounter++;
	}

}

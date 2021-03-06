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

import java.io.Serializable;

/**
 * @author Christian
 * @date 10.10.2010
 * @time 12:40:21
 *
 */
@SuppressWarnings("serial")
public abstract class RequestMessage<T extends ResponseMessage> implements Serializable {

	private Integer requestID;
	
	private transient ResponseCallback<T> responseCallback;
	
	/**
	 * @param responseCallback
	 */
	public RequestMessage(ResponseCallback<T> responseCallback) {
		this.responseCallback = responseCallback;
	}
	
	/**
	 * @param requestID
	 */
	void setRequestID(Integer requestID) {
		this.requestID = requestID;
	}

	/**
	 * @return
	 */
	public Integer getRequestID() {
		return requestID;
	}
	
	/**
	 * @return
	 */
	public ResponseCallback<T> getResponseCallback() {
		return responseCallback;
	}

}

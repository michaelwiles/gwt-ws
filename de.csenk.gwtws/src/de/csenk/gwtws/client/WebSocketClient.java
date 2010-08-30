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

package de.csenk.gwtws.client;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.FilterChain;
import de.csenk.gwtws.shared.Handler;
import de.csenk.gwtws.shared.Service;

/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 13:44:45
 *
 */
public class WebSocketClient implements Service {

	private final Handler handler;
	private final Connection webSocketConnection;
	
	/**
	 * @param url
	 * @param handler
	 */
	public WebSocketClient(final String url, final Handler handler) {
		this.handler = handler;
		this.webSocketConnection = new WebSocketConnection();
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoService#getHandler()
	 */
	@Override
	public Handler getHandler() {
		return handler;
	}

	/* (non-Javadoc)
	 * @see de.csenk.websocket.shared.IoService#getFilterChainBuilder()
	 */
	@Override
	public FilterChain getFilterChain() {
		// TODO Auto-generated method stub
		return null;
	}
		
}

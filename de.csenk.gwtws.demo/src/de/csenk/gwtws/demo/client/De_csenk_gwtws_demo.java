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


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import de.csenk.gwtws.client.WebSocketConnection;
import de.csenk.gwtws.client.filter.ClientLoggingFilter;
import de.csenk.gwtws.client.filter.serialization.ClientGWTSerializationFilter;
import de.csenk.gwtws.client.js.JavaScriptWebSocket;
import de.csenk.gwtws.client.js.JavaScriptWebSocketFactory;
import de.csenk.gwtws.demo.shared.Ping;
import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.FilterChain;
import de.csenk.gwtws.shared.MessageDispatchingHandler;
import de.csenk.gwtws.shared.filter.requestresponse.RequestResponseFilter;
import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class De_csenk_gwtws_demo implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		if (!JavaScriptWebSocket.IsSupported())
			return;
		
		MessageDispatchingHandler handler = new WebSocketClientHandler();
		handler.addReceivedMessageHandler(Ping.class, new PingHandler());
		
		String webSocketURL = GWT.getModuleBaseURL().replace("http", "ws") + "webSocket";
		Connection webSocketClient = new WebSocketConnection(webSocketURL, handler, new JavaScriptWebSocketFactory());
		
		buildFilterChain(webSocketClient.getFilterChain());
	}

	/**
	 * @param filterChain
	 */
	private void buildFilterChain(FilterChain filterChain) {
		GWTSerializer serializer = GWT.create(MessageSerializer.class);
		
		filterChain.addLast("logging", new ClientLoggingFilter());
		filterChain.addLast("serialization", new ClientGWTSerializationFilter(serializer));
		filterChain.addLast("requestresponse", new RequestResponseFilter());
	}
	
}
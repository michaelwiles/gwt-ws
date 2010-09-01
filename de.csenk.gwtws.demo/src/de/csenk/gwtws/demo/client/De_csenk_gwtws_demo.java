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

import de.csenk.gwtws.client.WebSocketClient;
import de.csenk.gwtws.client.filter.LoggingFilter;
import de.csenk.gwtws.client.filter.serialization.GWTClientSerializationFilter;
import de.csenk.gwtws.client.js.WebSocket;
import de.csenk.gwtws.shared.FilterChain;
import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class De_csenk_gwtws_demo implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		if (!WebSocket.IsSupported())
			return;
		
		String webSocketURL = GWT.getModuleBaseURL().replace("http", "ws") + "webSocket";
		WebSocketClient webSocketClient = new WebSocketClient(webSocketURL, new WebSocketClientHandler());
		
		FilterChain filterChain = webSocketClient.getFilterChain();
		filterChain.addLast("logging", new LoggingFilter());
		filterChain.addLast("serialization", new GWTClientSerializationFilter((GWTSerializer) GWT.create(GWTSerializer.class)));
	}
	
}
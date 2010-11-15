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

package de.csenk.gwt.ws.demo.server;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

import de.csenk.gwt.ws.demo.shared.MessageOfTheDayRequest;
import de.csenk.gwt.ws.demo.shared.Ping;
import de.csenk.gwt.ws.server.filter.StatisticsFilter;
import de.csenk.gwt.ws.server.filter.serialization.ServerGWTSerializationFilter;
import de.csenk.gwt.ws.server.filter.serialization.ServletContextSerializationPolicyProvider;
import de.csenk.gwt.ws.server.handler.ServerMessageDispatchingHandler;
import de.csenk.gwt.ws.server.jetty.JettyWebSocketConnection;
import de.csenk.gwt.ws.shared.Filter;
import de.csenk.gwt.ws.shared.FilterChain;
import de.csenk.gwt.ws.shared.MessageDispatchingHandler;

/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 15:41:04
 *
 */
public class WebSocketServletImpl extends WebSocketServlet {

	private static final long serialVersionUID = 5565068884455990101L;

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocketServlet#doWebSocketConnect(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	protected WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
		MessageDispatchingHandler handler = new ServerMessageDispatchingHandler();
		handler.addReceivedMessageHandler(Ping.class, new PingHandler());
		handler.addReceivedMessageHandler(MessageOfTheDayRequest.class, new MessageOfTheDayHandler());
		
		JettyWebSocketConnection webSocketConnection = new JettyWebSocketConnection(handler);
		buildFilterChain(webSocketConnection.getFilterChain());
		
		return webSocketConnection;
	}

	/**
	 * @param filterChain
	 */
	private void buildFilterChain(FilterChain filterChain) {
		SerializationPolicyProvider serialProvider = new ServletContextSerializationPolicyProvider(getServletContext());
		Filter serialFilter = new ServerGWTSerializationFilter(serialProvider);
		
		filterChain.addLast("statistics", new StatisticsFilter());
		filterChain.addLast("serialization", serialFilter);
	}

}

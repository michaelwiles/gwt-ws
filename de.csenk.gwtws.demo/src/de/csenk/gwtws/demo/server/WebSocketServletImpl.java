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

package de.csenk.gwtws.demo.server;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

import de.csenk.gwtws.server.filter.StatisticsFilter;
import de.csenk.gwtws.server.filter.serialization.ServerGWTSerializationFilter;
import de.csenk.gwtws.server.filter.serialization.ServletContextSerializationPolicyProvider;
import de.csenk.gwtws.server.jetty.JettyWebSocket;
import de.csenk.gwtws.shared.Filter;
import de.csenk.gwtws.shared.FilterChain;

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
		JettyWebSocket webSocket = new JettyWebSocket(new WebSocketServerHandler());
		
		buildFilterChain(webSocket.getFilterChain());
		
		return webSocket;
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

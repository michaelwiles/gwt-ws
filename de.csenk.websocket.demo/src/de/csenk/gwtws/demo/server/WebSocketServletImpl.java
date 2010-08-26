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

package de.csenk.gwtws.demo.server;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import de.csenk.websocket.server.WebSocketServer;

/**
 * @author Christian.Senk
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
		return new WebSocketServer(new WebSocketServerHandler());
	}

}

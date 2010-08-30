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

package de.csenk.gwtws.server.jetty;

import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket.Outbound;

import de.csenk.gwtws.shared.Sender;

/**
 * @author Christian.Senk
 * @date 30.08.2010
 * @time 15:46:16
 *
 */
public class OutboundSender implements Sender {

	private final Outbound outbound;
	
	public OutboundSender(Outbound outbound) {
		this.outbound = outbound;
	}
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Sender#send(java.lang.String)
	 */
	@Override
	public void send(String message) throws IOException {
		outbound.sendMessage(message);
	}
	
}

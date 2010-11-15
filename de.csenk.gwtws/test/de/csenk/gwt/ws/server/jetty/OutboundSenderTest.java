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

package de.csenk.gwt.ws.server.jetty;

import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.jetty.websocket.WebSocket.Outbound;
import org.jmock.Expectations;
import org.jmock.Mockery;

import de.csenk.gwt.ws.server.jetty.OutboundSender;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:23:57
 *
 */
public class OutboundSenderTest extends TestCase {

	private final Mockery mockContext = new Mockery();
	
	/**
	 * Test method for {@link de.csenk.gwt.ws.server.jetty.OutboundSender#send(java.lang.String)}.
	 * @throws IOException 
	 */
	public final void testSend() throws IOException {
		final String message = "Hello World!";
		
		final Outbound mockOutbound = mockContext.mock(Outbound.class);
		final OutboundSender outboundSender = new OutboundSender(mockOutbound);
		
		mockContext.checking(new Expectations(){{
			oneOf(mockOutbound).sendMessage(message);
		}});
		
		outboundSender.send(message);
		mockContext.assertIsSatisfied();
	}

}

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

import java.io.IOException;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import de.csenk.gwtws.client.WebSocket;
import de.csenk.gwtws.client.WebSocketSender;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 14:32:21
 * 
 */
public class WebSocketSenderTest extends TestCase {

	private final Mockery mockContext = new Mockery();

	/**
	 * Test method for
	 * {@link de.csenk.gwtws.client.WebSocketSender#send(java.lang.String)}.
	 * @throws IOException 
	 */
	public final void testSend() throws IOException {
		final WebSocket webSocketMock = mockContext.mock(WebSocket.class);
		final WebSocketSender webSocketSender = new WebSocketSender(
				webSocketMock);

		final String message = "Hello World!";

		mockContext.checking(new Expectations() {
			{
				oneOf(webSocketMock).send(message);
			}
		});

		webSocketSender.send(message);
		mockContext.assertIsSatisfied();
	}

}

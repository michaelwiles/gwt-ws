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

package de.csenk.gwt.ws.client.handler;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import de.csenk.gwt.ws.client.handler.ClientMessageDispatchingHandler;
import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.ExceptionHandler;
import de.csenk.gwt.ws.shared.MessageDispatchingHandler;
import de.csenk.gwt.ws.shared.MessageHandler;

/**
 * @author senk.christian@googlemail.com
 * @date 08.09.2010
 * @time 12:57:57
 *
 */
public class ClientMessageDispatchingHandlerTest extends TestCase {

	private Mockery mockContext;
	
	private Connection mockConnection;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		mockContext = new Mockery();
		mockConnection = mockContext.mock(Connection.class);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		mockContext = null;
		mockConnection = null;
	}

	/**
	 * Test method for {@link de.csenk.gwt.ws.client.handler.ClientMessageDispatchingHandler#onExceptionCaught(de.csenk.gwt.ws.shared.Connection, java.lang.Throwable)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnExceptionCaught() {
		final ExceptionHandler<RuntimeException> runtimeHandler = (ExceptionHandler<RuntimeException>) mockContext.mock(ExceptionHandler.class, "runtimeHandler");
		final ExceptionHandler<IllegalArgumentException> exceptionHandler = (ExceptionHandler<IllegalArgumentException>) mockContext.mock(ExceptionHandler.class, "exceptionHandler");
		
		final MessageDispatchingHandler dispatchingHandler = new ClientMessageDispatchingHandler();
		
		mockContext.checking(new Expectations() {{
			oneOf(runtimeHandler).handleException(with(mockConnection), with(any(RuntimeException.class)));
			oneOf(exceptionHandler).handleException(with(mockConnection), with(any(IllegalArgumentException.class)));
		}});
		
		dispatchingHandler.addExceptionHandler(RuntimeException.class, runtimeHandler);
		dispatchingHandler.addExceptionHandler(IllegalArgumentException.class, exceptionHandler);
		
		dispatchingHandler.onExceptionCaught(mockConnection, new IllegalStateException());
		dispatchingHandler.onExceptionCaught(mockConnection, new IllegalArgumentException());
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for {@link de.csenk.gwt.ws.client.handler.ClientMessageDispatchingHandler#onMessageReceived(de.csenk.gwt.ws.shared.Connection, java.lang.Object)}.
	 * @throws Throwable 
	 */
	@SuppressWarnings("unchecked")
	public void testOnMessageReceived() throws Throwable {
		final MessageHandler<Number> numberHandler = (MessageHandler<Number>) mockContext.mock(MessageHandler.class, "numberHandler");
		final MessageHandler<Integer> integerHandler = (MessageHandler<Integer>) mockContext.mock(MessageHandler.class, "integerHandler");
		
		final MessageDispatchingHandler dispatchingHandler = new ClientMessageDispatchingHandler();
		
		mockContext.checking(new Expectations() {{
			oneOf(integerHandler).handleMessage(mockConnection, 1337);
			oneOf(numberHandler).handleMessage(mockConnection, 13.37);
		}});
		
		dispatchingHandler.addReceivedMessageHandler(Number.class, numberHandler);
		dispatchingHandler.addReceivedMessageHandler(Integer.class, integerHandler);
		
		dispatchingHandler.onMessageReceived(mockConnection, 1337);
		dispatchingHandler.onMessageReceived(mockConnection, 13.37);
		
		mockContext.assertIsSatisfied();
	}

}

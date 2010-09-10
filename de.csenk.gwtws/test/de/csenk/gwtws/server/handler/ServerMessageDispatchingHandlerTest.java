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

package de.csenk.gwtws.server.handler;

import java.io.Serializable;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.ExceptionHandler;
import de.csenk.gwtws.shared.MessageDispatchingHandler;
import de.csenk.gwtws.shared.MessageHandler;

/**
 * @author Christian
 * @date 08.09.2010
 * @time 12:57:57
 *
 */
public class ServerMessageDispatchingHandlerTest extends TestCase {

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
	 * Test method for {@link de.csenk.gwtws.server.handler.ServerMessageDispatchingHandler#onExceptionCaught(de.csenk.gwtws.shared.Connection, java.lang.Throwable)}.
	 */
	@SuppressWarnings("unchecked")
	public void testOnExceptionCaught() {
		final ExceptionHandler<Throwable> throwableHandler = (ExceptionHandler<Throwable>) mockContext.mock(ExceptionHandler.class, "throwableHandler");
		final ExceptionHandler<IllegalArgumentException> exceptionHandler = (ExceptionHandler<IllegalArgumentException>) mockContext.mock(ExceptionHandler.class, "exceptionHandler");
		
		final MessageDispatchingHandler dispatchingHandler = new ServerMessageDispatchingHandler();
		
		mockContext.checking(new Expectations() {{
			oneOf(throwableHandler).handleException(with(mockConnection), with(any(Throwable.class)));
			oneOf(exceptionHandler).handleException(with(mockConnection), with(any(IllegalArgumentException.class)));
		}});
		
		dispatchingHandler.addExceptionHandler(Throwable.class, throwableHandler);
		dispatchingHandler.addExceptionHandler(IllegalArgumentException.class, exceptionHandler);
		
		dispatchingHandler.onExceptionCaught(mockConnection, new Exception());
		dispatchingHandler.onExceptionCaught(mockConnection, new IllegalArgumentException());
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for {@link de.csenk.gwtws.server.handler.ServerMessageDispatchingHandler#onMessageReceived(de.csenk.gwtws.shared.Connection, java.lang.Object)}.
	 * @throws Throwable 
	 */
	@SuppressWarnings("unchecked")
	public void testOnMessageReceived() throws Throwable {
		final MessageHandler<Object> objectHandler = (MessageHandler<Object>) mockContext.mock(MessageHandler.class, "objectHandler");
		final MessageHandler<Serializable> serializableHandler = (MessageHandler<Serializable>) mockContext.mock(MessageHandler.class, "serializableHandler");
		
		final MessageDispatchingHandler dispatchingHandler = new ServerMessageDispatchingHandler();
		
		mockContext.checking(new Expectations() {{
			oneOf(serializableHandler).handleMessage(mockConnection, 1337);
			oneOf(objectHandler).handleMessage(with(mockConnection), with(any(Object.class)));
		}});
		
		dispatchingHandler.addReceivedMessageHandler(Object.class, objectHandler);
		dispatchingHandler.addReceivedMessageHandler(Serializable.class, serializableHandler);
		
		dispatchingHandler.onMessageReceived(mockConnection, 1337);
		dispatchingHandler.onMessageReceived(mockConnection, new Object());
		
		mockContext.assertIsSatisfied();
	}

}

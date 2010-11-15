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

package de.csenk.gwt.ws.shared.filter;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.Filter;
import de.csenk.gwt.ws.shared.Handler;
import de.csenk.gwt.ws.shared.filter.TailFilter;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:27:06
 *
 */
public class TailFilterTest extends TestCase {

	private Mockery mockContext;

	private Connection mockConnection;
	private Handler mockHandler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		mockContext = new Mockery();

		mockConnection = mockContext.mock(Connection.class);
		mockHandler = mockContext.mock(Handler.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		mockConnection = null;
		mockHandler = null;

		mockContext = null;
	}
	
	/**
	 * Test method for {@link de.csenk.gwt.ws.shared.filter.TailFilter#onConnectionClosed(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection)}.
	 */
	public final void testOnConnectionClosed() throws Throwable {
		final Filter tailFilter = new TailFilter();
		
		mockContext.checking(new Expectations() {{
			oneOf(mockConnection).getHandler();
				will(returnValue(mockHandler));
				
			oneOf(mockHandler).onConnectionClosed(mockConnection);
		}});
		
		tailFilter.onConnectionClosed(null, mockConnection);
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for {@link de.csenk.gwt.ws.shared.filter.TailFilter#onConnectionOpened(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection)}.
	 */
	public final void testOnConnectionOpened() throws Throwable {
		final Filter tailFilter = new TailFilter();
		
		mockContext.checking(new Expectations() {{
			oneOf(mockConnection).getHandler();
				will(returnValue(mockHandler));
				
			oneOf(mockHandler).onConnectionOpened(mockConnection);
		}});
		
		tailFilter.onConnectionOpened(null, mockConnection);
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for {@link de.csenk.gwt.ws.shared.filter.TailFilter#onExceptionCaught(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Throwable)}.
	 */
	public final void testOnExceptionCaught() throws Throwable {
		final Filter tailFilter = new TailFilter();
		
		final Throwable THROWABLE = new Exception();
		
		mockContext.checking(new Expectations() {{
			oneOf(mockConnection).getHandler();
				will(returnValue(mockHandler));
				
			oneOf(mockHandler).onExceptionCaught(mockConnection, THROWABLE);
		}});
		
		tailFilter.onExceptionCaught(null, mockConnection, THROWABLE);
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for {@link de.csenk.gwt.ws.shared.filter.TailFilter#onMessageReceived(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)}.
	 * @throws Throwable 
	 */
	public final void testOnMessageReceived() throws Throwable {
		final Filter tailFilter = new TailFilter();
		
		final String MESSAGE = "Hello World!";
		
		mockContext.checking(new Expectations() {{
			oneOf(mockConnection).getHandler();
				will(returnValue(mockHandler));
				
			oneOf(mockHandler).onMessageReceived(mockConnection, MESSAGE);
		}});
		
		tailFilter.onMessageReceived(null, mockConnection, MESSAGE);
		
		mockContext.assertIsSatisfied();
	}

}

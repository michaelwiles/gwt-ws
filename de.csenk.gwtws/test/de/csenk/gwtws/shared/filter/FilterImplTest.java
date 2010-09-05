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

package de.csenk.gwtws.shared.filter;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.Filter;
import de.csenk.gwtws.shared.Filter.NextFilter;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:26:00
 *
 */
public class FilterImplTest extends TestCase {

	private Mockery mockContext;

	private Connection mockConnection;
	private NextFilter mockNextFilter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		mockContext = new Mockery();

		mockConnection = mockContext.mock(Connection.class);
		mockNextFilter = mockContext.mock(NextFilter.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		mockConnection = null;
		mockNextFilter = null;

		mockContext = null;
	}

	/**
	 * Test method for {@link de.csenk.gwtws.shared.filter.FilterImpl#onConnectionClosed(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection)}.
	 * @throws Throwable 
	 */
	public final void testOnConnectionClosed() throws Throwable {
		final Filter filter = new FilterImpl();
		
		mockContext.checking(new Expectations() {{
			oneOf(mockNextFilter).onConnectionClosed(mockConnection);
		}});
		
		filter.onConnectionClosed(mockNextFilter, mockConnection);
	}

	/**
	 * Test method for {@link de.csenk.gwtws.shared.filter.FilterImpl#onConnectionOpened(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection)}.
	 */
	public final void testOnConnectionOpened() throws Throwable {
		final Filter filter = new FilterImpl();
		
		mockContext.checking(new Expectations() {{
			oneOf(mockNextFilter).onConnectionOpened(mockConnection);
		}});
		
		filter.onConnectionOpened(mockNextFilter, mockConnection);
	}

	/**
	 * Test method for {@link de.csenk.gwtws.shared.filter.FilterImpl#onExceptionCaught(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Throwable)}.
	 */
	public final void testOnExceptionCaught() throws Throwable {
		final Filter filter = new FilterImpl();
		
		final Throwable THROWABLE = new Exception();
		
		mockContext.checking(new Expectations() {{
			oneOf(mockNextFilter).onExceptionCaught(mockConnection, THROWABLE);
		}});
		
		filter.onExceptionCaught(mockNextFilter, mockConnection, THROWABLE);
	}

	/**
	 * Test method for {@link de.csenk.gwtws.shared.filter.FilterImpl#onMessageReceived(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)}.
	 * @throws Throwable 
	 */
	public final void testOnMessageReceived() throws Throwable {
		final Filter filter = new FilterImpl();
		
		final String MESSAGE = "Hello World!";
		
		mockContext.checking(new Expectations() {{
			oneOf(mockNextFilter).onMessageReceived(mockConnection, MESSAGE);
		}});
		
		filter.onMessageReceived(mockNextFilter, mockConnection, MESSAGE);
	}

	/**
	 * Test method for {@link de.csenk.gwtws.shared.filter.FilterImpl#onSend(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)}.
	 */
	public final void testOnSend() throws Throwable {
		final Filter filter = new FilterImpl();
		
		final String MESSAGE = "Hello World!";
		
		mockContext.checking(new Expectations() {{
			oneOf(mockNextFilter).onSend(mockConnection, MESSAGE);
		}});
		
		filter.onSend(mockNextFilter, mockConnection, MESSAGE);
	}

}

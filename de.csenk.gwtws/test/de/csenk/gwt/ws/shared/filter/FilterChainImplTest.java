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
import org.jmock.Sequence;
import org.jmock.api.Invocation;
import org.jmock.lib.action.CustomAction;

import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.Filter;
import de.csenk.gwt.ws.shared.FilterChain;
import de.csenk.gwt.ws.shared.Filter.NextFilter;
import de.csenk.gwt.ws.shared.filter.DefaultFilterChain;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:25:33
 * 
 */
public class FilterChainImplTest extends TestCase {

	private Mockery mockContext;

	private Connection mockConnection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		mockContext = new Mockery();

		mockConnection = mockContext.mock(Connection.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		mockConnection = null;

		mockContext = null;
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwt.ws.shared.filter.DefaultFilterChain#addLast(java.lang.String, de.csenk.gwt.ws.shared.Filter)}
	 * .
	 * @throws Throwable 
	 */
	public final void testAddLast() throws Throwable {
		final Filter filter1 = mockContext.mock(Filter.class, "filter1");
		final Filter filter2 = mockContext.mock(Filter.class, "filter2");
		final FilterChain filterChain = new DefaultFilterChain(mockConnection);

		final String MESSAGE = "Hello World!";
		final String FILTER_SEQUENCE = "filterSequence";
		final Sequence filterSequence = mockContext.sequence(FILTER_SEQUENCE);

		mockContext.checking(new Expectations() {
			{
				oneOf(filter1).onMessageReceived(with(any(NextFilter.class)),
						with(mockConnection), with(MESSAGE));
					inSequence(filterSequence);
					will(new CustomAction("NextFilterAction") {

						@Override
						public Object invoke(Invocation invocation)
								throws Throwable {
							NextFilter nextFilter = (NextFilter) invocation.getParameter(0);
							nextFilter.onMessageReceived((Connection) invocation.getParameter(1), invocation.getParameter(2));
							return null;
						}
						
					});
				oneOf(filter2).onMessageReceived(with(any(NextFilter.class)),
						with(mockConnection), with(MESSAGE));
					inSequence(filterSequence);
			}
		});
		
		filterChain.addLast("filter1", filter1);
		filterChain.addLast("filter2", filter2);
		filterChain.fireMessageReceived(MESSAGE);
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwt.ws.shared.filter.DefaultFilterChain#fireSend(java.lang.Object)}
	 * .
	 * @throws Throwable 
	 */
	public final void testFireSend() throws Throwable {
		final Filter filter = mockContext.mock(Filter.class, "filter");
		final FilterChain filterChain = new DefaultFilterChain(mockConnection);

		final String MESSAGE = "Hello World!";

		mockContext.checking(new Expectations() {
			{
				oneOf(filter).onSend(with(any(NextFilter.class)), with(mockConnection), with(MESSAGE));
			}
		});
		
		filterChain.addLast("filter", filter);
		filterChain.fireSend(MESSAGE);
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwt.ws.shared.filter.DefaultFilterChain#fireExceptionCaught(java.lang.Throwable)}
	 * .
	 */
	public final void testFireExceptionCaught() {
		final Filter filter = mockContext.mock(Filter.class, "filter");
		final FilterChain filterChain = new DefaultFilterChain(mockConnection);

		final Throwable THROWABLE = new Exception();
		
		mockContext.checking(new Expectations() {
			{
				oneOf(filter).onExceptionCaught(with(any(NextFilter.class)), with(mockConnection), with(THROWABLE));
			}
		});
		
		filterChain.addLast("filter", filter);
		filterChain.fireExceptionCaught(THROWABLE);
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwt.ws.shared.filter.DefaultFilterChain#fireConnectionClosed()}
	 * .
	 * @throws Throwable 
	 */
	public final void testFireConnectionClosed() throws Throwable {
		final Filter filter = mockContext.mock(Filter.class, "filter");
		final FilterChain filterChain = new DefaultFilterChain(mockConnection);

		mockContext.checking(new Expectations() {
			{
				oneOf(filter).onConnectionClosed(with(any(NextFilter.class)), with(mockConnection));
			}
		});
		
		filterChain.addLast("filter", filter);
		filterChain.fireConnectionClosed();
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwt.ws.shared.filter.DefaultFilterChain#fireConnectionOpened()}
	 * .
	 * @throws Throwable 
	 */
	public final void testFireConnectionOpened() throws Throwable {
		final Filter filter = mockContext.mock(Filter.class, "filter");
		final FilterChain filterChain = new DefaultFilterChain(mockConnection);

		mockContext.checking(new Expectations() {
			{
				oneOf(filter).onConnectionOpened(with(any(NextFilter.class)), with(mockConnection));
			}
		});
		
		filterChain.addLast("filter", filter);
		filterChain.fireConnectionOpened();
		
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwt.ws.shared.filter.DefaultFilterChain#fireMessageReceived(java.lang.Object)}
	 * .
	 * @throws Throwable 
	 */
	public final void testFireMessageReceived() throws Throwable {
		final Filter filter = mockContext.mock(Filter.class, "filter");
		final FilterChain filterChain = new DefaultFilterChain(mockConnection);

		final String MESSAGE = "Hello World!";

		mockContext.checking(new Expectations() {
			{
				oneOf(filter).onMessageReceived(with(any(NextFilter.class)), with(mockConnection), with(MESSAGE));
			}
		});
		
		filterChain.addLast("filter", filter);
		filterChain.fireMessageReceived(MESSAGE);
		
		mockContext.assertIsSatisfied();
	}
	
}

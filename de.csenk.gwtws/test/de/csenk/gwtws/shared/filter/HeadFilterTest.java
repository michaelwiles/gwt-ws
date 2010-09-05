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
import de.csenk.gwtws.shared.Sender;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:26:38
 *
 */
public class HeadFilterTest extends TestCase {

	private Mockery mockContext;

	private Connection mockConnection;
	private Sender mockSender;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		mockContext = new Mockery();

		mockConnection = mockContext.mock(Connection.class);
		mockSender = mockContext.mock(Sender.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		mockConnection = null;
		mockSender = null;

		mockContext = null;
	}
	
	/**
	 * Test method for {@link de.csenk.gwtws.shared.filter.HeadFilter#onSend(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)}.
	 * @throws Throwable 
	 */
	public final void testOnSend() throws Throwable {
		final Filter headFilter = new HeadFilter();
		
		final String MESSAGE = "Hello World!";
		
		mockContext.checking(new Expectations() {{
			oneOf(mockConnection).getSender();
				will(returnValue(mockSender));
				
			oneOf(mockSender).send(MESSAGE);
		}});
		
		headFilter.onSend(null, mockConnection, MESSAGE);
		
		mockContext.assertIsSatisfied();
	}

}

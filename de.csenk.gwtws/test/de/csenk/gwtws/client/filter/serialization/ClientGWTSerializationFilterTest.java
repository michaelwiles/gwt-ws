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

package de.csenk.gwtws.client.filter.serialization;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.Filter.NextFilter;
import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:12:20
 * 
 */
public class ClientGWTSerializationFilterTest extends TestCase {

	private Mockery mockContext;

	private GWTSerializer mockSerializer;
	private NextFilter mockNextFilter;
	private Connection mockConnection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		mockContext = new Mockery();

		mockSerializer = mockContext.mock(GWTSerializer.class);
		mockNextFilter = mockContext.mock(NextFilter.class);
		mockConnection = mockContext.mock(Connection.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		mockSerializer = null;
		mockNextFilter = null;
		mockConnection = null;

		mockContext = null;
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwtws.client.filter.serialization.ClientGWTSerializationFilter#onMessageReceived(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)}
	 * .
	 * @throws Throwable 
	 */
	public final void testOnMessageReceived() throws Throwable {
		final Object mockObject = new Object();
		final ClientGWTSerializationFilter filter = new ClientGWTSerializationFilter(mockSerializer);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockSerializer).deserialize("");
					will(returnValue(mockObject));
				oneOf(mockNextFilter).onMessageReceived(mockConnection,mockObject);
			}
		});

		filter.onMessageReceived(mockNextFilter, mockConnection, "");
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwtws.client.filter.serialization.ClientGWTSerializationFilter#onSend(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)}
	 * .
	 * @throws Throwable 
	 */
	public final void testOnSend() throws Throwable {
		final Object mockObject = new Object();
		final String mockMessage = "MockMessage";
		final ClientGWTSerializationFilter filter = new ClientGWTSerializationFilter(mockSerializer);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockSerializer).serialize(mockObject);
					will(returnValue(mockMessage));
				oneOf(mockNextFilter).onSend(mockConnection, mockMessage);
			}
		});
		
		filter.onSend(mockNextFilter, mockConnection, mockObject);
		mockContext.assertIsSatisfied();
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwtws.client.filter.serialization.ClientGWTSerializationFilter#getSerializer()}
	 * .
	 */
	public final void testGetSerializer() {
		ClientGWTSerializationFilter filter = new ClientGWTSerializationFilter(
				mockSerializer);
		assertEquals(mockSerializer, filter.getSerializer());
	}

}

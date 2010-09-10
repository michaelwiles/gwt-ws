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

package de.csenk.gwtws.server.filter.serialization;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:20:51
 * 
 */
public class ServerGWTSerializerTest extends TestCase {

	private Mockery mockContext;

	private SerializationPolicyProvider mockPolicyProvider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		mockContext = new Mockery();

		mockPolicyProvider = mockContext.mock(SerializationPolicyProvider.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		mockPolicyProvider = null;

		mockContext = null;
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwtws.server.filter.serialization.ServerGWTSerializer#deserialize(java.lang.String)}
	 * .
	 * @throws SerializationException 
	 */
	public final void testDeserialize() throws SerializationException {
		final String serializedContent = "5|0|4|http://192.168.2.103:58365/de.csenk.gwtws.WebSocket.JUnit/|96917F6B45FCAB06345E921D8490F9EF|java.lang.String/2004016611|Hello World!|1|2|3|4|";
		final SerializationPolicy serializationPolicy = RPC.getDefaultSerializationPolicy();
		final ServerGWTSerializer serverSerializer = new ServerGWTSerializer(mockPolicyProvider);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockPolicyProvider).getSerializationPolicy(
						with(any(String.class)), with(any(String.class)));
					will(returnValue(serializationPolicy));
			}
		});
		
		Object obj = serverSerializer.deserialize(serializedContent);
		assertTrue(obj instanceof String);
		assertEquals("Hello World!", obj);
	}

	/**
	 * Test method for
	 * {@link de.csenk.gwtws.server.filter.serialization.ServerGWTSerializer#serialize(java.lang.Object)}
	 * .
	 * @throws SerializationException 
	 */
	public final void testSerialize() throws SerializationException {
		final ServerGWTSerializer serverSerializer = new ServerGWTSerializer(null);
		
		String str = serverSerializer.serialize("Hello World!");
		assertTrue(str.contains("java.lang.String"));
	}

}

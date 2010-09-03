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

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.SerializationException;

import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:15:52
 *
 */
public class ClientGWTSerializerTest extends GWTTestCase {

	/**
	 * Test method for {@link de.csenk.gwtws.client.filter.serialization.ClientGWTSerializer#deserialize(java.lang.String)}.
	 * @throws SerializationException 
	 */
	public final void testDeserialize() throws SerializationException {
		//Will create a deferred binding deriving from ClientGWTSerializer
		final GWTSerializer serializer = GWT.create(GWTSerializer.class);
		
		final String serializedContent = "[1337,2,1,[\"java.lang.Integer\",\"java.lang.Integer/3438268394\"],0,5]";
		Object obj = serializer.deserialize(serializedContent);
		
		assertTrue(obj instanceof Integer);
		assertEquals(new Integer(1337), obj);
	}

	/**
	 * Test method for {@link de.csenk.gwtws.client.filter.serialization.ClientGWTSerializer#serialize(java.lang.Object)}.
	 * @throws SerializationException 
	 */
	public final void testSerialize() throws SerializationException {
		//Will create a deferred binding deriving from ClientGWTSerializer
		final GWTSerializer serializer = GWT.create(GWTSerializer.class);
		
		final String serializedInteger = serializer.serialize(new Integer(1337));
		assertTrue(serializedInteger.contains("java.lang.Integer"));
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "de.csenk.gwtws.WebSocket";
	}

}

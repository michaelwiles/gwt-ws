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

import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.Filter.NextFilter;

/**
 * @author senk.christian@googlemail.com
 * @date 02.09.2010
 * @time 15:19:41
 *
 */
public class ServerGWTSerializationFilterTest extends TestCase {

	private final Mockery mockContext = new Mockery();
	
	/**
	 * Test method for {@link de.csenk.gwtws.server.filter.serialization.ServerGWTSerializationFilter#onMessageReceived(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)}.
	 * @throws Throwable 
	 */
	public final void testOnMessageReceived() throws Throwable {
		final String serializedContent = "5|0|3|http://192.168.2.103:62849/de.csenk.gwtws.WebSocket.JUnit/|96917F6B45FCAB06345E921D8490F9EF|java.lang.Integer/3438268394|1|2|3|3|1337|";
		
		final SerializationPolicyProvider mockPolicyProvider = mockContext.mock(SerializationPolicyProvider.class);
		final NextFilter mockNextFilter = mockContext.mock(NextFilter.class);
		final Connection mockConnection = mockContext.mock(Connection.class);
		
		final ServerGWTSerializationFilter filter = new ServerGWTSerializationFilter(mockPolicyProvider);
		final SerializationPolicy serializationPolicy = RPC.getDefaultSerializationPolicy();
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockPolicyProvider).getSerializationPolicy(
						with(any(String.class)), with(any(String.class)));
					will(returnValue(serializationPolicy));
				oneOf(mockNextFilter).onMessageReceived(mockConnection, with(any(Integer.class)));
			}
		});
		
		filter.onMessageReceived(mockNextFilter, mockConnection, serializedContent);
		mockContext.assertIsSatisfied();
	}

}

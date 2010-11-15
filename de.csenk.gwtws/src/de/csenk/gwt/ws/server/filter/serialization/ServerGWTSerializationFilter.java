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

package de.csenk.gwt.ws.server.filter.serialization;

import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

import de.csenk.gwt.ws.client.filter.serialization.ClientGWTSerializationFilter;
import de.csenk.gwt.ws.shared.Connection;

/**
 * @author senk.christian@googlemail.com
 * @date 01.09.2010
 * @time 17:38:04
 *
 */
public class ServerGWTSerializationFilter extends ClientGWTSerializationFilter {

	private ClassLoader contextClassLoader;
	
	/**
	 * @param serializationPolicyProvider
	 */
	public ServerGWTSerializationFilter(SerializationPolicyProvider serializationPolicyProvider) {
		super(new ServerGWTSerializer(serializationPolicyProvider));
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.client.filter.serialization.GWTClientSerializationFilter#onMessageReceived(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Throwable {
		assert message instanceof String;
		
		Object deserializedObject = null;
		ClassLoader originalContextCL = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(contextClassLoader);	
			deserializedObject = getSerializer().deserialize((String) message);	
		} finally {
			Thread.currentThread().setContextClassLoader(originalContextCL);
		}
		
		nextFilter.onMessageReceived(connection, deserializedObject);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwt.ws.shared.filter.FilterImpl#onConnectionOpened(de.csenk.gwt.ws.shared.Filter.NextFilter, de.csenk.gwt.ws.shared.Connection)
	 */
	@Override
	public void onConnectionOpened(NextFilter nextFilter, Connection connection)
			throws Throwable {
		contextClassLoader = Thread.currentThread().getContextClassLoader();
		
		super.onConnectionOpened(nextFilter, connection);
	}

}

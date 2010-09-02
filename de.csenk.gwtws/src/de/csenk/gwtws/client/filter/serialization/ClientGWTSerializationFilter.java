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

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.filter.FilterImpl;
import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;

/**
 * @author senk.christian@googlemail.com
 * @date 01.09.2010
 * @time 14:25:40
 *
 */
public class ClientGWTSerializationFilter extends FilterImpl {

	private final GWTSerializer serializer;
	
	public ClientGWTSerializationFilter(GWTSerializer gwtSerializer) {
		this.serializer = gwtSerializer;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.filter.FilterImpl#onMessageReceived(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(NextFilter nextFilter, Connection connection,
			Object message) throws Exception {
		assert message instanceof String;
		
		Object deserializedMessage = serializer.deserialize((String) message);
		nextFilter.onMessageReceived(connection, deserializedMessage);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.filter.FilterImpl#onSend(de.csenk.gwtws.shared.Filter.NextFilter, de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void onSend(NextFilter nextFilter, Connection connection,
			Object message) throws Exception {
		Object serializedMessage = serializer.serialize(message);
		nextFilter.onSend(connection, serializedMessage);
	}

	/**
	 * @return the serializer
	 */
	public GWTSerializer getSerializer() {
		return serializer;
	}
	
}

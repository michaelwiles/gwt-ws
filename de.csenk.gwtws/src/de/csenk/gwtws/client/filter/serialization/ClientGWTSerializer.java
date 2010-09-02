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

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamReader;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.Serializer;

import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;

/**
 * @author senk.christian@googlemail.com
 * @date 28.08.2010
 * @time 19:05:44
 * 
 */
public abstract class ClientGWTSerializer implements GWTSerializer {

	private final String moduleBaseURL;
	private final Serializer serializer;
	private final String serializationPolicyName;

	protected final Map<String, String> typeStringMap = new HashMap<String, String>();
	
	protected ClientGWTSerializer(String moduleBaseURL,
			String serializationPolicyName, Serializer serializer) {
		this.moduleBaseURL = moduleBaseURL;
		this.serializer = serializer;
		this.serializationPolicyName = serializationPolicyName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.filter.serialization.GWTSerializer#deserialize(
	 * java.lang.String)
	 */
	@Override
	public Object deserialize(String serializedContent)
			throws SerializationException {
		ClientSerializationStreamReader streamReader = new ClientSerializationStreamReader(serializer);
		streamReader.prepareToRead(serializedContent);
		
		String className = streamReader.readString();
		if (className.equals("java.lang.String")) {
			return streamReader.readString();
		} else {
			return streamReader.readObject();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.filter.serialization.GWTSerializer#serialize(java
	 * .lang.Object)
	 */
	@Override
	public String serialize(Object obj) throws SerializationException {
		ClientSerializationStreamWriter streamWriter = new ClientSerializationStreamWriter(
				serializer, moduleBaseURL, serializationPolicyName);
		streamWriter.prepareToWrite();
		
		String typeString = typeStringMap.get(obj.getClass().getName());
		if (typeString == null || typeString.length() <= 0) 
			throw new SerializationException("Serializer tried to serialize an unmapped class '" + obj.getClass().getName() + "'");
		
		streamWriter.writeString(typeString);
		
		if (obj instanceof String) {
			streamWriter.writeString((String) obj);
		} else {
			streamWriter.writeObject(obj);
		}
				
		return streamWriter.toString();
	}
}

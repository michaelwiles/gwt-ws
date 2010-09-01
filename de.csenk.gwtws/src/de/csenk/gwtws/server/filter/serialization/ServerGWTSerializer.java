/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.csenk.gwtws.server.filter.serialization;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamReader;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamWriter;

import de.csenk.gwtws.shared.filter.serialization.GWTSerializer;

/**
 * @author Christian
 * @date 28.08.2010
 * @time 19:13:33
 * 
 */
public class ServerGWTSerializer implements GWTSerializer {

	private SerializationPolicy serializationPolicy;
	private final SerializationPolicyProvider serializationPolicyProvider;
	
	/**
	 * @param serializationPolicyProvider
	 */
	public ServerGWTSerializer(SerializationPolicyProvider serializationPolicyProvider) {
		this.serializationPolicyProvider = serializationPolicyProvider;
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
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ServerSerializationStreamReader streamReader = new ServerSerializationStreamReader(classLoader, serializationPolicyProvider);
		streamReader.prepareToRead(serializedContent);

		if (serializationPolicy == null)
			serializationPolicy = streamReader.getSerializationPolicy();
		
		String objClassName = maybeDeobfuscate(streamReader, streamReader.readString());
		try {
			Class<?> objClass = Class.forName(objClassName, false, classLoader);
			return streamReader.deserializeValue(objClass);
		} catch (ClassNotFoundException e) {
			throw new SerializationException(e);
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
		if (serializationPolicy == null)
			throw new IllegalStateException("SerializationPolicy is unknown for this connection.");
		
		ServerSerializationStreamWriter streamWriter = new ServerSerializationStreamWriter(serializationPolicy);
		streamWriter.prepareToWrite();
		
		streamWriter.writeString(obj.getClass().getName());
		streamWriter.serializeValue(obj, obj.getClass());
		
		return streamWriter.toString();
	}

	/**
	 * Given a type identifier in the stream, attempt to deobfuscate it. Retuns
	 * the original identifier if deobfuscation is unnecessary or no mapping is
	 * known.
	 */
	private static String maybeDeobfuscate(
			ServerSerializationStreamReader streamReader, String name)
			throws SerializationException {
		int index;
		if ((index = name.indexOf('/')) != -1) {
			return name.substring(0, index);
		}
		return name;
	}

}

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

package de.csenk.gwt.ws.shared.filter.serialization;

import com.google.gwt.user.client.rpc.SerializationException;

/**
 * @author senk.christian@googlemail.com
 * @date 27.08.2010
 * @time 14:20:14
 *
 */
public interface GWTSerializer {

	/**
	 * @param serializedContent
	 * @return
	 */
	Object deserialize(String serializedContent) throws SerializationException;
	
	/**
	 * @param obj
	 * @return
	 */
	String serialize(Object obj) throws SerializationException;
	
}

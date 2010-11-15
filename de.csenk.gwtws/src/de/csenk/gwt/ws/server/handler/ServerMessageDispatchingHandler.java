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

package de.csenk.gwt.ws.server.handler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.csenk.gwt.ws.shared.handler.DefaultMessageDispatchingHandler;

/**
 * @author senk.christian@googlemail.com
 * @date 09.09.2010
 * @time 21:36:29
 *
 */
public class ServerMessageDispatchingHandler extends
		DefaultMessageDispatchingHandler {

	/**
	 * @param handlers
	 * @param type
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected synchronized Object findHandler(Map handlers, Class type, Set<Class> triedTypes) {
		if (handlers == null || type == null)
			return null;
		
		if (triedTypes != null && triedTypes.contains(type))
			return null;
		
		if (handlers.containsKey(type))
			return handlers.get(type);
		
		if (triedTypes == null)
			triedTypes = new HashSet<Class>();
		
		triedTypes.add(type);
		
		Class[] interfaces = type.getInterfaces();
		for (Class intf : interfaces) {
			Object handler = findHandler(handlers, intf, triedTypes);
			if (handler != null)
				return handler;
		}
		
		Class superClass = type.getSuperclass();
		return findHandler(handlers, superClass, null);
	}
	
}

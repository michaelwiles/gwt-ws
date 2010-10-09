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

package de.csenk.gwtws.client.handler;

import java.util.Map;
import java.util.Set;

import de.csenk.gwtws.shared.handler.DefaultMessageDispatchingHandler;

/**
 * @author senk.christian@googlemail.com
 * @date 09.09.2010
 * @time 21:40:02
 *
 */
public class ClientMessageDispatchingHandler extends
		DefaultMessageDispatchingHandler {

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.handler.DefaultMessageDispatchingHandler#findHandler(java.util.Map, java.lang.Class, java.util.Set)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Object findHandler(Map handlers, Class type, Set<Class> triedTypes) {
		if (handlers == null || type == null)
			return null;
		
		if (handlers.containsKey(type))
			return handlers.get(type);
		
		Class superClass = type.getSuperclass();
		return findHandler(handlers, superClass, null);
	}

}

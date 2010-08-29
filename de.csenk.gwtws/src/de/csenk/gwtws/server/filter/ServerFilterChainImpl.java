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

package de.csenk.gwtws.server.filter;

import java.util.ArrayList;
import java.util.List;

import de.csenk.gwtws.shared.IoFilter;
import de.csenk.gwtws.shared.IoFilterChain;

/**
 * @author Christian
 * @date 29.08.2010
 * @time 22:52:15
 *
 */
public class ServerFilterChainImpl implements IoFilterChain {
	
	private final List<IoFilter> filterList = new ArrayList<IoFilter>();

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.IoFilterChain#addLast(java.lang.String, de.csenk.gwtws.shared.IoFilter)
	 */
	@Override
	public synchronized void addLast(String filterName, IoFilter filter) {
		//TODO Do not ignore the filterName
		filterList.add(filter);
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.IoFilterChain#getFirst()
	 */
	@Override
	public synchronized IoFilter getFirst() {
		if (filterList.isEmpty())
			return null;
		
		return filterList.get(0);
	}

}

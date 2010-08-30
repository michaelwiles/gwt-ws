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

package de.csenk.gwtws.shared;

import de.csenk.gwtws.shared.Filter.NextFilter;

/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 14:08:49
 *
 */
public interface FilterChain {
	
	/**
	 * @param filterName
	 * @param filter
	 */
	void addLast(String filterName, Filter filter);
	
	/**
	 * @param connection
	 * @param message
	 */
	void fireSendMessage(Connection connection, Object message);
	
	/**
	 * @param connection
	 */
	void fireConnectionOpened(Connection connection);
	
	/**
	 * @param connection
	 */
	void fireConnectionClosed(Connection connection);
	
	/**
	 * @param connection
	 * @param caught
	 */
	void fireExceptionCaught(Connection connection, Throwable caught);
	
	/**
	 * @author Christian.Senk
	 * @date 30.08.2010
	 * @time 10:45:11
	 *
	 */
	public interface Entry {
		
		/**
		 * @return
		 */
		String getName();
		
		/**
		 * @return
		 */
		Filter getFilter();
		
		/**
		 * @return
		 */
		NextFilter getNextFilter();
		
	}
	
}

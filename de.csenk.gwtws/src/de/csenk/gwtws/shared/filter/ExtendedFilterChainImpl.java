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

package de.csenk.gwtws.shared.filter;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.ExtendedFilterChain;
import de.csenk.gwtws.shared.Sender;

/**
 * @author Christian
 * @date 29.08.2010
 * @time 22:52:15
 *
 */
public class ExtendedFilterChainImpl extends FilterChainImpl implements ExtendedFilterChain {
		
	/**
	 * @param connection
	 * @param socketSender
	 */
	public ExtendedFilterChainImpl(Connection connection, Sender socketSender) {
		super(connection, socketSender);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.ExtendedFilterChain#fireSendMessage(de.csenk.gwtws.shared.Connection, java.lang.Object)
	 */
	@Override
	public void fireSendMessage(Connection connection, Object message) {
		// TODO Auto-generated method stub
		
	}
	
	
}

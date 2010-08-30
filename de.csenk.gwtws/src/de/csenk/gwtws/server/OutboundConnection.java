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

package de.csenk.gwtws.server;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.FilterChain;
import de.csenk.gwtws.shared.Handler;

/**
 * @author Christian.Senk
 * @date 30.08.2010
 * @time 15:46:16
 *
 */
public class OutboundConnection implements Connection {

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#getFilterChain()
	 */
	@Override
	public FilterChain getFilterChain() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#getHandler()
	 */
	@Override
	public Handler getHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#send(java.lang.Object)
	 */
	@Override
	public void send(Object message) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.Connection#receive(java.lang.Object)
	 */
	@Override
	public void receive(Object message) {
		// TODO Auto-generated method stub
		
	}

}

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

/**
 * @author senk.christian@googlemail.com
 * @date 26.08.2010
 * @time 13:06:59
 *
 */
public interface Connection {

	/**
	 * Sends a message through this {@link Connection}.
	 * 
	 * @param message
	 */
	void send(Object message);
	
	/**
	 * @param message
	 */
	void receive(Object message);
	
	/**
	 * Closes this {@link Connection}.
	 */
	void close();
	
	/**
	 * @return
	 */
	Handler getHandler();
	
	/**
	 * @return
	 */
	FilterChain getFilterChain();
	
}

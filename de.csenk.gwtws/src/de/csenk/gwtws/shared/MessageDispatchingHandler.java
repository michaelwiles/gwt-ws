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

package de.csenk.gwtws.shared;

/**
 * @author Christian
 * @date 07.09.2010
 * @time 20:45:46
 *
 */
public interface MessageDispatchingHandler extends Handler {

	/**
	 * @param <E>
	 * @param clazz
	 * @param handler
	 */
	<E> void addReceivedMessageHandler(Class<E> clazz, MessageHandler<? super E> handler);
	
	/**
	 * @param <E>
	 * @param clazz
	 * @param handler
	 */
	<E extends Throwable> void addExceptionHandler(Class<E> clazz, ExceptionHandler<? super E> handler);

}

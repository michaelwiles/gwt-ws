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

package de.csenk.gwtws.shared.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.ExceptionHandler;
import de.csenk.gwtws.shared.MessageDispatchingHandler;
import de.csenk.gwtws.shared.MessageHandler;

/**
 * @author Christian
 * @date 08.09.2010
 * @time 11:08:11
 * 
 */
public abstract class DefaultMessageDispatchingHandler implements
		MessageDispatchingHandler {

	private final Map<Class<?>, MessageHandler<?>> receivedMessageHandlers = new HashMap<Class<?>, MessageHandler<?>>();
	private final Map<Class<?>, ExceptionHandler<?>> exceptionHandlers = new HashMap<Class<?>, ExceptionHandler<?>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.MessageDispatchingHandler#addReceivedMessageHandler
	 * (java.lang.Class, de.csenk.gwtws.shared.MessageHandler)
	 */
	@Override
	public synchronized <E> void addReceivedMessageHandler(Class<E> type,
			MessageHandler<? super E> handler) {
		if (receivedMessageHandlers.containsKey(type))
			return;

		receivedMessageHandlers.put(type, handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.MessageDispatchingHandler#addExceptionCaughtHandler
	 * (java.lang.Class, de.csenk.gwtws.shared.ExceptionHandler)
	 */
	@Override
	public synchronized <E extends Throwable> void addExceptionHandler(
			Class<E> type, ExceptionHandler<? super E> handler) {
		if (exceptionHandlers.containsKey(type))
			return;

		exceptionHandlers.put(type, handler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.Handler#onConnectionClosed(de.csenk.gwtws.shared
	 * .Connection)
	 */
	@Override
	public void onConnectionClosed(Connection connection) throws Throwable {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.Handler#onConnectionOpened(de.csenk.gwtws.shared
	 * .Connection)
	 */
	@Override
	public void onConnectionOpened(Connection connection) throws Throwable {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.Handler#onExceptionCaught(de.csenk.gwtws.shared
	 * .Connection, java.lang.Throwable)
	 */
	@Override
	public void onExceptionCaught(Connection connection, Throwable caught) {
		ExceptionHandler<Throwable> exceptionHandler = findExceptionHandler(caught
				.getClass());
		if (exceptionHandler != null) {
			exceptionHandler.handleException(connection, caught);
		}
	}

	/**
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ExceptionHandler<Throwable> findExceptionHandler(Class<?> type) {
		return (ExceptionHandler<Throwable>) findHandler(exceptionHandlers,
				type, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwtws.shared.Handler#onMessageReceived(de.csenk.gwtws.shared
	 * .Connection, java.lang.Object)
	 */
	@Override
	public void onMessageReceived(Connection connection, Object message)
			throws Throwable {
		MessageHandler<Object> messageHandler = findReceivedMessageHandler(message
				.getClass());
		if (messageHandler != null) {
			messageHandler.handleMessage(connection, message);
		}
	}

	/**
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MessageHandler<Object> findReceivedMessageHandler(Class<?> type) {
		return (MessageHandler<Object>) findHandler(receivedMessageHandlers,
				type, null);
	}

	/**
	 * @param handlers
	 * @param type
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected abstract Object findHandler(Map handlers, Class type, Set<Class> triedTypes);

}

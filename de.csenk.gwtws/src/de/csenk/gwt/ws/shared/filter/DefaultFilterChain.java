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

package de.csenk.gwt.ws.shared.filter;

import java.util.HashMap;
import java.util.Map;

import de.csenk.gwt.ws.shared.Connection;
import de.csenk.gwt.ws.shared.Filter;
import de.csenk.gwt.ws.shared.FilterChain;
import de.csenk.gwt.ws.shared.Filter.NextFilter;

/**
 * @author senk.christian@googlemail.com
 * @date 30.08.2010
 * @time 16:12:24
 * 
 */
public class DefaultFilterChain implements FilterChain {

	private final Map<String, Entry> filterMap = new HashMap<String, Entry>();

	private final Connection connection;

	private final EntryImpl tailEntry;
	private final EntryImpl headEntry;

	public DefaultFilterChain(Connection connection) {
		this.connection = connection;

		tailEntry = new EntryImpl(TailFilter.NAME, new TailFilter(), null, null);
		headEntry = new EntryImpl(HeadFilter.NAME, new HeadFilter(), null,
				tailEntry);
		tailEntry.prevEntry = headEntry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.csenk.gwt.ws.shared.FilterChain#addLast(java.lang.String,
	 * de.csenk.gwt.ws.shared.Filter)
	 */
	@Override
	public synchronized void addLast(String filterName, Filter filter) {
		checkNameIsNotReserved(filterName);
		
		EntryImpl filterEntry = insertFilter(tailEntry.prevEntry, filterName, filter);
		filterMap.put(filterName, filterEntry);
	}

	/**
	 * @param filterName
	 */
	private void checkNameIsNotReserved(String filterName) {
		if (filterMap.containsKey(filterName))
			throw new IllegalStateException("There is already a filter with name '" + filterName + "'");
	}

	/**
	 * @param prevEntry
	 * @param name
	 * @param filter
	 */
	private synchronized EntryImpl insertFilter(EntryImpl prevEntry, String name, Filter filter) {
		EntryImpl nextEntry = prevEntry.nextEntry;
		EntryImpl newEntry = new EntryImpl(name, filter, prevEntry, nextEntry);
		
		prevEntry.nextEntry = newEntry;
		nextEntry.prevEntry = newEntry;
		
		return newEntry;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwt.ws.shared.FilterChain#fireSendMessage(de.csenk.gwt.ws.shared
	 * .Connection, java.lang.Object)
	 */
	@Override
	public synchronized void fireSend(Object message) {
		callPreviousSend(tailEntry, message);
	}

	/**
	 * @param filterEntry
	 * @param connection
	 * @param message
	 */
	private void callPreviousSend(Entry filterEntry, Object message) {
		if (filterEntry == null)
			return;
		
		try {
			Filter filter = filterEntry.getFilter();
			NextFilter nextFilter = filterEntry.getNextFilter();

			filter.onSend(nextFilter, connection, message);
		} catch (Throwable e) {
			fireExceptionCaught(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwt.ws.shared.FilterChain#fireExceptionCaught(de.csenk.gwt.ws.
	 * shared.Connection, java.lang.Throwable)
	 */
	@Override
	public synchronized void fireExceptionCaught(Throwable caught) {
		callNextExceptionCaught(headEntry, caught);
	}

	/**
	 * @param filterEntry
	 * @param connection
	 * @param message
	 */
	private void callNextExceptionCaught(Entry filterEntry, Throwable caught) {
		if (filterEntry == null)
			return;
		
		try {
			Filter filter = filterEntry.getFilter();
			NextFilter nextFilter = filterEntry.getNextFilter();

			filter.onExceptionCaught(nextFilter, connection, caught);
		} catch (Throwable e) {
			fireExceptionCaught(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwt.ws.shared.FilterChain#fireConnectionClosed(de.csenk.gwt.ws
	 * .shared.Connection)
	 */
	@Override
	public synchronized void fireConnectionClosed() {
		callNextConnectionClosed(headEntry);
	}

	/**
	 * @param filterEntry
	 * @param connection
	 * @param message
	 */
	private void callNextConnectionClosed(Entry filterEntry) {
		if (filterEntry == null)
			return;
		
		try {
			Filter filter = filterEntry.getFilter();
			NextFilter nextFilter = filterEntry.getNextFilter();

			filter.onConnectionClosed(nextFilter, connection);
		} catch (Throwable e) {
			fireExceptionCaught(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwt.ws.shared.FilterChain#fireConnectionOpened(de.csenk.gwt.ws
	 * .shared.Connection)
	 */
	@Override
	public synchronized void fireConnectionOpened() {
		callNextConnectionOpened(headEntry);
	}

	/**
	 * @param filterEntry
	 * @param connection
	 * @param message
	 */
	private void callNextConnectionOpened(Entry filterEntry) {
		if (filterEntry == null)
			return;
		
		try {
			Filter filter = filterEntry.getFilter();
			NextFilter nextFilter = filterEntry.getNextFilter();

			filter.onConnectionOpened(nextFilter, connection);
		} catch (Throwable e) {
			fireExceptionCaught(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.csenk.gwt.ws.shared.FilterChain#fireMessageReceived(de.csenk.gwt.ws.
	 * shared.Connection, java.lang.Object)
	 */
	@Override
	public synchronized void fireMessageReceived(Object message) {
		callNextMessageReceived(headEntry, message);
	}

	/**
	 * @param filterEntry
	 * @param connection
	 * @param message
	 */
	private void callNextMessageReceived(Entry filterEntry, Object message) {
		if (filterEntry == null)
			return;
		
		try {
			Filter filter = filterEntry.getFilter();
			NextFilter nextFilter = filterEntry.getNextFilter();

			filter.onMessageReceived(nextFilter, connection, message);
		} catch (Throwable e) {
			fireExceptionCaught(e);
		}
	}

	/**
	 * @author senk.christian@googlemail.com
	 * @date 30.08.2010
	 * @time 10:50:20
	 * 
	 */
	private class EntryImpl implements Entry {

		private final String name;
		private final Filter filter;
		private final NextFilter nextFilter;

		private EntryImpl prevEntry;
		private EntryImpl nextEntry;

		/**
		 * @param name
		 * @param filter
		 * @param prevEntry
		 * @param nextEntry
		 */
		public EntryImpl(String name, Filter filter, EntryImpl prevEntry,
				EntryImpl nextEntry) {
			this.name = name;
			this.filter = filter;
			this.prevEntry = prevEntry;
			this.nextEntry = nextEntry;

			this.nextFilter = createNextFilter();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.csenk.gwt.ws.shared.FilterChain.Entry#getFilter()
		 */
		@Override
		public Filter getFilter() {
			return filter;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.csenk.gwt.ws.shared.FilterChain.Entry#getName()
		 */
		@Override
		public String getName() {
			return name;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.csenk.gwt.ws.shared.FilterChain.Entry#getNextFilter()
		 */
		@Override
		public NextFilter getNextFilter() {
			return nextFilter;
		}

		/**
		 * @return
		 */
		private NextFilter createNextFilter() {
			return new NextFilter() {

				@Override
				public void onSend(Connection connection, Object message)
						throws Exception {
					callPreviousSend(prevEntry, message);
				}

				@Override
				public void onMessageReceived(Connection connection,
						Object message) throws Exception {
					callNextMessageReceived(nextEntry, message);
				}

				@Override
				public void onExceptionCaught(Connection connection, Throwable caught) {
					callNextExceptionCaught(nextEntry, caught);
				}

				@Override
				public void onConnectionOpened(Connection connection)
						throws Exception {
					callNextConnectionOpened(nextEntry);
				}

				@Override
				public void onConnectionClosed(Connection connection)
						throws Exception {
					callNextConnectionClosed(nextEntry);
				}

			};
		}

	}

}

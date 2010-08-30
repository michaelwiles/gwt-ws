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

import java.util.HashMap;
import java.util.Map;

import de.csenk.gwtws.shared.Connection;
import de.csenk.gwtws.shared.Filter;
import de.csenk.gwtws.shared.FilterChain;
import de.csenk.gwtws.shared.Sender;
import de.csenk.gwtws.shared.Filter.NextFilter;

/**
 * @author Christian.Senk
 * @date 30.08.2010
 * @time 16:12:24
 *
 */
public abstract class FilterChainImpl implements FilterChain {

	private final Map<String, Entry> filterMap = new HashMap<String, Entry>();

	private final EntryImpl tailEntry;
	private final EntryImpl headEntry;
	
	public FilterChainImpl(Connection connection, Sender socketSender) {
		tailEntry = new EntryImpl(TailFilter.NAME, new TailFilter(), null, null);
		headEntry = new EntryImpl(HeadFilter.NAME, new HeadFilter(socketSender), null, tailEntry);
		tailEntry.prevEntry = headEntry;
	}
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.shared.FilterChain#addLast(java.lang.String, de.csenk.gwtws.shared.Filter)
	 */
	@Override
	public void addLast(String filterName, Filter filter) {
		// TODO Auto-generated method stub

	}

	/**
	 * @author Christian.Senk
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

		/* (non-Javadoc)
		 * @see de.csenk.gwtws.shared.FilterChain.Entry#getFilter()
		 */
		@Override
		public Filter getFilter() {
			return filter;
		}

		/* (non-Javadoc)
		 * @see de.csenk.gwtws.shared.FilterChain.Entry#getName()
		 */
		@Override
		public String getName() {
			return name;
		}

		/* (non-Javadoc)
		 * @see de.csenk.gwtws.shared.FilterChain.Entry#getNextFilter()
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
				public void onSendMessage(Connection connection, Object message)
						throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onMessageSent(Connection connection, Object message)
						throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onMessageReceived(Connection connection, Object message)
						throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onExceptionCaught(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onConnectionOpened(Connection connection) throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onConnectionClosed(Connection connection) throws Exception {
					// TODO Auto-generated method stub
					
				}
			};
		}
		
	}
	
}

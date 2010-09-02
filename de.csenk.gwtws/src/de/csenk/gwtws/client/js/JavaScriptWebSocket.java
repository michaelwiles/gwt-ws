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

package de.csenk.gwtws.client.js;

import com.google.gwt.core.client.JavaScriptObject;

import de.csenk.gwtws.client.WebSocket;
import de.csenk.gwtws.client.WebSocketCallback;

/**
 * @author senk.christian@googlemail.com
 * @date 25.08.2010
 * @time 12:04:46
 *
 * Represents the GWT version of the JavaScripts new WebSocket component.
 */
@SuppressWarnings("unused")
public class JavaScriptWebSocket implements WebSocket {
	
	/**
	 * @return <code>True</code> if the WebSocket component is supported by the current browser
	 */
	public static native boolean IsSupported() /*-{
		if ($wnd.WebSocket) {
			return true;
		} else {
			return false;
		}
	}-*/;
	
	private final JavaScriptObject jsWebSocket;
	private final WebSocketCallback socketCallback;
	
	/**
	 * Creates a new {@link JavaScriptWebSocket} that connects immediately to the end-point URL.
	 * 
	 * @param url
	 * @param socketCallback
	 */
	public JavaScriptWebSocket(final String url, final WebSocketCallback socketCallback) {
		assert url != null;
		assert socketCallback != null;
		assert IsSupported();
		
		this.socketCallback = socketCallback;
		this.jsWebSocket = createJSWebSocket(url, this);
	}
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.client.js.WebSocket#send(java.lang.String)
	 */
	@Override
	public native void send(String message) /*-{
		if (message == null)
			return;
		
		this.@de.csenk.gwtws.client.js.JavaScriptWebSocket::jsWebSocket.send(message);
	}-*/;
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.client.js.WebSocket#close()
	 */
	@Override
	public native void close() /*-{
		this.@de.csenk.gwtws.client.js.JavaScriptWebSocket::jsWebSocket.close();
	}-*/;
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.client.js.WebSocket#getBufferedAmount()
	 */
	@Override
	public native int getBufferedAmount() /*-{
		return this.@de.csenk.gwtws.client.js.JavaScriptWebSocket::jsWebSocket.bufferedAmount;
	}-*/;
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.client.js.WebSocket#getReadyState()
	 */
	@Override
	public native int getReadyState() /*-{
		return this.@de.csenk.gwtws.client.js.JavaScriptWebSocket::jsWebSocket.readyState;
	}-*/;
	
	/* (non-Javadoc)
	 * @see de.csenk.gwtws.client.js.WebSocket#getURL()
	 */
	@Override
	public native String getURL() /*-{
		return this.@de.csenk.gwtws.client.js.JavaScriptWebSocket::jsWebSocket.url;
	}-*/;
	
	/**
	 * Creates the JavaScript WebSocket component and set's all callback handlers.
	 * 
	 * @param url
	 */
	private native JavaScriptObject createJSWebSocket(final String url, final WebSocket webSocket) /*-{
		var jsWebSocket = new WebSocket(url);
		
		jsWebSocket.onopen = function() {
			webSocket.@de.csenk.gwtws.client.js.JavaScriptWebSocket::onOpen()();
		}
		
		jsWebSocket.onclose = function() {
			webSocket.@de.csenk.gwtws.client.js.JavaScriptWebSocket::onClose()();
		}
		
		jsWebSocket.onerror = function() {
			webSocket.@de.csenk.gwtws.client.js.JavaScriptWebSocket::onError()();
		}
		
		jsWebSocket.onmessage = function(socketResponse) {
			if (socketResponse.data) {
				webSocket.@de.csenk.gwtws.client.js.JavaScriptWebSocket::onMessage(Ljava/lang/String;)(socketResponse.data);
			}
		}
		
		return jsWebSocket;
	}-*/;
	
	/**
	 * 
	 */
	private void onOpen() {
		socketCallback.onOpen(this);
	}
	
	/**
	 * @param message
	 */
	private void onMessage(String message) {
		socketCallback.onMessage(this, message);
	}
	
	/**
	 * 
	 */
	private void onError() {
		socketCallback.onError(this);
	}
	
	/**
	 * 
	 */
	private void onClose() {
		socketCallback.onClose(this);
	}
	
}

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

package de.csenk.websocket.client.js;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Christian.Senk
 * @date 25.08.2010
 * @time 12:04:46
 *
 * Represents the GWT version of the JavaScripts new WebSocket component.
 */
@SuppressWarnings("unused")
public class WebSocket {
	
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
	 * Creates a new {@link WebSocket} that connects immediately to the end-point URL.
	 * 
	 * @param url
	 * @param socketCallback
	 */
	public WebSocket(final String url, final WebSocketCallback socketCallback) {
		assert url != null;
		assert socketCallback != null;
		assert IsSupported();
		
		this.socketCallback = socketCallback;
		this.jsWebSocket = createJSWebSocket(url, this);
	}
	
	/**
	 * Sends a message.
	 * 
	 * @param message
	 */
	public native void send(String message) /*-{
		if (message == null)
			return;
		
		this.@de.csenk.websocket.client.js.WebSocket::jsWebSocket.send(message);
	}-*/;
	
	/**
	 * Closes this {@link WebSocket}.
	 */
	public native void close() /*-{
		this.@de.csenk.websocket.client.js.WebSocket::jsWebSocket.close();
	}-*/;
	
	/**
	 * @return the bufferedAmount property of the underlying JavaScript WebSocket.
	 */
	public native int getBufferedAmount() /*-{
		return this.@de.csenk.websocket.client.js.WebSocket::jsWebSocket.bufferedAmount;
	}-*/;
	
	/**
	 * @return the readyState property of the underlying JavaScript WebSocket.
	 */
	public native int getReadyState() /*-{
		return this.@de.csenk.websocket.client.js.WebSocket::jsWebSocket.readyState;
	}-*/;
	
	/**
	 * @return the url property of the underlying JavaScript WebSocket.
	 */
	public native String getURL() /*-{
		return this.@de.csenk.websocket.client.js.WebSocket::jsWebSocket.url;
	}-*/;
	
	/**
	 * Creates the JavaScript WebSocket component and set's all callback handlers.
	 * 
	 * @param url
	 */
	private native JavaScriptObject createJSWebSocket(final String url, final WebSocket webSocket) /*-{
		var jsWebSocket = new WebSocket(url);
		
		jsWebSocket.onopen = function() {
			webSocket.@de.csenk.websocket.client.js.WebSocket::onOpen()();
		}
		
		jsWebSocket.onclose = function() {
			webSocket.@de.csenk.websocket.client.js.WebSocket::onClose()();
		}
		
		jsWebSocket.onerror = function() {
			webSocket.@de.csenk.websocket.client.js.WebSocket::onError()();
		}
		
		jsWebSocket.onmessage = function(socketResponse) {
			if (socketResponse.data) {
				webSocket.@de.csenk.websocket.client.js.WebSocket::onMessage(Ljava/lang/String;)(socketResponse.data);
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

# WebSockets for GWT #
This project is intended to create a bit more than just a simple implementation of the html5 websocket-standard for gwt. Indeed there is just a plain implementation you are free to use, just take a look in the "de.csenk.gwtws.client.js" subpackage.

But why use just plain websockets? There are several drawbacks like you are only be able to transmit UTF-8 strings through the websocket connection. Inspired by the http://mina.apache.org project **gwt-ws** provides some high level functionality to handle websockets and transmit data.

## Getting Started ##

### Prepare GWT ###
In order to use WebSockets make sure your servlet container e.g. jetty supports this. Until now the gwt-ws framework itself only publishes an implementation of websockets for jetty. The default embedded jetty hasn't support for websockets because websockets are enabled since version 7. GWT 2.0.3 uses a jetty 6.x instance. Just take a look at the **gwt-jetty** project (http://code.google.com/p/gwt-jetty/) to enable other versions of jetty.

### Setup server side ###
Implement the abstract class `org.eclipse.jetty.websocket.WebSocketServlet` in a custom servlet and override the method `doWebSocketConnect`. Lets say you named the new class `WebSocketServletImpl`. Create the necessary servlet mappings.

```
<!-- Servlets -->
<servlet>
	<servlet-name>webSocket</servlet-name>
	<servlet-class>de.csenk.gwtws.demo.server.WebSocketServletImpl</servlet-class>
</servlet>

<servlet-mapping>
	<servlet-name>webSocket</servlet-name>
	<url-pattern>/de_csenk_gwtws_demo/webSocket</url-pattern>
</servlet-mapping>
```

In your servlet implementation you must return an implementation of `org.eclipse.jetty.websocket.WebSocket`, the framework offers a class with much more high level funtionality. Just return an instance of `JettyWebSocketConnection`. This could look like this.

```
/* (non-Javadoc)
 * @see org.eclipse.jetty.websocket.WebSocketServlet#doWebSocketConnect(javax.servlet.http.HttpServletRequest, java.lang.String)
 */
@Override
protected WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
	return new JettyWebSocketConnection(new Handler() {

		@Override
		public void onConnectionClosed(Connection connection)
				throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionOpened(Connection connection)
				throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onExceptionCaught(Connection connection,
				Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMessageReceived(Connection connection, Object message)
				throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	});
}
```

Thats all, you are ready to receive messages through a web socket.

### Setup client side ###
Like the server side, it's quite simple as on the server side. Just add the following self explaining snippet to your entry point implementation. Did you see that the client and the server use the same Handler interface?

```
@Override
public void onModuleLoad() {
	if (!JavaScriptWebSocket.IsSupported())
		return;
	
	//webSocketURL looks like "ws://127.0.0.1:8888/de_csenk_gwtws_demo/webSocket"
	String webSocketURL = GWT.getModuleBaseURL().replace("http", "ws") + "webSocket";
	JavaScriptWebSocketConnection webSocketConnection = new JavaScriptWebSocketConnection(webSocketURL, new Handler() {

		@Override
		public void onConnectionClosed(Connection connection)
				throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionOpened(Connection connection)
				throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onExceptionCaught(Connection connection,
				Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMessageReceived(Connection connection, Object message)
				throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	});
}
```

### Sending messages ###
That was easy, you can now send messages back and forth. Just use the connection interface to do this. The best point for this task is the "onConnectionOpened" event.

```
@Override
public void onConnectionOpened(Connection connection)
		throws Exception {
	connection.send("Hello World");
}
```

On the server side you can receive this message and do whatever you want. For example you can print it to System.out

```
@Override
public void onMessageReceived(Connection connection, Object message)
		throws Exception {
	System.out.println(message);
}
```

### Using Filters ###
The tasks covered until now are also able to realize with the standard web socket implementation. **gwt-ws** provides more high level features. For example you are able to filter your received and sending messages. All you have to do is define some filter logic. **gwt-ws** ships with some usefull filters like a `GWTSerializationFilter`. This filter takes some objects, serializes it down to strings and then forwards it to the next filter. Look at [GWTSerializationFilter](GWTSerializationFilter.md) for the usage of this filter.

`JettyWebSocketConnection` on the server side and `JavaScriptWebSocketConnection` on the client side. Both implements the `Connection` interface. Connections have attached filter chains ready for configuring.

```
Connection connection = new JavaScriptWebSocketConnection([...]);
FilterChain filterChain = connection.getFilterChain();

filterChain.addLast("logging", new ClientLoggingFilter());
```

This snipped adds a filter right before the string is going to be send through the web socket. It utilizes [gwt-log](http://code.google.com/p/gwt-log/) to log incoming and outgoing strings.
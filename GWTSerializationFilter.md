GWT-RPC utilizes a serialization mechanism to serialize high level objects into strings intended for transmission over http. gwt-ws takes this serialization and provides filters for server and client side.

On the client side, the mechanism needs to know which classes are serializable. This is done by deferred bindings. You define an interface extending `GWTSerializer` and annotate this with `Serializable`. This annotation takes an array of classes which are serializable.

```
@Serializable({SomeClass.class, SomeInterface.class, })
public interface CustomSerializer extends GWTSerializer {

}
```

`GWT.create()` lets create gwt-ws an deferred implementation of the GWTSerializer interface. Use this implementation in the client side filter.

```
GWTSerializer serializer = GWT.create(MessageSerializer.class);
Filter serializationFilter = new ClientGWTSerializationFilter(serializer);
```

The deferred binding process generates also a serialization policy file stored on the server side. With every serialization the client sends information about how to find this file to the server. The server loads this file and can use this file for the next serialization. In order to find this file, an instance of `SerializationPolicyProvider` is used by the server side filter. In most cases the policy file lies related to some servlet. gwt-ws provides an implementation with `ServletContextSerializationPolicyProvider` which takes a `ServletContext` to load a policy file.

```
SerializationPolicyProvider policyProvider = new ServletContextSerializationPolicyProvider(getServletContext());
Filter serialFilter = new ServerGWTSerializationFilter(policyProvider);
```

You are ready to add the filters to your chain. For more examples take a look at the demo project.
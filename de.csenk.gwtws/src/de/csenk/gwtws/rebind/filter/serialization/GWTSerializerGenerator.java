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

package de.csenk.gwtws.rebind.filter.serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.GeneratedResource;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.javac.TypeOracleMediator;
import com.google.gwt.dev.util.Util;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.linker.rpc.RpcPolicyFileArtifact;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;
import com.google.gwt.user.server.rpc.impl.TypeNameObfuscator;

import de.csenk.gwtws.client.filter.serialization.ClientGWTSerializer;
import de.csenk.gwtws.rebind.filter.serialization.com.google.gwt.user.rebind.rpc.BlacklistTypeFilter;
import de.csenk.gwtws.rebind.filter.serialization.com.google.gwt.user.rebind.rpc.SerializableTypeOracle;
import de.csenk.gwtws.rebind.filter.serialization.com.google.gwt.user.rebind.rpc.SerializableTypeOracleBuilder;
import de.csenk.gwtws.rebind.filter.serialization.com.google.gwt.user.rebind.rpc.SerializationUtils;
import de.csenk.gwtws.rebind.filter.serialization.com.google.gwt.user.rebind.rpc.Shared;
import de.csenk.gwtws.rebind.filter.serialization.com.google.gwt.user.rebind.rpc.TypeFilter;
import de.csenk.gwtws.rebind.filter.serialization.com.google.gwt.user.rebind.rpc.TypeSerializerCreator;
import de.csenk.gwtws.shared.filter.serialization.Serializable;

/**
 * @author senk.christian@googlemail.com
 * @date 27.08.2010
 * @time 14:25:20
 * 
 */
public class GWTSerializerGenerator extends Generator {

	private static final String IMPLEMENTATION_SUFFIX = "_Impl";

	/**
	 * Compares {@link JType}s according to their qualified source names.
	 */
	static final Comparator<JType> JTYPE_COMPARATOR = new Comparator<JType>() {
		public int compare(JType t1, JType t2) {
			return t1.getQualifiedSourceName().compareTo(
					t2.getQualifiedSourceName());
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger
	 * , com.google.gwt.core.ext.GeneratorContext, java.lang.String)
	 */
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		TypeOracle typeOracle = context.getTypeOracle();
		assert (typeOracle != null);

		JClassType serializerInterface = typeOracle.findType(typeName);
		if (serializerInterface == null) {
			logger.log(TreeLogger.ERROR, "Unable to find metadata for type '"
					+ typeName + "'", null);
			throw new UnableToCompleteException();
		}

		if (serializerInterface.isInterface() == null) {
			logger.log(TreeLogger.ERROR, serializerInterface
					.getQualifiedSourceName()
					+ " is not an interface", null);
			throw new UnableToCompleteException();
		}

		TreeLogger serializerLogger = logger.branch(TreeLogger.DEBUG,
				"Generating implementation for serializer '"
						+ serializerInterface.getQualifiedSourceName() + "'",
				null);

		return createSerializer(serializerLogger, context, serializerInterface);
	}

	/**
	 * @param serializerLogger
	 * @param context
	 * @param serializerInterface
	 * @return
	 */
	private String createSerializer(TreeLogger serializerLogger,
			GeneratorContext context, JClassType serializerInterface)
			throws UnableToCompleteException {
		TypeOracle typeOracle = context.getTypeOracle();
		SourceWriter sourceWriter = getSourceWriter(serializerLogger, context,
				serializerInterface);
		if (sourceWriter == null)
			return getImplementationQualifiedName(serializerInterface);

		// TODO validate serializerInterface

		final PropertyOracle propertyOracle = context.getPropertyOracle();

		// Load the blacklist/whitelist
		TypeFilter blacklistTypeFilter = new BlacklistTypeFilter(
				serializerLogger, propertyOracle);

		// Determine the set of serializable types
		SerializableTypeOracleBuilder typesSentFromBrowserBuilder = new SerializableTypeOracleBuilder(
				serializerLogger, propertyOracle, typeOracle);
		typesSentFromBrowserBuilder.setTypeFilter(blacklistTypeFilter);
		SerializableTypeOracleBuilder typesSentToBrowserBuilder = new SerializableTypeOracleBuilder(
				serializerLogger, propertyOracle, typeOracle);
		typesSentToBrowserBuilder.setTypeFilter(blacklistTypeFilter);

		addRoots(serializerLogger, typeOracle, typesSentFromBrowserBuilder,
				typesSentToBrowserBuilder, serializerInterface);

		// TODO Type name ellision?

		// Create a resource file to receive all of the serialization
		// information
		// computed by STOB and mark it as private so it does not end up in the
		// output.
		OutputStream pathInfo = context.tryCreateResource(serializerLogger,
				serializerInterface.getQualifiedSourceName() + ".rpc.log");
		PrintWriter writer = null;
		SerializableTypeOracle typesSentFromBrowser;
		SerializableTypeOracle typesSentToBrowser;
		try {
			writer = new PrintWriter(pathInfo);

			typesSentFromBrowserBuilder.setLogOutputStream(pathInfo);
			typesSentToBrowserBuilder.setLogOutputStream(pathInfo);

			writer.write("====================================\n");
			writer.write("Types potentially sent from browser:\n");
			writer.write("====================================\n\n");
			writer.flush();
			typesSentFromBrowser = typesSentFromBrowserBuilder
					.build(serializerLogger);

			writer.write("===================================\n");
			writer.write("Types potentially sent from server:\n");
			writer.write("===================================\n\n");
			writer.flush();
			typesSentToBrowser = typesSentToBrowserBuilder
					.build(serializerLogger);

			if (pathInfo != null) {
				context.commitResource(serializerLogger, pathInfo).setPrivate(
						true);
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		
		Map<JType, String> typeStrings = generateTypeHandlers(serializerLogger,
				context, typesSentFromBrowser, typesSentToBrowser,
				serializerInterface);

		String serializationPolicyStrongName = writeSerializationPolicyFile(
				serializerLogger, context, typesSentFromBrowser,
				typesSentToBrowser, typeStrings, serializerInterface);

		String serializerInterfaceName = TypeOracleMediator
				.computeBinaryClassName(serializerInterface);
		generateFields(sourceWriter, typesSentFromBrowser,
				serializationPolicyStrongName, serializerInterfaceName,
				serializerInterface);
		
		generateContructor(sourceWriter, serializerInterface, typeStrings);
		
		sourceWriter.commit(serializerLogger);
		return getImplementationQualifiedName(serializerInterface);
	}

	/**
	 * Generate any fields required by the proxy.
	 */
	private void generateFields(SourceWriter srcWriter,
			SerializableTypeOracle serializableTypeOracle,
			String serializationPolicyStrongName,
			String remoteServiceInterfaceName, JClassType serializerInterface) {
		// Initialize a field with binary name of the remote service interface
		srcWriter
				.println("private static final String INTERFACE_NAME = "
						+ "\"" + remoteServiceInterfaceName + "\";");
		srcWriter
				.println("private static final String SERIALIZATION_POLICY =\""
						+ serializationPolicyStrongName + "\";");
		String typeSerializerName = SerializationUtils
				.getTypeSerializerQualifiedName(serializerInterface);
		srcWriter.println("private static final " + typeSerializerName
				+ " SERIALIZER = new " + typeSerializerName + "();");
		srcWriter.println();
	}

	/**
	 * @param serializerLogger
	 * @param context
	 * @param typesSentFromBrowser
	 * @param typesSentToBrowser
	 * @param typeStrings
	 * @return
	 */
	private String writeSerializationPolicyFile(TreeLogger logger,
			GeneratorContext ctx, SerializableTypeOracle serializationSto,
			SerializableTypeOracle deserializationSto,
			Map<JType, String> typeStrings, JClassType serializerInterface)
			throws UnableToCompleteException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(
					baos,
					SerializationPolicyLoader.SERIALIZATION_POLICY_FILE_ENCODING);
			TypeOracle oracle = ctx.getTypeOracle();
			PrintWriter pw = new PrintWriter(osw);

			JType[] serializableTypes = unionOfTypeArrays(serializationSto
					.getSerializableTypes(), deserializationSto
					.getSerializableTypes(),
					new JType[] { serializerInterface });

			for (int i = 0; i < serializableTypes.length; ++i) {
				JType type = serializableTypes[i];
				String binaryTypeName = TypeOracleMediator
						.computeBinaryClassName(type);
				pw.print(binaryTypeName);
				pw.print(", "
						+ Boolean.toString(deserializationSto
								.isSerializable(type)));
				pw.print(", "
						+ Boolean.toString(deserializationSto
								.maybeInstantiated(type)));
				pw.print(", "
						+ Boolean.toString(serializationSto
								.isSerializable(type)));
				pw.print(", "
						+ Boolean.toString(serializationSto
								.maybeInstantiated(type)));
				pw.print(", " + typeStrings.get(type));

				/*
				 * Include the serialization signature to bump the RPC file name
				 * if obfuscated identifiers are used.
				 */
				pw.print(", "
						+ SerializationUtils.getSerializationSignature(oracle,
								type));
				pw.print('\n');

				/*
				 * Emit client-side field information for classes that may be
				 * enhanced on the server. Each line consists of a
				 * comma-separated list containing the keyword '@ClientFields',
				 * the class name, and a list of all potentially serializable
				 * client-visible fields.
				 */
				if ((type instanceof JClassType)
						&& ((JClassType) type).isEnhanced()) {
					JField[] fields = ((JClassType) type).getFields();
					JField[] rpcFields = new JField[fields.length];
					int numRpcFields = 0;
					for (JField f : fields) {
						if (f.isTransient() || f.isStatic() || f.isFinal()) {
							continue;
						}
						rpcFields[numRpcFields++] = f;
					}

					pw.print(SerializationPolicyLoader.CLIENT_FIELDS_KEYWORD);
					pw.print(',');
					pw.print(binaryTypeName);
					for (int idx = 0; idx < numRpcFields; idx++) {
						pw.print(',');
						pw.print(rpcFields[idx].getName());
					}
					pw.print('\n');
				}
			}

			// Closes the wrapped streams.
			pw.close();

			byte[] serializationPolicyFileContents = baos.toByteArray();
			String serializationPolicyName = Util
					.computeStrongName(serializationPolicyFileContents);

			String serializationPolicyFileName = SerializationPolicyLoader
					.getSerializationPolicyFileName(serializationPolicyName);
			OutputStream os = ctx.tryCreateResource(logger,
					serializationPolicyFileName);
			if (os != null) {
				os.write(serializationPolicyFileContents);
				GeneratedResource resource = ctx.commitResource(logger, os);

				/*
				 * Record which proxy class created the resource. A manifest
				 * will be emitted by the RpcPolicyManifestLinker.
				 */
				ctx
						.commitArtifact(logger, new RpcPolicyFileArtifact(
								serializerInterface.getQualifiedSourceName(),
								resource));
			} else {
				logger.log(TreeLogger.TRACE,
						"SerializationPolicy file for RemoteService '"
								+ serializerInterface.getQualifiedSourceName()
								+ "' already exists; no need to rewrite it.",
						null);
			}

			return serializationPolicyName;
		} catch (UnsupportedEncodingException e) {
			logger
					.log(
							TreeLogger.ERROR,
							SerializationPolicyLoader.SERIALIZATION_POLICY_FILE_ENCODING
									+ " is not supported", e);
			throw new UnableToCompleteException();
		} catch (IOException e) {
			logger.log(TreeLogger.ERROR, null, e);
			throw new UnableToCompleteException();
		}
	}

	/**
	 * Take the union of two type arrays, and then sort the results
	 * alphabetically.
	 */
	private static JType[] unionOfTypeArrays(JType[]... types) {
		Set<JType> typesList = new HashSet<JType>();
		for (JType[] a : types) {
			typesList.addAll(Arrays.asList(a));
		}
		JType[] serializableTypes = typesList.toArray(new JType[0]);
		Arrays.sort(serializableTypes, JTYPE_COMPARATOR);
		return serializableTypes;
	}

	/**
	 * Generate the proxy constructor and delegate to the superclass constructor
	 * using the default address for the
	 * {@link com.google.gwt.user.client.rpc.RemoteService RemoteService}.
	 */
	private void generateContructor(SourceWriter srcWriter, JClassType serializerInterface, Map<JType, String> typeStrings) {
		srcWriter.println("public " + getImplementationSimpleName(serializerInterface) + "() {");
		srcWriter.indent();
		srcWriter.println("super(GWT.getModuleBaseURL(),");
		srcWriter.indent();
		srcWriter.println("SERIALIZATION_POLICY, ");
		srcWriter.println("SERIALIZER);");
		srcWriter.outdent();
		
		srcWriter.println();
		for (Entry<JType, String> typeEntry : typeStrings.entrySet()) {
			srcWriter.print("typeStringMap.put(\"");
			srcWriter.print(typeEntry.getKey().getQualifiedSourceName() + "\", \"");
			srcWriter.print(typeEntry.getValue());
			srcWriter.println("\");");
		}
		
		srcWriter.outdent();
		srcWriter.println("}");
	}

	/**
	 * @param logger
	 * @param context
	 * @param typesSentFromBrowser
	 * @param typesSentToBrowser
	 * @param serializerInterface
	 * @return
	 * @throws UnableToCompleteException
	 */
	private Map<JType, String> generateTypeHandlers(TreeLogger logger,
			GeneratorContext context,
			SerializableTypeOracle typesSentFromBrowser,
			SerializableTypeOracle typesSentToBrowser,
			JClassType serializerInterface) throws UnableToCompleteException {
		TypeSerializerCreator tsc = new TypeSerializerCreator(logger,
				typesSentFromBrowser, typesSentToBrowser, context,
				SerializationUtils
						.getTypeSerializerQualifiedName(serializerInterface));
		tsc.realize(logger);

		Map<JType, String> typeStrings = new HashMap<JType, String>(tsc
				.getTypeStrings());
		typeStrings.put(serializerInterface,
				TypeNameObfuscator.SERVICE_INTERFACE_ID);
		return typeStrings;
	}

	/**
	 * @param serializerLogger
	 * @param typeOracle
	 * @param typesSentFromBrowserBuilder
	 * @param typesSentToBrowserBuilder
	 */
	private void addRoots(TreeLogger serializerLogger, TypeOracle typeOracle,
			SerializableTypeOracleBuilder typesSentFromBrowserBuilder,
			SerializableTypeOracleBuilder typesSentToBrowserBuilder,
			JClassType serializerInterface) throws UnableToCompleteException {
		try {
			addRequiredRoots(serializerLogger, typeOracle,
					typesSentFromBrowserBuilder);
			addRequiredRoots(serializerLogger, typeOracle,
					typesSentToBrowserBuilder);

			addSerializerRootTypes(serializerLogger, typeOracle,
					typesSentFromBrowserBuilder, typesSentToBrowserBuilder,
					serializerInterface);
		} catch (NotFoundException e) {
			serializerLogger.log(TreeLogger.ERROR,
					"Unable to find type referenced from remote service", e);
			throw new UnableToCompleteException();
		}
	}

	/**
	 * @param serializerLogger
	 * @param typeOracle
	 * @param typesSentFromBrowserBuilder
	 * @param typesSentToBrowserBuilder
	 * @param serializerInterface
	 */
	private void addSerializerRootTypes(TreeLogger serializerLogger,
			TypeOracle typeOracle,
			SerializableTypeOracleBuilder typesSentFromBrowserBuilder,
			SerializableTypeOracleBuilder typesSentToBrowserBuilder,
			JClassType serializerInterface) {
		serializerLogger = serializerLogger.branch(TreeLogger.DEBUG,
				"Analyzing '"
						+ serializerInterface
								.getParameterizedQualifiedSourceName()
						+ "' for serializable types", null);

		if (!serializerInterface.isAnnotationPresent(Serializable.class))
			return;
		
		Serializable serializableAnnotation = serializerInterface
				.getAnnotation(Serializable.class);
		for (Class<?> clazz : serializableAnnotation.value()) {
			JClassType classType = typeOracle
					.findType(clazz.getCanonicalName());

			typesSentFromBrowserBuilder
					.addRootType(serializerLogger, classType);
			typesSentToBrowserBuilder.addRootType(serializerLogger, classType);
		}
	}

	/**
	 * Add the implicit root types that are needed to make RPC work. These would
	 * be {@link String} and {@link IncompatibleRemoteServiceException}.
	 */
	private static void addRequiredRoots(TreeLogger logger,
			TypeOracle typeOracle, SerializableTypeOracleBuilder stob)
			throws NotFoundException {
		logger = logger.branch(TreeLogger.DEBUG, "Analyzing implicit types");

		// String is always instantiable.
		addRootType(logger, typeOracle, stob, String.class.getName());
		addRootType(logger, typeOracle, stob, Boolean.class.getName());
		addRootType(logger, typeOracle, stob, Byte.class.getName());
		addRootType(logger, typeOracle, stob, Short.class.getName());
		addRootType(logger, typeOracle, stob, Integer.class.getName());
		addRootType(logger, typeOracle, stob, Long.class.getName());
		addRootType(logger, typeOracle, stob, Float.class.getName());
		addRootType(logger, typeOracle, stob, Double.class.getName());

		// IncompatibleRemoteServiceException is always serializable
		JClassType icseType = typeOracle
				.getType(IncompatibleRemoteServiceException.class.getName());
		stob.addRootType(logger, icseType);
	}

	/**
	 * @param logger
	 * @param typeOracle
	 * @param stob
	 * @throws NotFoundException
	 */
	private static void addRootType(TreeLogger logger, TypeOracle typeOracle,
			SerializableTypeOracleBuilder stob, String typeName) throws NotFoundException {
		JClassType type = typeOracle.getType(typeName);
		stob.addRootType(logger, type);
	}

	/**
	 * @param logger
	 * @param ctx
	 * @param serializerInterface
	 * @return
	 */
	private SourceWriter getSourceWriter(TreeLogger logger,
			GeneratorContext ctx, JClassType serializerInterface) {
		JPackage serializerIntfPkg = serializerInterface.getPackage();
		String packageName = serializerIntfPkg == null ? "" : serializerIntfPkg
				.getName();

		PrintWriter printWriter = ctx.tryCreate(logger, packageName,
				getImplementationSimpleName(serializerInterface));
		if (printWriter == null) {
			return null;
		}

		ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
				packageName, getImplementationSimpleName(serializerInterface));

		String[] imports = new String[] { ClientGWTSerializer.class
				.getCanonicalName(),
				GWT.class.getCanonicalName()};
		for (String imp : imports) {
			composerFactory.addImport(imp);
		}

		composerFactory.setSuperclass(ClientGWTSerializer.class
				.getSimpleName());
		composerFactory.addImplementedInterface(serializerInterface
				.getErasedType().getQualifiedSourceName());

		return composerFactory.createSourceWriter(ctx, printWriter);
	}

	/**
	 * @param serializerInterface
	 * @return
	 */
	private String getImplementationSimpleName(JClassType serializerInterface) {
		String[] name = Shared.synthesizeTopLevelClassName(serializerInterface,
				IMPLEMENTATION_SUFFIX);
		return name[1];
	}

	/**
	 * @param serializerInterface
	 * @return
	 */
	private String getImplementationQualifiedName(JClassType serializerInterface) {
		String[] name = Shared.synthesizeTopLevelClassName(serializerInterface,
				IMPLEMENTATION_SUFFIX);
		return name[0].length() == 0 ? name[1] : name[0] + "." + name[1];
	}
}

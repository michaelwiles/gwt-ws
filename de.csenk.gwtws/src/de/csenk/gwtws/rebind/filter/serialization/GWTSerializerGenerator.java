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

package de.csenk.gwtws.rebind.filter.serialization;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import de.csenk.gwtws.client.filter.serialization.AbstractGWTSerializerImpl;

/**
 * @author Christian.Senk
 * @date 27.08.2010
 * @time 14:25:20
 * 
 */
public class GWTSerializerGenerator extends Generator {

	private static final String IMPLEMENTATION_SUFFIX = "_IMPL";

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
		SourceWriter sourceWriter = getSourceWriter(serializerLogger, context,
				serializerInterface);
		if (sourceWriter == null)
			return getImplementationQualifiedName(serializerInterface);

		
		sourceWriter.commit(serializerLogger);
		return getImplementationQualifiedName(serializerInterface);
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

		PrintWriter printWriter = ctx.tryCreate(logger, packageName, getImplementationSimpleName(serializerInterface));
		if (printWriter == null) {
			return null;
		}

		ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(
				packageName, getImplementationSimpleName(serializerInterface));

		String[] imports = new String[] { AbstractGWTSerializerImpl.class
				.getCanonicalName() };
		for (String imp : imports) {
			composerFactory.addImport(imp);
		}

		composerFactory.setSuperclass(AbstractGWTSerializerImpl.class
				.getSimpleName());
		composerFactory.addImplementedInterface(serializerInterface
				.getErasedType().getQualifiedSourceName());

		return composerFactory.createSourceWriter(ctx, printWriter);
	}

	/**
	 * @param serializerInterface
	 * @return
	 */
	public String getImplementationSimpleName(JClassType serializerInterface) {
		return serializerInterface.getName() + IMPLEMENTATION_SUFFIX;
	}

	/**
	 * @param serializerInterface
	 * @return
	 */
	public String getImplementationQualifiedName(JClassType serializerInterface) {
		JPackage serializerIntfPkg = serializerInterface.getPackage();
		String packageName = serializerIntfPkg == null ? "" : serializerIntfPkg
				.getName();

		String className = serializerInterface.getName()
				+ IMPLEMENTATION_SUFFIX;

		return packageName + "." + className;
	}
}

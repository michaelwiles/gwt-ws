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

package de.csenk.gwtws.server.filter.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

/**
 * @author senk.christian@googlemail.com
 * @date 01.09.2010
 * @time 21:24:40
 * 
 * Most of this code is copied from {@link RemoteServiceServlet}.
 */
public class ServletContextSerializationPolicyProvider implements
		SerializationPolicyProvider {

	private final ServletContext servletContext;

	/**
	 * A cache of moduleBaseURL and serialization policy strong name to
	 * {@link SerializationPolicy}.
	 */
	private final Map<String, SerializationPolicy> serializationPolicyCache = new HashMap<String, SerializationPolicy>();

	/**
	 * @param servletContext
	 */
	public ServletContextSerializationPolicyProvider(
			ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.google.gwt.user.server.rpc.SerializationPolicyProvider#
	 * getSerializationPolicy(java.lang.String, java.lang.String)
	 */
	@Override
	public SerializationPolicy getSerializationPolicy(String moduleBaseURL,
			String strongName) {
		SerializationPolicy serializationPolicy = getCachedSerializationPolicy(
				moduleBaseURL, strongName);
		if (serializationPolicy != null) {
			return serializationPolicy;
		}

		serializationPolicy = doGetSerializationPolicy(moduleBaseURL,
				strongName);

		if (serializationPolicy == null) {
			// Failed to get the requested serialization policy; use the default
			// log(
			// "WARNING: Failed to get the SerializationPolicy '"
			// + strongName
			// + "' for module '"
			// + moduleBaseURL
			// +
			// "'; a legacy, 1.3.3 compatible, serialization policy will be used.  You may experience SerializationExceptions as a result.",
			// null);
			serializationPolicy = RPC.getDefaultSerializationPolicy();
		}

		// This could cache null or an actual instance. Either way we will not
		// attempt to lookup the policy again.
		putCachedSerializationPolicy(moduleBaseURL, strongName,
				serializationPolicy);

		return serializationPolicy;
	}

	/**
	 * @param moduleBaseURL
	 * @param strongName
	 * @param serializationPolicy
	 */
	private void putCachedSerializationPolicy(String moduleBaseURL,
			String strongName, SerializationPolicy serializationPolicy) {
		synchronized (serializationPolicyCache) {
			serializationPolicyCache.put(moduleBaseURL + strongName,
					serializationPolicy);
		}
	}

	/**
	 * @param moduleBaseURL
	 * @param strongName
	 * @return
	 */
	private SerializationPolicy getCachedSerializationPolicy(
			String moduleBaseURL, String strongName) {
		synchronized (serializationPolicyCache) {
			return serializationPolicyCache.get(moduleBaseURL + strongName);
		}
	}

	/**
	 * @param moduleBaseURL
	 * @param strongName
	 * @return
	 */
	private SerializationPolicy doGetSerializationPolicy(String moduleBaseURL,
			String strongName) {
		// The request can tell you the path of the web app relative to the
		// container root.
		String contextPath = servletContext.getContextPath();

		String modulePath = null;
		if (moduleBaseURL != null) {
			try {
				modulePath = new URL(moduleBaseURL).getPath();
			} catch (MalformedURLException ex) {
				// log the information, we will default
				servletContext.log("Malformed moduleBaseURL: " + moduleBaseURL,
						ex);
			}
		}

		SerializationPolicy serializationPolicy = null;

		/*
		 * Check that the module path must be in the same web app as the servlet
		 * itself. If you need to implement a scheme different than this,
		 * override this method.
		 */
		if (modulePath == null || !modulePath.startsWith(contextPath)) {
			String message = "ERROR: The module path requested, "
					+ modulePath
					+ ", is not in the same web application as this servlet, "
					+ contextPath
					+ ".  Your module may not be properly configured or your client and server code maybe out of date.";
			servletContext.log(message, null);
		} else {
			// Strip off the context path from the module base URL. It should be
			// a
			// strict prefix.
			String contextRelativePath = modulePath.substring(contextPath
					.length());

			String serializationPolicyFilePath = SerializationPolicyLoader
					.getSerializationPolicyFileName(contextRelativePath
							+ strongName);

			// Open the RPC resource file and read its contents.
			InputStream is = servletContext
					.getResourceAsStream(serializationPolicyFilePath);
			try {
				if (is != null) {
					try {
						serializationPolicy = SerializationPolicyLoader
								.loadFromStream(is, null);
					} catch (ParseException e) {
						servletContext.log(
								"ERROR: Failed to parse the policy file '"
										+ serializationPolicyFilePath + "'", e);
					} catch (IOException e) {
						servletContext.log(
								"ERROR: Could not read the policy file '"
										+ serializationPolicyFilePath + "'", e);
					}
				} else {
					String message = "ERROR: The serialization policy file '"
							+ serializationPolicyFilePath
							+ "' was not found; did you forget to include it in this deployment?";
					servletContext.log(message);
				}
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// Ignore this error
					}
				}
			}
		}

		return serializationPolicy;
	}

}

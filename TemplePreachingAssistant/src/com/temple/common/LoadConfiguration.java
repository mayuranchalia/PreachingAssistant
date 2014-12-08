/**
 * 
 */
package com.temple.common;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Mayur Jain
 *
 */
public class LoadConfiguration {

	private static LoadConfiguration configuration = new LoadConfiguration();
	private Properties configProperties = null;

	public Properties getConfigProperties() {
		return configProperties;
	}

	public void setConfigProperties(Properties configProperties) {
		this.configProperties = configProperties;
	}

	private LoadConfiguration() {
		configProperties = new Properties();
	}

	public static LoadConfiguration getInstance() {
		try {
			InputStream in = LoadConfiguration.class.getClassLoader()
					.getResourceAsStream("configuration.properties");
			configuration.getConfigProperties().load(in);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return configuration;
	}

}

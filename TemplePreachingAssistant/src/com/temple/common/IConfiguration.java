/**
 * 
 */
package com.temple.common;

/**
 * @author Mayur Jain
 *
 */
public interface IConfiguration {

	public static final String MYSQL_HOST = LoadConfiguration.getInstance()
			.getConfigProperties().getProperty("mysql.host", "localhost");
	public static final String MYSQL_PORT = LoadConfiguration.getInstance()
			.getConfigProperties().getProperty("mysql.port", "3306");
	public static final String MYSQL_DB = LoadConfiguration.getInstance()
			.getConfigProperties().getProperty("mysql.db", "UsersDB");
	public static final String MYSQL_USER = LoadConfiguration.getInstance()
			.getConfigProperties().getProperty("mysql.user", "root");
	public static final String MYSQL_PASSWORD = LoadConfiguration.getInstance()
			.getConfigProperties().getProperty("mysql.password", "ADmin123");
	
	public static final String SMS_GATEWAY_KEY= LoadConfiguration.getInstance()
			.getConfigProperties().getProperty("smsgateway.key", "f30eytu3h3003a75a037s8097m9r8e");
	
}

/**
 * 
 */
package com.temple.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * @author Mayur Jain
 *
 */
public class RepositoryCommunication implements IRepositroyCommunication {

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedstatement = null;

	RepositoryCommunication() throws Exception {

	/*	Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://"
				+ IConfiguration.MYSQL_HOST + ":" + IConfiguration.MYSQL_PORT
				+ "/" + IConfiguration.MYSQL_DB, IConfiguration.MYSQL_USER,
				IConfiguration.MYSQL_PASSWORD);*/
		connection = DataSourceC3P0.getInstance().getConnection();
		
	}

	public Connection getConnection() throws Exception {
		return connection;
	}

	public Statement getStatement() throws Exception {
		statement = connection.createStatement();
		return statement;
	}

	public ResultSet executeDDLQuery(String Query) throws Exception {
		getStatement();
		try {
			return statement.executeQuery(Query);
		} finally {
			statement.close();
		}
	}

	public int executeDMLQuery(String query) throws Exception {
		try {
			getStatement();
			return statement.executeUpdate(query);
		} finally {
			statement.close();
		}
	}

	public void close() throws Exception {

		connection.close();
	}

	@Override
	public PreparedStatement getPreparedStatement(String sql, int keys)
			throws Exception {
		// Statement.RETURN_GENERATED_KEYS
		preparedstatement = connection.prepareStatement(sql, keys);
		return preparedstatement;
	}

	@Override
	public PreparedStatement getPreparedStatement(String sql) throws Exception {
		preparedstatement = connection.prepareStatement(sql);
		return preparedstatement;
	}

}

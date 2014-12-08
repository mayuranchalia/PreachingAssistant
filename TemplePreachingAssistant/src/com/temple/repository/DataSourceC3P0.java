package com.temple.repository;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.temple.common.IConfiguration;

public class DataSourceC3P0 implements IDataSource {

	private static IDataSource datasource;
	private ComboPooledDataSource cpds;

	private DataSourceC3P0() throws IOException, SQLException,
			PropertyVetoException {
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver"); // loads the jdbc driver
		cpds.setJdbcUrl("jdbc:mysql://" + IConfiguration.MYSQL_HOST + ":"
				+ IConfiguration.MYSQL_PORT + "/" + IConfiguration.MYSQL_DB);
		cpds.setUser("root");
		cpds.setPassword("ADmin123");

		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(3);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(180);

	}

	public static IDataSource getInstance() throws IOException, SQLException,
			PropertyVetoException {
		if (datasource == null) {
			datasource = new DataSourceC3P0();
			return datasource;
		} else {
			return datasource;
		}
	}

	public Connection getConnection() throws SQLException {
		return this.cpds.getConnection();
	}

}
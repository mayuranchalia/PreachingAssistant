package com.temple.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDataSource {
	 public Connection getConnection() throws SQLException ;
}

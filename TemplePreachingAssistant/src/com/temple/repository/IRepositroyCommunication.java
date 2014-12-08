package com.temple.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IRepositroyCommunication {
	
	public Connection getConnection() throws Exception;
	public Statement getStatement() throws Exception;
	public PreparedStatement getPreparedStatement(String sql,int keys)throws Exception;
	public PreparedStatement getPreparedStatement(String sql)throws Exception;
	public ResultSet executeDDLQuery(String Query) throws Exception;
	public int executeDMLQuery(String query) throws Exception;
	public void close() throws Exception;
	
}

package de.unipotsdam.cs.toolup.database;

import static de.unipotsdam.cs.toolup.util.SingletonUtil.loadFromFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SqlStatements {
	
	private static final String TOKEN_BO_TABLE_NAME = "BO_TABLE_NAME";
	private static final File SQL_STATEMENT_FILE = new File("src/de/unipotsdam/cs/toolup/database/SQL_Statements.xml").getAbsoluteFile();;
	private static final File SELECT_STATEMENT_FILE = new File("src/de/unipotsdam/cs/toolup/database/SELECT_Statements.xml").getAbsoluteFile();
	private static Properties SQL_STATEMENTS = null;
	private static Properties SELECT_STATEMENTS = null;

	public static PreparedStatement getSelectAllFrom(String tableName) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());				
		String preparedQuery = getSelectStatement(tableName, "BYid");
		return connection.prepareStatement(preparedQuery);
	}

	public static PreparedStatement getRelationAbyB(
			String a, String b) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());
		String preparedQuery = getSelectStatement(a, "BY" + b);	
		return connection.prepareStatement(preparedQuery);
	}
	
	private static String getSelectStatement(String tableName, String by) throws SQLException {
		String preparedQuery;
		try {
			preparedQuery = getSelectStatements().getProperty(tableName + by);
		} catch (IOException e) {
			throw new SQLException(e);
		}
		return preparedQuery;
	}

	private static Properties getSelectStatements() throws IOException{
		if (SELECT_STATEMENTS == null){
			SELECT_STATEMENTS = loadFromFile(SELECT_STATEMENTS, SELECT_STATEMENT_FILE);
		} 
		return SELECT_STATEMENTS;
	}

	public static PreparedStatement getInsertInto(String tableName) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());				
		String preparedQuery = getInsertStatement(tableName);
		return connection.prepareStatement(preparedQuery);
	}

	private static String getInsertStatement(String tableName) throws SQLException {
		String preparedQuery;
		preparedQuery = getSqlStatements().getProperty("insertBO");
		preparedQuery = preparedQuery.replace(TOKEN_BO_TABLE_NAME, tableName);
		return preparedQuery;
	}

	private static Properties getSqlStatements() throws SQLException {
		if (SQL_STATEMENTS == null){
			try {
				SQL_STATEMENTS = loadFromFile(SQL_STATEMENTS, SQL_STATEMENT_FILE);
			} catch (IOException e) {
				throw new SQLException("Could not read from file " + SQL_STATEMENT_FILE, e);
			}
		} 
		return SQL_STATEMENTS;
	}

	public static PreparedStatement getDeleteFrom(String tableName) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());				
		String preparedQuery = getDeleteStatement(tableName);
		return connection.prepareStatement(preparedQuery);
	}

	private static String getDeleteStatement(String tableName) throws SQLException {
		String preparedQuery;
		preparedQuery = getSqlStatements().getProperty("deleteBO");
		preparedQuery = preparedQuery.replace(TOKEN_BO_TABLE_NAME, tableName);
		return preparedQuery;
	}

	public static PreparedStatement getUpdate(String tableName) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());				
		String preparedQuery = getUpdateStatement(tableName);
		return connection.prepareStatement(preparedQuery);
	}

	private static String getUpdateStatement(String tableName) throws SQLException {
		String preparedQuery;
		preparedQuery = getSqlStatements().getProperty("updateBO");
		preparedQuery = preparedQuery.replace(TOKEN_BO_TABLE_NAME, tableName);
		return preparedQuery;
	}
}

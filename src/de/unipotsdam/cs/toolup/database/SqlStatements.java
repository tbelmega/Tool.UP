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
	
	private static final File INSERT_STATEMENT_FILE = new File("src/de/unipotsdam/cs/toolup/database/INSERT_Statements.xml").getAbsoluteFile();;
	private static final File SELECT_STATEMENT_FILE = new File("src/de/unipotsdam/cs/toolup/database/SELECT_Statements.xml").getAbsoluteFile();
	private static Properties INSERT_STATEMENTS = null;
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
		try {
			preparedQuery = getInsertStatements().getProperty("generic");
			preparedQuery = preparedQuery.replace("BO_TABLE_NAME", tableName);
		} catch (IOException e) {
			throw new SQLException(e);
		}
		return preparedQuery;
	}

	private static Properties getInsertStatements() throws IOException {
		if (INSERT_STATEMENTS == null){
			INSERT_STATEMENTS = loadFromFile(INSERT_STATEMENTS, INSERT_STATEMENT_FILE);
		} 
		return INSERT_STATEMENTS;
	}
}

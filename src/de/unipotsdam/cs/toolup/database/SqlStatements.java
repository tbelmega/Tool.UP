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
	
	private static final String TOKEN_TABLE_NAME = "TABLE_NAME";
	private static final File SQL_STATEMENT_FILE = new File("src/de/unipotsdam/cs/toolup/database/SQL_Statements.xml").getAbsoluteFile();
	private static final CharSequence TOKEN_FOREIGN_KEY = "FOREIGN_KEY";
	private static Properties SQL_STATEMENTS = null;



	public static PreparedStatement getSelectRelation(
			String relationName, String criteriaAttribute) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());
		String preparedQuery = getSqlStatements().getProperty("selectRelation");	
		preparedQuery = preparedQuery.replace(TOKEN_TABLE_NAME, relationName).replace(TOKEN_FOREIGN_KEY,  criteriaAttribute);		
		return connection.prepareStatement(preparedQuery);
	}
	
	public static PreparedStatement getSelectAllFrom(String tableName) throws SQLException {
		return getCustomizedStatement("selectBO", tableName);
	}

	public static PreparedStatement getInsertInto(String tableName) throws SQLException {
		return getCustomizedStatement("insertBO", tableName);
	}

	public static PreparedStatement getDeleteFrom(String tableName) throws SQLException {				
		return getCustomizedStatement("deleteBO", tableName);
	}

	public static PreparedStatement getUpdate(String tableName) throws SQLException {			
		return getCustomizedStatement("updateBO", tableName);
	}
	
	private static PreparedStatement getCustomizedStatement(String statementKey, String tableName) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());	
		String preparedQuery;
		preparedQuery = getSqlStatements().getProperty(statementKey);
		preparedQuery = preparedQuery.replace(TOKEN_TABLE_NAME, tableName);
		return connection.prepareStatement(preparedQuery);
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

}

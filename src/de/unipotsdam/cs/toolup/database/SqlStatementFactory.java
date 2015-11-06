package de.unipotsdam.cs.toolup.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SqlStatementFactory {
	
	private static final String TOKEN_TABLE_NAME = "TABLE_NAME";
	private static final File SQL_STATEMENT_FILE = new File("src/de/unipotsdam/cs/toolup/database/SQL_Statements.xml").getAbsoluteFile();
	private static final CharSequence TOKEN_FOREIGN_KEY = "FOREIGN_KEY";
	private final Connection connection;
	private Properties sqlStatements;


	public SqlStatementFactory() throws IOException, SQLException {
		sqlStatements = new Properties();
		sqlStatements.loadFromXML(new FileInputStream(SQL_STATEMENT_FILE));
		connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());
	}

	public PreparedStatement getSelectRelation(
			String relationName, String criteriaAttribute) throws SQLException {
		String preparedQuery = sqlStatements.getProperty("selectRelation");	
		preparedQuery = preparedQuery.replace(TOKEN_TABLE_NAME, relationName).replace(TOKEN_FOREIGN_KEY,  criteriaAttribute);		
		return connection.prepareStatement(preparedQuery);
	}
	
	public PreparedStatement getSelectAllFrom(String tableName) throws SQLException {
		return getCustomizedStatement("selectBO", tableName);
	}

	public PreparedStatement getInsertInto(String tableName) throws SQLException {
		return getCustomizedStatement("insertBO", tableName);
	}
	

	public PreparedStatement getInsertRelation(String tableName) throws SQLException {
		return getCustomizedStatement("insertRelation", tableName);
	}

	public PreparedStatement getDeleteFrom(String tableName) throws SQLException {				
		return getCustomizedStatement("deleteBO", tableName);
	}

	public PreparedStatement getUpdate(String tableName) throws SQLException {			
		return getCustomizedStatement("updateBO", tableName);
	}
	
	private PreparedStatement getCustomizedStatement(String statementKey, String tableName) throws SQLException {
		String preparedQuery;
		preparedQuery = sqlStatements.getProperty(statementKey);
		preparedQuery = preparedQuery.replace(TOKEN_TABLE_NAME, tableName);
		return connection.prepareStatement(preparedQuery);
	}



}

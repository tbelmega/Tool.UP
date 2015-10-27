package de.unipotsdam.cs.toolup.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static de.unipotsdam.cs.toolup.util.SingletonPropertiesSynchronizedImpl.*;

public class SqlStatements {
	
	static File SELECT_STATEMENT_FILE = new File("src/de/unipotsdam/cs/toolup/database/SELECT_Statements.xml").getAbsoluteFile();
	private static Properties SELECT_STATEMENTS = null;

	public static PreparedStatement getSelectAllFrom(String tableName) throws SQLException {

		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());
				
		String preparedQuery = getStatement(tableName, "BYid");
				
		return connection.prepareStatement(preparedQuery);
	}

	public static PreparedStatement getRelationAbyB(
			String a, String b) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());

		String preparedQuery = getStatement(a, "BY" + b);
		
		return connection.prepareStatement(preparedQuery);
	}
	
	private static String getStatement(String tableName, String by) throws SQLException {
		String preparedQuery;
		try {
			preparedQuery = getSelectStatements().getProperty(tableName + by);
		} catch (IOException e) {
			throw new SQLException(e);
		}
		return preparedQuery;
	}

	private static Properties getSelectStatements() throws IOException{
		return getProperties(SELECT_STATEMENTS, SELECT_STATEMENT_FILE);
	}
}

package de.unipotsdam.cs.toolup.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SqlStatementFactory {

    public static final String SQL_STATEMENTS_FILENAME = "/SQL_Statements.xml";
    private static final String TOKEN_TABLE_NAME = "TABLE_NAME";
    private static final String TOKEN_FOREIGN_KEY = "FOREIGN_KEY";
    private final Connection connection;
    private Properties sqlStatements;


    public SqlStatementFactory() throws IOException, SQLException {
        sqlStatements = new Properties();
        sqlStatements.loadFromXML(this.getClass().getResourceAsStream(SQL_STATEMENTS_FILENAME));
        connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());
    }

    public PreparedStatement getSelectRelation(
            String relationName, String criteriaAttribute) throws SQLException {
        String preparedQuery = sqlStatements.getProperty("selectRelation");
        preparedQuery = preparedQuery.replace(TOKEN_TABLE_NAME, relationName).replace(TOKEN_FOREIGN_KEY, criteriaAttribute);
        return connection.prepareStatement(preparedQuery);
    }

    public PreparedStatement getStatementSelectByUuidFrom(String tableName) throws SQLException {
        return getCustomizedStatement("selectBO", tableName);
    }

    public PreparedStatement getStatementSelectAllFrom(String tableName) throws SQLException {
        return getCustomizedStatement("selectAll", tableName);
    }

    public PreparedStatement getInsertInto(String tableName) throws SQLException {
        switch (tableName) {
            case "category":
                return getCustomizedStatement("insertCategory", tableName);
            default:
                return getCustomizedStatement("insertBO", tableName);
        }
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

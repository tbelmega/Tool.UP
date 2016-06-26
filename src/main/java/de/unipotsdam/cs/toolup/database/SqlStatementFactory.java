package de.unipotsdam.cs.toolup.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_CATEGORY;

class SqlStatementFactory {

    private static final String SQL_STATEMENTS_FILENAME = "/SQL_Statements.xml";
    private static final String TOKEN_TABLE_NAME = "TABLE_NAME";
    private static final String TOKEN_FOREIGN_KEY = "FOREIGN_KEY";
    private final Connection connection;
    private Properties sqlStatements;


    SqlStatementFactory() throws IOException, SQLException {
        sqlStatements = new Properties();
        sqlStatements.loadFromXML(this.getClass().getResourceAsStream(SQL_STATEMENTS_FILENAME));
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());
    }

    PreparedStatement getSelectRelation(
            String relationName, String criteriaAttribute) throws SQLException {
        String preparedQuery = sqlStatements.getProperty("selectRelation");
        preparedQuery = preparedQuery.replace(TOKEN_TABLE_NAME, relationName).replace(TOKEN_FOREIGN_KEY, criteriaAttribute);
        return connection.prepareStatement(preparedQuery);
    }

    PreparedStatement getStatementSelectByUuidFrom(String tableName) throws SQLException {
        return getCustomizedStatement("selectBO", tableName);
    }

    PreparedStatement getStatementSelectAllFrom(String tableName) throws SQLException {
        return getCustomizedStatement("selectAll", tableName);
    }

    PreparedStatement getInsertInto(String tableName) throws SQLException {
        return getCustomizedStatement(tableName + "Insert", tableName);
    }


    PreparedStatement getInsertRelation(String tableName) throws SQLException {
        return getCustomizedStatement("insertRelation", tableName);
    }

    PreparedStatement getDeleteFrom(String tableName) throws SQLException {
        return getCustomizedStatement("deleteBO", tableName);
    }

    PreparedStatement getUpdate(String tableName) throws SQLException {
        return getCustomizedStatement("updateBO", tableName);
    }

    private PreparedStatement getCustomizedStatement(String statementKey, String tableName) throws SQLException {
        String preparedQuery;
        preparedQuery = sqlStatements.getProperty(statementKey);
        preparedQuery = preparedQuery.replace(TOKEN_TABLE_NAME, tableName);
        return connection.prepareStatement(preparedQuery);
    }


    PreparedStatement getSelectSubcategories() throws SQLException {
        return getCustomizedStatement("selectSubCategories", TABLE_NAME_CATEGORY);
    }

    PreparedStatement getSelectAllCategoriesWithApplication() throws SQLException {
        String preparedQuery = sqlStatements.getProperty("selectCategoriesWithApplication");
        return connection.prepareStatement(preparedQuery);
    }

    PreparedStatement getSelectTopLevelCategories() throws SQLException {
        String preparedQuery = sqlStatements.getProperty("selectCategoriesWithoutSuperCategory");
        return connection.prepareStatement(preparedQuery);
    }

    PreparedStatement getFullTextSearchStatement(String searchString) throws SQLException {
        String preparedQuery = sqlStatements.getProperty("fullTextSearch");
        return connection.prepareStatement(preparedQuery);
    }
}

package de.unipotsdam.cs.toolup.database;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class SqlStatementFactoryTest {

    private static SqlStatementFactory sqlStatementFactory;

    @BeforeClass
    public static void createSqlStatementFactory() throws Exception {
        sqlStatementFactory = new SqlStatementFactory();
    }

    @Test
    public void testThatSqlStatementsFileIsFound() throws IOException {
        //arrange

        //act
        InputStream in = this.getClass().getResourceAsStream("/SQL_Statements.xml");

        //assert
        assertNotNull(in);
    }

    @Test
    public void testThatSqlStatementsFileContains() throws IOException {
        //arrange
        InputStream in = this.getClass().getResourceAsStream("/SQL_Statements.xml");
        Properties sqlStatements = new Properties();
        sqlStatements.loadFromXML(in);

        //act
        String selectStatement = sqlStatements.getProperty("selectBO");

        //assert
        assertNotNull(selectStatement);
    }

    @Test
    public void testGetSingleRecordFrom() throws IOException, SQLException {
        //arrange
        String expectedStatement = "SELECT * FROM foo WHERE uuid = 'bar';";


        //act

        PreparedStatement statement = sqlStatementFactory.getStatementSelectByUuidFrom("foo");
        statement.setString(1, "bar");

        //assert
        assertTrue(statement.toString().contains(expectedStatement));
    }

    @Test
    public void testGetAllRecordsFrom() throws IOException, SQLException {
        //arrange
        String expectedStatement = "SELECT * FROM foo;";


        //act

        PreparedStatement statement = sqlStatementFactory.getStatementSelectAllFrom("foo");

        //assert
        assertTrue(statement.toString().contains(expectedStatement));
    }

}

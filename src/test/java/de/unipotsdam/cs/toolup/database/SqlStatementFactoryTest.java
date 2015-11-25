package de.unipotsdam.cs.toolup.database;


import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class SqlStatementFactoryTest {

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
}

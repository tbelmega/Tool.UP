package de.unipotsdam.cs.toolup.database;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.sql.SQLException;

/**
 * AbstractDatabaseTest is the super class for tests that create data in the database.
 * The DatabaseControllerHelper object keeps track of the created objects and deletes them after the tests.
 */
public class AbstractDatabaseTest {


    protected DatabaseControllerHelper db;

    @BeforeClass
    public void setUp() throws IOException, SQLException {
        db = DatabaseControllerHelper.getInstance();
    }

    @AfterClass
    public void cleanCreatedObjects() throws Exception {
        db.deleteCreatedBOsFromDatabase();
    }

}

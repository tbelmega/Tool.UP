package de.unipotsdam.cs.toolup.database;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

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

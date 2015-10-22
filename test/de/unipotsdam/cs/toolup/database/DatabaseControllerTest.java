package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.Application;




public class DatabaseControllerTest {

	
	@Test
	public void testThatJDBCcanExecuteQuery() throws SQLException{
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&pw=");
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("select * from application;");
		res.first();
		String title = res.getString("title");
		assertEquals("Dropbox", title);
	}
	

	@Test
	public void testThatDBCLoadsApplicationFromDB() throws SQLException {
		//arrange
		Application expectedApp = new Application("test_id_1","Dropbox",null);
		
		//act
		Application app = DatabaseController.load("test_id_1");
		
		//assert
		assertTrue(expectedApp.equals(app));
	}
	
	@Test
	public void testThatLoadedApplicationHasExpectedValues() throws SQLException {
		//arrange
		String expectedTitle = "Dropbox";
		String expectedDescription = "Dropbox Description";
		
		//act
		Application app = DatabaseController.load("test_id_1");
		
		//assert
		assertEquals(expectedTitle, app.getTitle());
		assertEquals(expectedDescription, app.getDescription());
	}

}

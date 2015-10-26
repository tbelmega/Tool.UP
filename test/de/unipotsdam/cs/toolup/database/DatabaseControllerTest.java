package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.BusinessObject;




public class DatabaseControllerTest {
	
	@Test
	public void testThatLoadedApplicationHasExpectedValues() throws SQLException {
		//arrange
		String expectedTitle = "Dropbox";
		String expectedDescription = "Dropbox Description";
		
		//act
		BusinessObject app = DatabaseController.load("application/test_id_1");
		
		//assert
		assertEquals(expectedTitle, app.getTitle());
		assertEquals(expectedDescription, app.getDescription());
	}
	
	@Test
	public void testThatLoadedFeatureHasExpectedValues() throws SQLException {
		//arrange
		String expectedTitle = "Kalender anlegen";
		String expectedDescription = "Kalender anlegen Description";
		
		//act
		BusinessObject feat = DatabaseController.load("feature/test_id_21");
		
		//assert
		assertEquals(expectedTitle, feat.getTitle());
		assertEquals(expectedDescription, feat.getDescription());
	}
	
	@Test(dataProvider = PROVIDE_SAMPLE_IDS)
	public void testThatTablenameIsExtractableFromId(String expectedTableName, String id) {
		//arrange
		
		//act
		String tableName = DatabaseController.getTableNameFromId(id);

		//assert
		assertEquals(expectedTableName, tableName);
	}
	
	
	private static final String PROVIDE_SAMPLE_IDS = "provideSampleIds";
	
	@DataProvider(name = PROVIDE_SAMPLE_IDS)
	public Object[][] provideSampleIds(){
		return new Object[][]{
				
				{"application","application/123456"},
				{"feature","feature/123456"},
				{"category","category/123456"}
				
		};
	}
	

}

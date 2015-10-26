package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.assertEquals;

import java.sql.SQLException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;




public class DatabaseControllerTest {
	
	@Test(dataProvider = PROVIDE_BUSINESS_OBJECTS)
	public void testThatLoadedBusinessObjectHasExpectedValues(String expectedTitle, String expectedDescription, Class<? extends BusinessObject> expectedClass, String id) throws SQLException {
		//arrange

		//act
		BusinessObject busObj = DatabaseController.load(id);
		
		//assert
		assertEquals(expectedTitle, busObj.getTitle());
		assertEquals(expectedDescription, busObj.getDescription());
		assertEquals(expectedClass, busObj.getClass());
	}

	/**
	 * 
	 * @param expectedTitle
	 * @param expectedDescription
	 * @param expectedClass
	 * @param id
	 */
	private static final String PROVIDE_BUSINESS_OBJECTS = "provideBusinessObjects";
	
	@DataProvider(name= PROVIDE_BUSINESS_OBJECTS)
	public Object[][] provideBusinessObjects(){
		return new Object[][]{
				
				{"Dropbox","Dropbox Description",Application.class,"application/test_id_1"},
				{"Box.UP","Box.UP Description",Application.class,"application/test_id_2"},
				{"Cloud Speicher","Cloud Speicher Description",Category.class,"category/test_id_11"},
				{"Kalender anlegen","Kalender anlegen Description",Feature.class,"feature/test_id_21"}
				
		};
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

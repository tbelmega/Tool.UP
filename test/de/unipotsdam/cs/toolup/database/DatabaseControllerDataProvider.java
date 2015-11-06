package de.unipotsdam.cs.toolup.database;

import org.testng.annotations.DataProvider;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;

public class DatabaseControllerDataProvider {
	
	public static final String FEATURE_TEST_ID_22 = "feature/test_id_22";
	public static final String FEATURE_TEST_ID_21 = "feature/test_id_21";
	public static final String APPLICATION_TEST_ID_2 = "application/test_id_2";
	public static final String APPLICATION_TEST_ID_1 = "application/test_id_1";
	public static final String CATEGORY_TEST_ID_11 = "category/test_id_11";


	/**
	 * 
	 * @param expectedTitle
	 * @param expectedDescription
	 * @param expectedClass
	 * @param id
	 */
	public static final String PROVIDE_BUSINESS_OBJECTS = "provideBusinessObjects";
	
	@DataProvider(name= PROVIDE_BUSINESS_OBJECTS)
	public static Object[][] provideBusinessObjects(){
		return new Object[][]{
				
				{"Dropbox","Dropbox Description",Application.class,APPLICATION_TEST_ID_1},
				{"Box.UP","Box.UP Description",Application.class,APPLICATION_TEST_ID_2},
				{"Cloud Speicher","Cloud Speicher Description",Category.class,CATEGORY_TEST_ID_11},
				{"Kalender anlegen","Kalender anlegen Description",Feature.class,FEATURE_TEST_ID_21}
				
		};
	}
	
	/**
	 * 
	 * @param tableName
	 */
	public static final String PROVIDE_BO_TABLES = "provideBOTables";
	
	@DataProvider(name= PROVIDE_BO_TABLES)
	public static Object[][] provideBOTables(){
		return new Object[][]{
				{"application"},
				{"category"},
				{"feature"}
		};
	}
	
	public static final String PROVIDE_BO_TABLES_AND_RELATIONS = "provideBOTablesAndRelatedIds";
	
	@DataProvider(name= PROVIDE_BO_TABLES_AND_RELATIONS)
	public static Object[][] provideBOTablesAndRelatedIds(){
		return new Object[][]{
				{"application", new String[] {CATEGORY_TEST_ID_11, FEATURE_TEST_ID_21}},
				{"category", new String[] {APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2}},
				{"feature", new String[] {APPLICATION_TEST_ID_1}}
		};
	}
}

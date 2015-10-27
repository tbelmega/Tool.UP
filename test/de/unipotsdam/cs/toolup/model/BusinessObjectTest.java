package de.unipotsdam.cs.toolup.model;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BusinessObjectTest {
	
	private static final String FEATURE_TEST_ID_21 = "feature/test_id_21";
	private static final String APPLICATION_TEST_ID_1 = "application/test_id_1";
	private static final String CATEGORY_TEST_ID_11 = "category/test_id_11";
	
	@Test(dataProvider = PROVIDE_SAMPLE_IDS)
	public void testThatTablenameIsExtractableFromId(String expectedTableName, String id) {
		//arrange
		
		//act
		String tableName = BusinessObject.getTableNameFromId(id);

		//assert
		assertEquals(expectedTableName, tableName);
	}
	
	
	private static final String PROVIDE_SAMPLE_IDS = "provideSampleIds";
	
	@DataProvider(name = PROVIDE_SAMPLE_IDS)
	public Object[][] provideSampleIds(){
		return new Object[][]{
				
				{"application",APPLICATION_TEST_ID_1},
				{"feature",FEATURE_TEST_ID_21},
				{"category",CATEGORY_TEST_ID_11}
				
		};
	}
}

package de.unipotsdam.cs.toolup.model;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.util.FileUtil;
import de.unipotsdam.cs.toolup.util.JSONUtil;

public class BusinessObjectTest {

	private static final String FILENAME_TEST_APP_JSON = "test/resources/TestApplication.json";
	private static final String FILENAME_TEST_FEAT_JSON = "test/resources/TestFeature.json";
	private static final String FILENAME_TEST_CAT_JSON = "test/resources/TestCategory.json";
	
	
	private static final String APPLICATION_TESTDESCRIPTION_1 = "Dropbox Description";
	private static final String APPLICATION_TESTTITLE_1 = "Dropbox";
	private static final String FEATURE_TESTTITLE_21 = "Kalender anlegen";
	private static final String FEATURE_TESTDESCRIPTION_21 = "Kalender anlegen Description";
	private static final String CATEGORY_TESTTITLE_11 = "Cloud Speicher";
	private static final String CATEGORY_TESTDESCRIPTION_11 = "Cloud Speicher Description";
	
	private static final String TABLENAME_CATEGORY = "category";
	private static final String TABLENAME_FEATURE = "feature";
	private static final String TABLENAME_APPLICATION = "application";
	private static final String FEATURE_TEST_ID_21 = "feature/test_id_21";
	private static final String FEATURE_TEST_ID_22 = "feature/test_id_22";
	private static final String APPLICATION_TEST_ID_1 = "application/test_id_1";
	private static final String APPLICATION_TEST_ID_2 = "application/test_id_2";
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
				
				{TABLENAME_APPLICATION,APPLICATION_TEST_ID_1},
				{TABLENAME_FEATURE,FEATURE_TEST_ID_21},
				{TABLENAME_CATEGORY,CATEGORY_TEST_ID_11}
				
		};
	}
	
	@Test
	public void testThatAnApplicationDoesNotEqualAnOtherApplication() {
		//arrange
		BusinessObject anOtherApp = new Application(APPLICATION_TEST_ID_1,null,null, null,null);
		BusinessObject newApp = new Application(APPLICATION_TEST_ID_2,null,null, null,null);
		
		//act

		//assert
		assertFalse(anOtherApp.equals(newApp));
	}
	
	@Test
	public void testThatAnApplicationDoesEqualItself() {
		//arrange
		BusinessObject anOtherApp = new Application(APPLICATION_TEST_ID_1,null,null, null,null);
		BusinessObject newApp = new Application(APPLICATION_TEST_ID_1,null,null, null,null);
		
		//act
		
		//assert
		assertTrue(anOtherApp.equals(newApp));
	}
	
	
	@Test(dataProvider = PROVIDE_BUSINESS_OBJECTS)
	public void testThatBusinessObjectIsConvertedToJsonCorrectly(BusinessObject bo, JSONObject expectedJson) throws JSONException {
		//arrange

		//act
		JSONObject jsonRepresentation = bo.convertToJson();

		//assert
		JSONUtil.assertEqualJSONContent(expectedJson, jsonRepresentation);
	}
	
	@Test(dataProvider = PROVIDE_BOS_TO_COMPARE)
	public void testThatBOEqualsOtherBOInAllProperties(boolean expectedResult, BusinessObject aBusinessObject, BusinessObject anOtherBusinessObject) {
		//arrange
	
		//act
		boolean equal = aBusinessObject.equalsInAllProperties(anOtherBusinessObject);

		//assert
		assertEquals(expectedResult,equal);
	}
	
	private static final String PROVIDE_BOS_TO_COMPARE = "provideBOsToCompare";
	
	@DataProvider(name = PROVIDE_BOS_TO_COMPARE)
	private Object[][] provideBOsToCompare() {

		HashSet<String> emptySet = new HashSet<String>();
		HashSet<String> featSet = new HashSet<String>();
		featSet.add(FEATURE_TEST_ID_21);
		HashSet<String> appSet = new HashSet<String>();
		appSet.add(APPLICATION_TEST_ID_1);
		HashSet<String> catSet = new HashSet<String>();
		catSet.add(CATEGORY_TEST_ID_11);
		
		return new Object[][] {
				{ true, new Application(APPLICATION_TEST_ID_1,"","",emptySet,emptySet), 
					new Application(APPLICATION_TEST_ID_1,"","",emptySet,emptySet)},
				{ true, new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1,"",emptySet,emptySet), 
					new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1,"",emptySet,emptySet)},
				{ true, new Application(APPLICATION_TEST_ID_1,"",APPLICATION_TESTDESCRIPTION_1,emptySet,emptySet), 
					new Application(APPLICATION_TEST_ID_1,"",APPLICATION_TESTDESCRIPTION_1,emptySet,emptySet)},
				{ true, new Application(APPLICATION_TEST_ID_1,"","",featSet,catSet), 
					new Application(APPLICATION_TEST_ID_1,"","",featSet,catSet)},
					
				{ true, new Category(CATEGORY_TEST_ID_11,CATEGORY_TESTTITLE_11,CATEGORY_TESTDESCRIPTION_11,appSet), 
					new Category(CATEGORY_TEST_ID_11,CATEGORY_TESTTITLE_11,CATEGORY_TESTDESCRIPTION_11,appSet)},
					
				{ false, new Application(APPLICATION_TEST_ID_1,"","",emptySet,emptySet),
					new Application(APPLICATION_TEST_ID_2,"","",emptySet,emptySet)},
				{ false, new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1,"",emptySet,emptySet),
					new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1 + "foo","",emptySet,emptySet)},
				{ false, new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1,emptySet,emptySet),
					new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1 + "foo",emptySet,emptySet)},
				{ false, new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1,featSet,emptySet),
					new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1,emptySet,emptySet)},
				{ false, new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1,featSet,catSet),
					new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1,featSet,appSet)},
					
				{ false, new Application(APPLICATION_TEST_ID_1,APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1,featSet,catSet), 
					new Category(CATEGORY_TEST_ID_11,CATEGORY_TESTTITLE_11,CATEGORY_TESTDESCRIPTION_11,appSet)}
		};
	}
	
	@Test(dataProvider = PROVIDE_BUSINESS_OBJECTS)
	public void testThatBusinessObjectIsCreatedFromJsonData(BusinessObject expectedBo, JSONObject jsonRepresentation) throws JSONException {
		//arrange
		
		//act
		BusinessObject bo = BusinessObject.createBusinessObjectFromJson(jsonRepresentation);
		
		//assert
		assertTrue(bo.equalsInAllProperties(expectedBo));
	}
	
	
	
	private static final String PROVIDE_BUSINESS_OBJECTS = "provideBusinessObjects";

	
	@DataProvider(name = PROVIDE_BUSINESS_OBJECTS) 
	public Object[][] provideBusinessObjects() throws Exception{
		
		Set<String> relatedCats = new HashSet<String>();
		relatedCats.add(CATEGORY_TEST_ID_11);
		
		Set<String> relatedFeats = new HashSet<String>();
		relatedFeats.add(FEATURE_TEST_ID_21);
		relatedFeats.add(FEATURE_TEST_ID_22);
		
		Set<String> relatedApps = new HashSet<String>();
		relatedApps.add(APPLICATION_TEST_ID_1);
		relatedApps.add(APPLICATION_TEST_ID_2);
		
		BusinessObject app1 = new Application(APPLICATION_TEST_ID_1, APPLICATION_TESTTITLE_1, APPLICATION_TESTDESCRIPTION_1, relatedCats, relatedFeats);
		BusinessObject feat1 = new Feature(FEATURE_TEST_ID_21, FEATURE_TESTTITLE_21, FEATURE_TESTDESCRIPTION_21, relatedApps);
		BusinessObject cat1 = new Category(CATEGORY_TEST_ID_11, CATEGORY_TESTTITLE_11, CATEGORY_TESTDESCRIPTION_11, relatedApps);
		
		JSONObject expectedJsonApp = new JSONObject(FileUtil.readFile(FILENAME_TEST_APP_JSON));
		JSONObject expectedJsonFeat = new JSONObject(FileUtil.readFile(FILENAME_TEST_FEAT_JSON));
		JSONObject expectedJsonCat = new JSONObject(FileUtil.readFile(FILENAME_TEST_CAT_JSON));
		
		return new Object[][] {
				{ app1, expectedJsonApp },
				{ feat1, expectedJsonFeat },
				{ cat1, expectedJsonCat },
		};
	}
}

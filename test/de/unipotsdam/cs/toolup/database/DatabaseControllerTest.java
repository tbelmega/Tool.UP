package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;




public class DatabaseControllerTest {
	
	private static final String FEATURE_TEST_ID_22 = "feature/test_id_22";
	private static final String FEATURE_TEST_ID_21 = "feature/test_id_21";
	private static final String APPLICATION_TEST_ID_2 = "application/test_id_2";
	private static final String APPLICATION_TEST_ID_1 = "application/test_id_1";
	private static final String CATEGORY_TEST_ID_11 = "category/test_id_11";

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
				
				{"Dropbox","Dropbox Description",Application.class,APPLICATION_TEST_ID_1},
				{"Box.UP","Box.UP Description",Application.class,APPLICATION_TEST_ID_2},
				{"Cloud Speicher","Cloud Speicher Description",Category.class,CATEGORY_TEST_ID_11},
				{"Kalender anlegen","Kalender anlegen Description",Feature.class,FEATURE_TEST_ID_21}
				
		};
	}
	
	
	@Test
	public void testThatLoadedCategoryHasRelatedApplications() throws SQLException {
		//arrange
		Collection<String> expectedAppIds = Arrays.asList(new String[] {APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2});

		//act
		Category cat = (Category) DatabaseController.load(CATEGORY_TEST_ID_11);

		//assert
		assertTrue(cat.getRelatedApplications().containsAll(expectedAppIds));
	}
	
	@Test
	public void testThatLoadedFeatureHasRelatedApplications() throws SQLException {
		//arrange
		Collection<String> expectedAppIds = Arrays.asList(new String[] {APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2});
		
		//act
		Feature feat = (Feature) DatabaseController.load(FEATURE_TEST_ID_21);
		
		//assert
		assertTrue(feat.getRelatedApplications().containsAll(expectedAppIds));
	}
	
	@Test
	public void testThatLoadedApplicationHasRelatedCategories() throws SQLException {
		//arrange
		Collection<String> expectedCatIds = Arrays.asList(new String[] {CATEGORY_TEST_ID_11});
		
		//act
		Application app = (Application) DatabaseController.load(APPLICATION_TEST_ID_1);
		
		//assert
		assertTrue(app.getRelatedCategories().containsAll(expectedCatIds));
	}
	
	@Test
	public void testThatLoadedApplicationHasRelatedFeatures() throws SQLException {
		//arrange
		Collection<String> expectedFeatureIds = Arrays.asList(new String[] {FEATURE_TEST_ID_21,FEATURE_TEST_ID_22});
		
		//act
		Application app = (Application) DatabaseController.load(APPLICATION_TEST_ID_1);
		
		//assert
		assertTrue(app.getRelatedFeatures().containsAll(expectedFeatureIds));
	}
	
	@Test(dataProvider = PROVIDE_BUSINESS_OBJECTS)
	public void testThatApplicationIsInsertedIntoDatabase(String title, String description, Class<? extends BusinessObject> clazz, String id) throws SQLException {
		//arrange
		String tablename = BusinessObject.getTableNameFromId(id);
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(tablename + "/" + UUID.randomUUID());
		assertFalse(DatabaseController.checkIfExistsInDB(aBusinessObject));

		//act
		DatabaseController.storeToDatabase(aBusinessObject);

		//assert
		BusinessObject loaded = DatabaseController.load(aBusinessObject.getUuid());
		assertTrue(aBusinessObject.equals(loaded));
	}
	
//	@Test
//	public void testThatAppilcationIsDeletedFromDatabase() {
//		//arrange
//		Application anApplication = new Application("application/test_id_4", "Moodle", "Moodle Description", null, null);
//		
//		//act
//		DatabaseController.deleteFromDatabase(anApplication);
//
//		//assert
//		
//	}
	
	@Test(dataProvider = PROVIDE_BUSINESS_OBJECTS)
	public void testThatCheckExistReturnsTrue(String title, String description, Class<? extends BusinessObject> clazz, String id) throws SQLException {
		//arrange
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id);
		
		//act
		boolean exists = DatabaseController.checkIfExistsInDB(aBusinessObject);
		boolean existsById = DatabaseController.checkIfExistsInDB(id);

		//assert
		assertTrue(exists);
		assertTrue(existsById);
	}
	
	@Test
	public void testThatCheckExistReturnsFalse() throws SQLException {
		//arrange
		BusinessObject aBusinessObject = new Application("application/test_id_" + UUID.randomUUID(), "", "", null, null);
		
		//act
		boolean exists = DatabaseController.checkIfExistsInDB(aBusinessObject);
		
		//assert
		assertFalse(exists);
	}
}

package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;
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
	
	@Test
	public void testThatApplicationIsStoredToDatabase() throws SQLException {
		//arrange
		Application anApplication = new Application("application/test_id_4", "Moodle", "Moodle Description", null, null);

		//act
		DatabaseController.storeToDatabase(anApplication);

		//assert
		Application loaded = (Application) DatabaseController.load(anApplication.getUuid());
		assertTrue(anApplication.equals(loaded));
	}
}
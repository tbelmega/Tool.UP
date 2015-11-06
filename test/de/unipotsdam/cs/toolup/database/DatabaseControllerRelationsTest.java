package de.unipotsdam.cs.toolup.database;

import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.APPLICATION_TEST_ID_1;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.APPLICATION_TEST_ID_2;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.CATEGORY_TEST_ID_11;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.FEATURE_TEST_ID_21;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.FEATURE_TEST_ID_22;
import static org.testng.AssertJUnit.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;

public class DatabaseControllerRelationsTest {
	
	private DatabaseController db;

	@BeforeClass
	public void setUp() throws IOException, SQLException {
		db = DatabaseController.getInstance();
	}
	
	@Test
	public void testThatLoadedCategoryHasRelatedApplications() throws SQLException {
		//arrange
		Collection<String> expectedAppIds = Arrays.asList(new String[] {APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2});

		//act
		Category cat = (Category) db.load(CATEGORY_TEST_ID_11);

		//assert
		assertTrue(cat.getRelatedApplications().containsAll(expectedAppIds));
	}
	
	@Test
	public void testThatLoadedFeatureHasRelatedApplications() throws SQLException {
		//arrange
		Collection<String> expectedAppIds = Arrays.asList(new String[] {APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2});
		
		//act
		Feature feat = (Feature) db.load(FEATURE_TEST_ID_21);
		
		//assert
		assertTrue(feat.getRelatedApplications().containsAll(expectedAppIds));
	}
	
	@Test
	public void testThatLoadedApplicationHasRelatedCategories() throws SQLException {
		//arrange
		Collection<String> expectedCatIds = Arrays.asList(new String[] {CATEGORY_TEST_ID_11});
		
		//act
		Application app = (Application) db.load(APPLICATION_TEST_ID_1);
		
		//assert
		assertTrue(app.getRelatedCategories().containsAll(expectedCatIds));
	}
	
	@Test
	public void testThatLoadedApplicationHasRelatedFeatures() throws SQLException {
		//arrange
		Collection<String> expectedFeatureIds = Arrays.asList(new String[] {FEATURE_TEST_ID_21,FEATURE_TEST_ID_22});
		
		//act
		Application app = (Application) db.load(APPLICATION_TEST_ID_1);
		
		//assert
		assertTrue(app.getRelatedFeatures().containsAll(expectedFeatureIds));
	}
	
	@Test
	public void testThatInsertedApplicationStoresRelations() throws SQLException {
		//arrange
		String uuid = "application/" + UUID.randomUUID();
		Application someApplication = (Application) BusinessObjectFactory.createInstance(uuid,"AAA","aaaaa");
		someApplication.addRelation(CATEGORY_TEST_ID_11);
		someApplication.addRelation(FEATURE_TEST_ID_21);
		someApplication.addRelation(FEATURE_TEST_ID_22);

		//act
		db.storeToDatabase(someApplication);

		//assert
		BusinessObject loadedApplication = db.load(uuid);
		Set<String> expectedRelations = someApplication.getRelatedBOs();
		Set<String> actualRelations = loadedApplication.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
		
		//clean up /TODO: clean up even if test fails
		db.deleteFromDatabase(uuid);
	}
	
	@Test
	public void testThatInsertedCategoryStoresRelations() throws SQLException {
		//arrange
		String uuid = "category/" + UUID.randomUUID();
		Category someCategory = (Category) BusinessObjectFactory.createInstance(uuid,"AAA","aaaaa");
		someCategory.addRelation(APPLICATION_TEST_ID_1);
		someCategory.addRelation(APPLICATION_TEST_ID_2);
		
		//act
		db.storeToDatabase(someCategory);
		
		//assert
		BusinessObject loadedCategory = db.load(uuid);
		Set<String> expectedRelations = someCategory.getRelatedBOs();
		Set<String> actualRelations = loadedCategory.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
		
		//clean up /TODO: clean up even if test fails
		db.deleteFromDatabase(uuid);
	}
	
	@Test
	public void testThatInsertedFeatureStoresRelations() throws SQLException {
		//arrange
		String uuid = "feature/" + UUID.randomUUID();
		Feature someFeature = (Feature) BusinessObjectFactory.createInstance(uuid,"AAA","aaaaa");
		someFeature.addRelation(APPLICATION_TEST_ID_1);
		someFeature.addRelation(APPLICATION_TEST_ID_2);
		
		//act
		db.storeToDatabase(someFeature);
		
		//assert
		BusinessObject loadedFeature = db.load(uuid);
		Set<String> expectedRelations = someFeature.getRelatedBOs();
		Set<String> actualRelations = loadedFeature.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
		
		//clean up /TODO: clean up even if test fails
		db.deleteFromDatabase(uuid);
	}
	
	@Test
	public void testThatUpdatedApplicationStoresRelations() throws SQLException {
		//arrange
		String uuid = "application/" + UUID.randomUUID();
		Application someApplication = (Application) BusinessObjectFactory.createInstance(uuid,"AAA","aaaaa");
		db.storeToDatabase(someApplication);
		someApplication.addRelation(CATEGORY_TEST_ID_11);
		someApplication.addRelation(FEATURE_TEST_ID_21);
		someApplication.addRelation(FEATURE_TEST_ID_22);
		
		//act
		db.storeToDatabase(someApplication);
		
		//assert
		BusinessObject loadedApplication = db.load(uuid);
		Set<String> expectedRelations = someApplication.getRelatedBOs();
		Set<String> actualRelations = loadedApplication.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
		
		//clean up /TODO: clean up even if test fails
		db.deleteFromDatabase(uuid);
		
		//clean up /TODO: clean up even if test fails
		db.deleteFromDatabase(uuid);
	}
	
	@Test
	public void testThatUpdatedCategoryStoresRelations() throws SQLException {
		//arrange
		String uuid = "category/" + UUID.randomUUID();
		Category someCategory = (Category) BusinessObjectFactory.createInstance(uuid,"AAA","aaaaa");
		db.storeToDatabase(someCategory);
		someCategory.addRelation(APPLICATION_TEST_ID_1);
		someCategory.addRelation(APPLICATION_TEST_ID_2);
		
		//act
		db.storeToDatabase(someCategory);
		
		//assert
		BusinessObject loadedCategory = db.load(uuid);
		Set<String> expectedRelations = someCategory.getRelatedBOs();
		Set<String> actualRelations = loadedCategory.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
		
		//clean up /TODO: clean up even if test fails
		db.deleteFromDatabase(uuid);
	}
	
	@Test
	public void testThatUpdatedFeatureStoresRelations() throws SQLException {
		//arrange
		String uuid = "feature/" + UUID.randomUUID();
		Feature someFeature = (Feature) BusinessObjectFactory.createInstance(uuid,"AAA","aaaaa");
		db.storeToDatabase(someFeature);
		someFeature.addRelation(APPLICATION_TEST_ID_1);
		someFeature.addRelation(APPLICATION_TEST_ID_2);
		
		//act
		db.storeToDatabase(someFeature);
		
		//assert
		BusinessObject loadedFeature = db.load(uuid);
		Set<String> expectedRelations = someFeature.getRelatedBOs();
		Set<String> actualRelations = loadedFeature.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
		
		//clean up /TODO: clean up even if test fails
		db.deleteFromDatabase(uuid);
	}
	
}

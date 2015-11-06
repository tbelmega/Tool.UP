package de.unipotsdam.cs.toolup.database;

import static de.unipotsdam.cs.toolup.database.DatabaseController.*;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.APPLICATION_TEST_ID_1;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.APPLICATION_TEST_ID_2;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.CATEGORY_TEST_ID_11;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.FEATURE_TEST_ID_21;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.FEATURE_TEST_ID_22;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;

public class DatabaseControllerRelationsTest extends AbstractDatabaseTest {
	
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
		
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES_AND_RELATIONS)
	public void testThatInsertedBOStoresRelations(String tablename, String[] relatedIds) throws SQLException {
		//arrange		
		BusinessObject someBO = BusinessObjectFactory.createInstanceWithNewUuid(tablename);
		for (String id: Arrays.asList(relatedIds)){
			someBO.addRelation(id);			
		}
		
		//act
		db.storeToDatabase(someBO);
		
		//assert
		BusinessObject loadedApplication = db.load(someBO.getUuid());
		Set<String> expectedRelations = someBO.getRelatedBOs();
		Set<String> actualRelations = loadedApplication.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
	}
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES_AND_RELATIONS)
	public void testThatUpdatedBOStoresRelations(String tablename, String[] relatedIds) throws SQLException {
		//arrange		
			//store new BO without relations
		BusinessObject someBO = BusinessObjectFactory.createInstanceWithNewUuid(tablename);
		db.storeToDatabase(someBO);
		
			//add relations, that should be stored to DB by update
		for (String id: Arrays.asList(relatedIds)){
			someBO.addRelation(id);			
		}
		
		//act
		db.storeToDatabase(someBO);
		
		//assert
		BusinessObject loadedApplication = db.load(someBO.getUuid());
		Set<String> expectedRelations = someBO.getRelatedBOs();
		Set<String> actualRelations = loadedApplication.getRelatedBOs();
		assertTrue(actualRelations.containsAll(expectedRelations));
	}
	
}

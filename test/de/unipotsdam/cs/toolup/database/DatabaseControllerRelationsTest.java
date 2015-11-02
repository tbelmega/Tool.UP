package de.unipotsdam.cs.toolup.database;

import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.APPLICATION_TEST_ID_1;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.APPLICATION_TEST_ID_2;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.CATEGORY_TEST_ID_11;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.FEATURE_TEST_ID_21;
import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.FEATURE_TEST_ID_22;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;

public class DatabaseControllerRelationsTest {
	
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
	
}

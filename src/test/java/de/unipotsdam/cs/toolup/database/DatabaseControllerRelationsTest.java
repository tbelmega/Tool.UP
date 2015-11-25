package de.unipotsdam.cs.toolup.database;

import de.unipotsdam.cs.toolup.model.*;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider.*;
import static org.testng.AssertJUnit.assertTrue;

public class DatabaseControllerRelationsTest extends AbstractDatabaseTest {

    @Test
    public void testThatLoadedCategoryHasRelatedApplications() throws Exception {
        //arrange
        Collection<String> expectedAppIds = Arrays.asList(APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2);

        //act
        Category cat = (Category) db.load(CATEGORY_TEST_ID_11);

        //assert
        assertTrue(cat.getRelatedApplications().containsAll(expectedAppIds));
    }

    @Test
    public void testThatLoadedFeatureHasRelatedApplications() throws Exception {
        //arrange
        Collection<String> expectedAppIds = Arrays.asList(APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2);

        //act
        Feature feat = (Feature) db.load(FEATURE_TEST_ID_21);

        //assert
        assertTrue(feat.getRelatedApplications().containsAll(expectedAppIds));
    }

    @Test
    public void testThatLoadedApplicationHasRelatedCategories() throws Exception {
        //arrange
        Collection<String> expectedCatIds = Collections.singletonList(CATEGORY_TEST_ID_11);

        //act
        BusinessObject app = db.load(APPLICATION_TEST_ID_1);

        //assert
        assertTrue(app.getRelatedBOs().containsAll(expectedCatIds));
    }

    @Test
    public void testThatLoadedApplicationHasRelatedFeatures() throws Exception {
        //arrange
        Collection<String> expectedFeatureIds = Arrays.asList(FEATURE_TEST_ID_21, FEATURE_TEST_ID_22);

        //act
        Application app = (Application) db.load(APPLICATION_TEST_ID_1);

        //assert
        assertTrue(app.getRelatedFeatures().containsAll(expectedFeatureIds));
    }

    @Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES_AND_RELATIONS)
    public void testThatInsertedBOStoresRelations(String tablename, String[] relatedIds) throws Exception {
        //arrange
        BusinessObject someBO = BusinessObjectFactory.createInstanceWithNewUuid(tablename);
        for (String id : Arrays.asList(relatedIds)) {
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
    public void testThatUpdatedBOStoresRelations(String tablename, String[] relatedIds) throws Exception {
        //arrange
        //store new BO without relations
        BusinessObject someBO = BusinessObjectFactory.createInstanceWithNewUuid(tablename);
        db.storeToDatabase(someBO);

        //add relations, that should be stored to DB by update
        for (String id : Arrays.asList(relatedIds)) {
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

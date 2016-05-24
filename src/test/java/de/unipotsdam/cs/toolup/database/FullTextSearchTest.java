package de.unipotsdam.cs.toolup.database;

import de.unipotsdam.cs.toolup.model.BusinessObject;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.APPLICATION_TEST_ID_1;
import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.APPLICATION_TEST_ID_2;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertCollectionEquals;

public class FullTextSearchTest extends AbstractDatabaseTest {

    /**
     * Full text search for "Dropbox" should return only APP_1, which has the search string as app title and in app description.
     * @throws Exception
     */
    @Test
    public void testThatFullTextSearchMatchesAppTitel() throws Exception {
        //arrange
        Collection<String> expectedAppIDs = Arrays.asList(APPLICATION_TEST_ID_1);

        //act
        Map<String, BusinessObject> loadedApps = db.doFullTextSearch("Dropbox");

        //assert
        assertCollectionEquals(expectedAppIDs, loadedApps.keySet());
    }

    /**
     * Full text search for "description" should return APP_1 and APP_2, which have the search string in their description.
     * @throws Exception
     */
    @Test
    public void testThatFullTextSearchMatchesAppDescription() throws Exception {
        //arrange
        Collection<String> expectedAppIDs = Arrays.asList(APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2);

        //act
        Map<String, BusinessObject> loadedApps = db.doFullTextSearch("description");

        //assert
        assertCollectionEquals(expectedAppIDs, loadedApps.keySet());
    }

    /**
     * Full text search for "Kalender" should return APP_1 and APP_2, which have FEAT_21 with the search string in its title.
     * @throws Exception
     */
    @Test
    public void testThatFullTextSearchMatchesFeatureTitel() throws Exception {
        //arrange
        Collection<String> expectedAppIDs = Arrays.asList(APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2);

        //act
        Map<String, BusinessObject> loadedApps = db.doFullTextSearch("Kalender");

        //assert
        assertCollectionEquals(expectedAppIDs, loadedApps.keySet());
    }

    /**
     * Full text search for "teilen " should return only APP_1, which has FEAT_22 with the search string in its description.
     * @throws Exception
     */
    @Test
    public void testThatFullTextSearchMatchesFeatureDescription() throws Exception {
        //arrange
        Collection<String> expectedAppIDs = Arrays.asList(APPLICATION_TEST_ID_1);

        //act
        Map<String, BusinessObject> loadedApps = db.doFullTextSearch("teilen ");

        //assert
        assertCollectionEquals(expectedAppIDs, loadedApps.keySet());
    }
}

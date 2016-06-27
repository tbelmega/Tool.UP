package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.database.AbstractDatabaseTest;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by tbelm on 26.06.2016.
 * Test the new functionality of the string search,
 * based on the test data in the database.
 * <p>
 * An Application should be returned, if the search string is contained either in its title or description,
 * or in the title or description of one of its features.
 */
public class ApplicationStringSearchTest extends AbstractDatabaseTest {

    public static final String FEATURE_TEST_ID = "feature-" + UUID.randomUUID().toString();
    public static final String APPLICATION_TEST_ID = "application-" + UUID.randomUUID().toString();

    private static Feature testFeature;
    private static Application testApplication;

    @BeforeClass
    public void setUpTestData() throws InvalidIdException, SQLException {
        testApplication = new Application(APPLICATION_TEST_ID, "Test-application-1234", "456789",
                new HashSet<String>(), new HashSet<String>());

        testFeature = new Feature(FEATURE_TEST_ID, "test-feature", null, new HashSet<String>());
        testFeature.addRelation(testApplication.getUuid());


        db.storeToDatabase(testApplication);
        db.storeToDatabase(testFeature);
    }

    @Test
    public void testThatSearchForFullTitleReturnsApp() throws Exception {
        //arrange

        //act
        Set<Application> result = new ApplicationStringSearch("Test-application-1234").getResult();


        //assert
        assertEquals(1, result.size());
        for (Application app : result) {
            assertEquals("Test-application-1234", app.getTitle());
        }
    }

    @Test
    public void testThatSearchForLowercaseTitleReturnsApp() throws Exception {
        //arrange

        //act
        Set<Application> result = new ApplicationStringSearch("test-application-1234").getResult();


        //assert
        assertEquals(1, result.size());
        for (Application app : result) {
            assertEquals("Test-application-1234", app.getTitle());
        }
    }

    @Test
    public void testThatSearchForTitleSubstringReturnsApp() throws Exception {
        //arrange

        //act
        Set<Application> result = new ApplicationStringSearch("123").getResult();


        //assert
        assertEquals(1, result.size());
        for (Application app : result) {
            assertEquals("Test-application-1234", app.getTitle());
        }
    }

    @Test
    public void testThatSearchForFeatureTitleReturnsApp() throws Exception {
        //arrange

        //act
        Set<Application> result = new ApplicationStringSearch("test-feature").getResult();


        //assert
        assertEquals(1, result.size());
        for (Application app : result) {
            assertEquals("Test-application-1234", app.getTitle());
        }
    }
}

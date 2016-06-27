package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.database.AbstractDatabaseTest;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by tbelm on 26.06.2016.
 */
public class ApplicationTest extends AbstractDatabaseTest {

    public static final String TITLE = "title1";
    public static final String DESCRIPTION = "description1";
    public static final String SHORT_DESCRIPTION = "shortdesc1";
    public static final String PROVIDER = "provider1";
    public static final String CONTACT = "contact1";
    private static final String APPLICATION_TEST_ID = "application-test_id_" + UUID.randomUUID().toString();

    @Test
    public void testThatApplicationHasAllProperties() throws Exception {
        //arrange
        Application app = new Application(APPLICATION_TEST_ID, TITLE, DESCRIPTION, SHORT_DESCRIPTION, PROVIDER, CONTACT,
                new HashSet<String>(), new HashSet<String>());
        db.storeToDatabase(app);

        //act
        Application loadedApp = (Application) db.load(APPLICATION_TEST_ID);


        //assert
        assertEquals(TITLE, loadedApp.getTitle());
        assertEquals(DESCRIPTION, loadedApp.getDescription());
        assertEquals(SHORT_DESCRIPTION, loadedApp.getShortDescription());
        assertEquals(CONTACT, loadedApp.getContact());
        assertEquals(PROVIDER, loadedApp.getProvider());
    }
}

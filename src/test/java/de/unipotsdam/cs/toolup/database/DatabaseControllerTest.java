package de.unipotsdam.cs.toolup.database;

import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.*;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_APPLICATION;
import static de.unipotsdam.cs.toolup.model.BusinessObject.ID_DELIMITER;
import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static org.testng.AssertJUnit.*;

public class DatabaseControllerTest extends AbstractDatabaseTest {

    @Test(dataProvider = DatabaseControllerDataProvider.PROVIDE_BUSINESS_OBJECTS, dataProviderClass = DatabaseControllerDataProvider.class)
    public void testThatLoadedBusinessObjectHasExpectedValues(String expectedTitle, String expectedDescription, Class<? extends BusinessObject> expectedClass, String id) throws Exception {
        //arrange

        //act
        BusinessObject busObj = db.load(id);

        //assert
        assertEquals(expectedTitle, busObj.getTitle());
        assertEquals(expectedDescription, busObj.getDescription());
        assertEquals(expectedClass, busObj.getClass());
    }


    @Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
    public void testThatBusinessObjectIsInsertedIntoDatabase(String tableName) throws Exception {
        //arrange
        BusinessObject aBusinessObject = BusinessObjectFactory.createInstanceWithNewUuid(tableName);
        assertFalse(db.checkIfExistsInDB(aBusinessObject));

        //act
        db.storeToDatabase(aBusinessObject);

        //assert
        BusinessObject loaded = db.load(aBusinessObject.getUuid());
        assertTrue(aBusinessObject.equals(loaded));
    }

    @Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
    public void testThatBusinessObjectIsDeletedFromDatabase(String tableName) throws Exception {
        //arrange
        String id = tableName + ID_DELIMITER + UUID.randomUUID();
        BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id);
        db.storeToDatabase(aBusinessObject);
        assertTrue(db.checkIfExistsInDB(aBusinessObject));

        //act
        db.deleteFromDatabase(id);

        //assert
        assertFalse(db.checkIfExistsInDB(aBusinessObject));
    }

    @Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BUSINESS_OBJECTS)
    public void testThatCheckExistReturnsTrue(String title, String description, Class<? extends BusinessObject> clazz, String id) throws Exception {
        //arrange
        BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id);

        //act
        boolean exists = db.checkIfExistsInDB(aBusinessObject);
        boolean existsById = db.checkIfExistsInDB(id);

        //assert
        assertTrue(exists);
        assertTrue(existsById);
    }

    @Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
    public void testThatCheckExistReturnsFalse(String tablename) throws Exception {
        //arrange
        BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(tablename + ID_DELIMITER + UUID.randomUUID());

        //act
        boolean exists = db.checkIfExistsInDB(aBusinessObject);

        //assert
        assertFalse(exists);
    }

    @Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
    public void testThatBusinessObjectIsUpdated(String tablename) throws Exception {
        //arrange
        String id = tablename + ID_DELIMITER + UUID.randomUUID();
        BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id, "aaa", "AAAAA");
        db.storeToDatabase(aBusinessObject);

        //act
        BusinessObject modifiedBusinessObject = BusinessObjectFactory.createInstance(id, "bbb", "BBBBBB");
        db.storeToDatabase(modifiedBusinessObject);

        //assert
        BusinessObject loadedBO = db.load(id);
        assertTrue(modifiedBusinessObject.equals(loadedBO));
        assertEquals(modifiedBusinessObject.getTitle(), loadedBO.getTitle());
    }

    @Test
    public void testThatDatabaseControllerHelperDeletesObjects() throws Exception {
        //arrange
        String id = TABLE_NAME_APPLICATION + ID_DELIMITER + UUID.randomUUID();
        BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id);
        db.storeToDatabase(aBusinessObject);
        assertTrue(db.checkIfExistsInDB(aBusinessObject));

        //act
        db.deleteCreatedBOsFromDatabase();

        //assert
        assertFalse(db.checkIfExistsInDB(aBusinessObject));
    }

    @Test(dataProvider = "provideForLoadAllFromTable")
    public void testThatAllBusinessObjectsAreLoaded(List<String> expectedBOs, String tableName) throws SQLException {
        //arrange

        //act
        Map<String, BusinessObject> loadedBOs = db.loadAllFrom(tableName);

        //assert
        Set<String> ids = loadedBOs.keySet();
        assertTrue("Loaded BusinessObjects do not contain all expected.", ids.containsAll(expectedBOs));
    }

    @DataProvider
    public Iterator<Object[]> provideForLoadAllFromTable() {
        List<Object[]> data = new LinkedList<>();

        data.add(new Object[]{Arrays.asList(
                APPLICATION_TEST_ID_1,
                APPLICATION_TEST_ID_2),
                "application"});

        data.add(new Object[]{Arrays.asList(
                CATEGORY_TEST_ID_11,
                CATEGORY_TEST_ID_12),
                "category"});

        data.add(new Object[]{Arrays.asList(
                FEATURE_TEST_ID_21,
                FEATURE_TEST_ID_22),
                "feature"});

        return data.iterator();
    }
}

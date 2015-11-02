package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.sql.SQLException;
import java.util.UUID;

import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;




public class DatabaseControllerTest {

	@Test(dataProvider = DatabaseControllerDataProvider.PROVIDE_BUSINESS_OBJECTS, dataProviderClass = DatabaseControllerDataProvider.class)
	public void testThatLoadedBusinessObjectHasExpectedValues(String expectedTitle, String expectedDescription, Class<? extends BusinessObject> expectedClass, String id) throws SQLException {
		//arrange

		//act
		BusinessObject busObj = DatabaseController.load(id);
		
		//assert
		assertEquals(expectedTitle, busObj.getTitle());
		assertEquals(expectedDescription, busObj.getDescription());
		assertEquals(expectedClass, busObj.getClass());
	}


	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
	public void testThatBusinessObjectIsInsertedIntoDatabase(String tablename) throws SQLException {
		//arrange
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(tablename + "/" + UUID.randomUUID());
		assertFalse(DatabaseController.checkIfExistsInDB(aBusinessObject));

		//act
		DatabaseController.storeToDatabase(aBusinessObject);

		//assert
		BusinessObject loaded = DatabaseController.load(aBusinessObject.getUuid());
		assertTrue(aBusinessObject.equals(loaded));
		
		//clean up
		DatabaseController.deleteFromDatabase(aBusinessObject.getUuid());
	}
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
	public void testThatBusinessObjectIsDeletedFromDatabase(String tablename) throws SQLException {
		//arrange
		String id = tablename + "/" + UUID.randomUUID();
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id);
		DatabaseController.storeToDatabase(aBusinessObject);
		assertTrue(DatabaseController.checkIfExistsInDB(aBusinessObject));
		
		//act
		DatabaseController.deleteFromDatabase(id);

		//assert
		assertFalse(DatabaseController.checkIfExistsInDB(aBusinessObject));
	}
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BUSINESS_OBJECTS)
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
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
	public void testThatCheckExistReturnsFalse(String tablename) throws SQLException {
		//arrange
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(tablename + "/" + UUID.randomUUID());
		
		//act
		boolean exists = DatabaseController.checkIfExistsInDB(aBusinessObject);
		
		//assert
		assertFalse(exists);
	}
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
	public void testThatBusinessObjectIsUpdated(String tablename) throws SQLException {
		//arrange
		String id = tablename + "/" + UUID.randomUUID();
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id, "aaa", "AAAAA");
		DatabaseController.storeToDatabase(aBusinessObject);

		//act
		aBusinessObject = BusinessObjectFactory.createInstance(id, "bbb", "BBBBBB");
		DatabaseController.storeToDatabase(aBusinessObject);

		//assert
		BusinessObject loadedBO = DatabaseController.load(id);
		assertTrue(aBusinessObject.equals(loadedBO));
		
		//clean up
		DatabaseController.deleteFromDatabase(id);
	}
}

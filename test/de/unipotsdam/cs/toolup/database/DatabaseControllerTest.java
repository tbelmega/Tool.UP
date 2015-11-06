package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;

public class DatabaseControllerTest {
	
	private DatabaseController db;

	@BeforeClass
	public void setUp() throws IOException, SQLException {
		db = DatabaseController.getInstance();
	}

	@Test(dataProvider = DatabaseControllerDataProvider.PROVIDE_BUSINESS_OBJECTS, dataProviderClass = DatabaseControllerDataProvider.class)
	public void testThatLoadedBusinessObjectHasExpectedValues(String expectedTitle, String expectedDescription, Class<? extends BusinessObject> expectedClass, String id) throws SQLException {
		//arrange

		//act
		BusinessObject busObj = db.load(id);
		
		//assert
		assertEquals(expectedTitle, busObj.getTitle());
		assertEquals(expectedDescription, busObj.getDescription());
		assertEquals(expectedClass, busObj.getClass());
	}


	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
	public void testThatBusinessObjectIsInsertedIntoDatabase(String tablename) throws SQLException {
		//arrange
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(tablename + "/" + UUID.randomUUID());
		assertFalse(db.checkIfExistsInDB(aBusinessObject));

		//act
		db.storeToDatabase(aBusinessObject);

		//assert
		BusinessObject loaded = db.load(aBusinessObject.getUuid());
		assertTrue(aBusinessObject.equals(loaded));
		
		//clean up
		db.deleteFromDatabase(aBusinessObject.getUuid());
	}
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
	public void testThatBusinessObjectIsDeletedFromDatabase(String tablename) throws SQLException {
		//arrange
		String id = tablename + "/" + UUID.randomUUID();
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id);
		db.storeToDatabase(aBusinessObject);
		assertTrue(db.checkIfExistsInDB(aBusinessObject));
		
		//act
		db.deleteFromDatabase(id);

		//assert
		assertFalse(db.checkIfExistsInDB(aBusinessObject));
	}
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BUSINESS_OBJECTS)
	public void testThatCheckExistReturnsTrue(String title, String description, Class<? extends BusinessObject> clazz, String id) throws SQLException {
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
	public void testThatCheckExistReturnsFalse(String tablename) throws SQLException {
		//arrange
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(tablename + "/" + UUID.randomUUID());
		
		//act
		boolean exists = db.checkIfExistsInDB(aBusinessObject);
		
		//assert
		assertFalse(exists);
	}
	
	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BO_TABLES)
	public void testThatBusinessObjectIsUpdated(String tablename) throws SQLException {
		//arrange
		String id = tablename + "/" + UUID.randomUUID();
		BusinessObject aBusinessObject = BusinessObjectFactory.createInstance(id, "aaa", "AAAAA");
		db.storeToDatabase(aBusinessObject);

		//act
		BusinessObject modifiedBusinessObject = BusinessObjectFactory.createInstance(id, "bbb", "BBBBBB");
		db.storeToDatabase(modifiedBusinessObject);

		//assert
		BusinessObject loadedBO = db.load(id);
		assertTrue(modifiedBusinessObject.equals(loadedBO));
		assertEquals(modifiedBusinessObject.getTitle(), loadedBO.getTitle());
		
		//clean up
		db.deleteFromDatabase(id);
	}
}

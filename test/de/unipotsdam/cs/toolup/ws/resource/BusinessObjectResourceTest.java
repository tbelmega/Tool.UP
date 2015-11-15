package de.unipotsdam.cs.toolup.ws.resource;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import javax.ws.rs.core.Response;

import org.testng.annotations.Test;

import de.unipotsdam.cs.toolup.database.DatabaseControllerDataProvider;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;

public class BusinessObjectResourceTest {

	@Test(dataProviderClass = DatabaseControllerDataProvider.class, dataProvider = DatabaseControllerDataProvider.PROVIDE_BUSINESS_OBJECTS)
	public void testThatGetValidIdReturnsStatusCodeOK(String title, String description, Class<? extends BusinessObject> clazz, String id) throws Exception {
		//arrange
		 BusinessObject expectedBO = BusinessObjectFactory.createInstance(id);

		//act
		Response testResponse = new BusinessObjectResource().getBusinessObject(expectedBO.getUuid());

		//assert
		assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
		BusinessObject result = (BusinessObject) testResponse.getEntity();
		assertTrue(expectedBO.equals(result));
	}
	 
	 @Test
	 public void testThatGetInvalidIdReturnsNotFound() {
		 //arrange
		 
		 //act
		 Response testResponse = new ApplicationResource().get("foo");
		 
		 //assert
		 assertEquals(Response.Status.NOT_FOUND.getStatusCode(), testResponse.getStatus());
	 }
}

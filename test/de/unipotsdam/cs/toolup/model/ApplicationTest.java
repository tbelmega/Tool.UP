package de.unipotsdam.cs.toolup.model;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class ApplicationTest {

	private static final String TEST_ID_2 = "test_id_2";
	private static final String TEST_ID_1 = "test_id_1";

	@Test
	public void testThatAnApplicationDoesNotEqualAnOtherApplication() {
		//arrange
		Application anOtherApp = new Application(TEST_ID_1,null,null);
		
		//act
		Application newApp = new Application(TEST_ID_2,null,null);

		//assert
		assertFalse(anOtherApp.equals(newApp));
	}
	
	@Test
	public void testThatAnApplicationDoesEqualItself() {
		//arrange
		Application anOtherApp = new Application(TEST_ID_1,null,null);
		
		//act
		Application newApp = new Application(TEST_ID_1,null,null);
		
		//assert
		assertTrue(anOtherApp.equals(newApp));
	}
	
}

package de.unipotsdam.cs.toolup.database;

import static org.testng.AssertJUnit.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ToolUpPropertiesTest {

	private static final String KEY_SERVERADRESS = "database server adress";
	private static final String VALUE_SERVERADRESS = "localhost";
	private static final String KEY_SERVERPORT = "database server port";
	private static final Object VALUE_SERVERPORT = "3306";
	private static final String KEY_USERNAME = "database user name";
	private static final String VALUE_USERNAME = "root";
	private static final String KEY_PASSWORD = "database password";
	private static final String VALUE_PASSWORD = "";
	private static final String KEY_SCHEMA = "database schema";
	private static final String VALUE_SCHEMA = "test";
	
	File PROPERTIES_FILE = new File("Tool.UP_cfg_template.xml");
	
	@BeforeMethod
	public void prepareConfigFile() throws FileNotFoundException, IOException{
		Properties props = new Properties();
		props.put(KEY_SERVERADRESS, VALUE_SERVERADRESS);
		props.put(KEY_SERVERPORT, VALUE_SERVERPORT);
		props.put(KEY_USERNAME, VALUE_USERNAME);
		props.put(KEY_PASSWORD, VALUE_PASSWORD);
		props.put(KEY_SCHEMA, VALUE_SCHEMA);
		props.storeToXML(new FileOutputStream(PROPERTIES_FILE), null, "utf-8");
	}
	
	@Test
	public void testThatPropertiesAreLoaded() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		//arrange

		//act
		Properties props = ToolUpProperties.getProperties();
		
		//assert
		assertEquals(VALUE_SERVERADRESS,props.getProperty(KEY_SERVERADRESS));
		assertEquals(VALUE_SERVERPORT,props.getProperty(KEY_SERVERPORT));
		assertEquals(VALUE_USERNAME,props.getProperty(KEY_USERNAME));
		assertEquals(VALUE_PASSWORD,props.getProperty(KEY_PASSWORD));
		assertEquals(VALUE_SCHEMA,props.getProperty(KEY_SCHEMA));
	}
	
	
}

package de.unipotsdam.cs.toolup.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class ToolUpProperties {

	private static final String DB_USERNAME = "root";
	private static final String DB_SCHEMA = "test";
	private static final String SERVER_PORT = "3306";
	private static final String SERVER_ADRESS = "localhost";
	private static final String DATABASE_CONNECTOR = "jdbc:mysql://";
	private static final String DB_PASSWORD = "";
	private static String DATABASE_URL = null;
	private static Properties PROPERTIES = null;
	
	static File PROPERTIES_FILE = new File("Tool.UP_cfg.xml");

	public static String getDatabaseUrl() {
		if (DATABASE_URL == null){
			DATABASE_URL = DATABASE_CONNECTOR+SERVER_ADRESS+":"+SERVER_PORT+"/"+DB_SCHEMA+"?user="+DB_USERNAME+"&pw=" + DB_PASSWORD;
		}
		return DATABASE_URL;
	}

	public static Properties getProperties() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		if (PROPERTIES == null){
			loadPropertiesFromFile();
		}
		return PROPERTIES;
	}

	private static synchronized void loadPropertiesFromFile() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		if (PROPERTIES == null){
			PROPERTIES = new Properties(); 
			PROPERTIES.loadFromXML(new FileInputStream(PROPERTIES_FILE));		
		}
	}

}

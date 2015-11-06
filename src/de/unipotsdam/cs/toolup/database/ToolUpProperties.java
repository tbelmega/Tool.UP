package de.unipotsdam.cs.toolup.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class ToolUpProperties {

	private static final String KEY_SERVERADRESS = "database server adress";
	private static final String KEY_SERVERPORT = "database server port";
	private static final String KEY_USERNAME = "database user name";
	private static final String KEY_PASSWORD = "database password";
	private static final String KEY_SCHEMA = "database schema";
	
	private static final String DATABASE_CONNECTOR = "jdbc:mysql://";
	
	private static String DATABASE_URL = null;
	private static Properties PROPERTIES = null;
	
	static File PROPERTIES_FILE = new File("Tool.UP_cfg.xml");

	/**
	 * Constructs the database url out of the read properties.
	 * @return
	 * @throws IOException
	 */
	public static String getDatabaseUrl() throws IOException {
		if (DATABASE_URL == null){
			DATABASE_URL = new StringBuilder()
			.append(DATABASE_CONNECTOR)
			.append(getProperties().getProperty(KEY_SERVERADRESS) )
			.append( ":"+getProperties().getProperty(KEY_SERVERPORT))
			.append("/"+getProperties().getProperty(KEY_SCHEMA))
			.append("?user="+getProperties().getProperty(KEY_USERNAME+"&pw=" ))
			.append( getProperties().getProperty(KEY_PASSWORD))
			.toString();
		}
		return DATABASE_URL;
	}

	/**
	 * 
	 * @return the ToolUp singleton Properties object
	 * @throws IOException
	 */
	public static Properties getProperties() throws IOException {
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

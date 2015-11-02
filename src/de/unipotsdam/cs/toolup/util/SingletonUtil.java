package de.unipotsdam.cs.toolup.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * 
 * @author tbelmega
 * This class provides a thread-safe helper method to implement the singleton pattern for a Properties field.
 *
 */
public class SingletonUtil {
	
	public static synchronized Properties loadFromFile(Properties p, File f) throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		if (p == null){
			p = new Properties(); 
			p.loadFromXML(new FileInputStream(f));		
		}
		return p;
	}

}

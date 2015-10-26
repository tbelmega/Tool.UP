package de.unipotsdam.cs.toolup.database;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;

public class BusinessObjectFactory {

	public static BusinessObject createBusinessObject(String id, String title,
			String description) {
		int indexOfFirstSlash = id.indexOf('/');
		String className =  id.substring(0, indexOfFirstSlash);
		
		switch (className.toLowerCase()){
		case "application": return new Application(id, title, description);
		case "category": return new Category(id, title, description);
		case "feature": return new Feature(id, title, description);
		default: throw new UnsupportedOperationException("No class defined for this prefix:" + className);
		}
	}

}

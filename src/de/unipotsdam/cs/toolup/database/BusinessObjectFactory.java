package de.unipotsdam.cs.toolup.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;

public class BusinessObjectFactory {

	public static BusinessObject createBusinessObject(String id, String title,
			String description) throws SQLException {
		int indexOfFirstSlash = id.indexOf('/');
		String className =  id.substring(0, indexOfFirstSlash);
		
		switch (className.toLowerCase()){
		case "application": return new Application(id, title, description, DatabaseController.loadRelatedCategoriesForApp(id), DatabaseController.loadRelatedFeaturesForApp(id));
		case "category": return new Category(id, title, description, DatabaseController.loadRelatedApplicationsForCat(id));
		case "feature": return new Feature(id, title, description, DatabaseController.loadRelatedApplicationsForFeat(id));
		default: throw new UnsupportedOperationException("No class defined for this prefix:" + className);
		}
	}

	static BusinessObject createBusinessObjectFromSingleResult(
			ResultSet res) throws SQLException {
		res.first();
		String id = res.getString("uuid");
		String title = res.getString("title");
		String description = res.getString("description");
		
		return createBusinessObject(id,title,description);
	}

}
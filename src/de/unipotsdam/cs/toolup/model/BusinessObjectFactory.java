package de.unipotsdam.cs.toolup.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;
import static de.unipotsdam.cs.toolup.model.BusinessObject.*;

public class BusinessObjectFactory {

	private static final String KEY_UUID = "uuid";
	private static final String TABLENAME_FEATURE = "feature";
	private static final String TABLENAME_CATEGORY = "category";
	private static final String TABLENAME_APPLICATION = "application";

	private static BusinessObject createBusinessObjectWithLoadedRelations(String id, String title,
			String description) throws SQLException {
		int indexOfFirstSlash = id.indexOf('/');
		String className =  id.substring(0, indexOfFirstSlash);
		
		switch (className.toLowerCase()){
		case TABLENAME_APPLICATION: return new Application(id, title, description, DatabaseController.loadRelatedCategoriesForApp(id), DatabaseController.loadRelatedFeaturesForApp(id));
		case TABLENAME_CATEGORY: return new Category(id, title, description, DatabaseController.loadRelatedApplicationsForCat(id));
		case TABLENAME_FEATURE: return new Feature(id, title, description, DatabaseController.loadRelatedApplicationsForFeat(id));
		default: throw new UnsupportedOperationException("No class defined for this prefix:" + className);
		}
	}

	public static BusinessObject createBusinessObjectFromSingleResult(
			ResultSet res) throws SQLException {
		res.first();
		String id = res.getString(KEY_UUID);
		String title = res.getString(KEY_TITLE);
		String description = res.getString(KEY_DESCRIPTION);
		
		return createBusinessObjectWithLoadedRelations(id,title,description);
	}

	public static BusinessObject createInstance(String type, String id) {
		switch (type.toLowerCase()){
		case TABLENAME_APPLICATION: return new Application(id);
		case TABLENAME_CATEGORY: return new Category(id);
		case TABLENAME_FEATURE: return new Feature(id);
		default: throw new UnsupportedOperationException("No class defined for this prefix:" + type);
		}
	}

}

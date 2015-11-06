package de.unipotsdam.cs.toolup.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;
import de.unipotsdam.cs.toolup.model.NullBusinessObject;

public class DatabaseController {
		
	private static final String COLUMN_SUFFIX_UUID = "_uuid";
	public static final String TABLE_NAME_FEATURE = "feature";
	public static final String TABLE_NAME_CATEGORY = "category";
	public static final String TABLE_NAME_APPLICATION = "application";
	public static final String TABLE_NAME_APPLICATION_FEATURE = "application_has_feature";
	public static final String TABLE_NAME_APPLICATION_CATEGORY = "application_belongs_to_category";


	public static BusinessObject load(String uuid) throws SQLException {
		String tableName = BusinessObject.getTableNameFromId(uuid);
		
		PreparedStatement prepQuery = SqlStatements.getSelectAllFrom(tableName);
		
		prepQuery.setString(1, uuid);
		ResultSet res = prepQuery.executeQuery();
		
		return BusinessObjectFactory.createBusinessObjectFromSingleResult(res);
	}


	public static Set<String> loadRelatedBusinessObjectsForId(String tableName, String targetBusinessObjectType, String id) throws SQLException {

		String criteriaBusinessObjectType = BusinessObject.getTableNameFromId(id);
		
		PreparedStatement prepQuery = SqlStatements.getSelectRelation(tableName, criteriaBusinessObjectType + COLUMN_SUFFIX_UUID);

		prepQuery.setString(1, id);
		prepQuery.toString();
		
		ResultSet res = prepQuery.executeQuery();

		return getRelatedIdsFromResultSet(targetBusinessObjectType + COLUMN_SUFFIX_UUID,
				res);
	}

	private static Set<String> getRelatedIdsFromResultSet(
			String resultColumnName, ResultSet res) throws SQLException {
		Set<String> relatedIds = new HashSet<String>();
		while (res.next()) {
			relatedIds.add(res.getString(resultColumnName));
		}
		return relatedIds;
	}

	public static Set<String> loadRelatedCategoriesForApp(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_CATEGORY, TABLE_NAME_CATEGORY, id);
	}

	public static Set<String> loadRelatedApplicationsForCat(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_CATEGORY, TABLE_NAME_APPLICATION, id);
	}

	public static Set<String> loadRelatedFeaturesForApp(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_FEATURE, TABLE_NAME_FEATURE, id);
	}

	public static Collection<String> loadRelatedApplicationsForFeat(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_FEATURE, TABLE_NAME_APPLICATION, id);
	}


	public static boolean checkIfExistsInDB(BusinessObject aBusinessObject) throws SQLException {
		return checkIfExistsInDB(aBusinessObject.getUuid());
	}

	public static boolean checkIfExistsInDB(String id) throws SQLException {
		BusinessObject objectFromDB = load(id);
		return !(objectFromDB instanceof NullBusinessObject);
	}


	public static void storeToDatabase(BusinessObject aBusinessObject) throws SQLException {
		boolean exists = checkIfExistsInDB(aBusinessObject);
		if (exists) {
			updateDatabase(aBusinessObject);
		} else {
			insertIntoDatabase(aBusinessObject);
		}		
	}


	private static void insertIntoDatabase(BusinessObject aBusinessObject)
			throws SQLException {
		String tableName = BusinessObject.getTableNameFromId(aBusinessObject.getUuid());
		insertBO(aBusinessObject, tableName);
		insertRelations(aBusinessObject, tableName);
	}


	private static void insertRelations(BusinessObject aBusinessObject,
			String tableName) throws SQLException {
		if (aBusinessObject.getRelatedBOs().isEmpty()) return;
		switch (tableName) {
		case TABLE_NAME_APPLICATION: {
			Application app = (Application) aBusinessObject;
			insertSingleRelationInto(TABLE_NAME_APPLICATION_FEATURE, app.getUuid(), 1, app.getRelatedFeatures());
			insertSingleRelationInto(TABLE_NAME_APPLICATION_CATEGORY, app.getUuid(), 1, app.getRelatedCategories());
			break;
		}
		case TABLE_NAME_CATEGORY: {
			Category cat = (Category) aBusinessObject;
			insertSingleRelationInto(TABLE_NAME_APPLICATION_CATEGORY, cat.getUuid(), 2, cat.getRelatedApplications());
			break;
		}
		case TABLE_NAME_FEATURE: {
			Feature feat = (Feature) aBusinessObject;
			insertSingleRelationInto(TABLE_NAME_APPLICATION_FEATURE, feat.getUuid(), 2, feat.getRelatedApplications());
			break;
		}
		default: throw new NotImplementedException();
		}
	}


	/**
	 * 
	 * @param tableName the name of the relation table
	 * @param uuid the BO thats relations are to be stored
	 * @param uuidColumnNumber column number of the BOs id in the table. 1 if BO is Application, 2 otherwise.
	 * @param relatedIds a collection of the uuid's of related BOs
	 * @throws SQLException
	 */
	private static void insertSingleRelationInto(String tableName, String uuid,
			int uuidColumnNumber, Collection<String> relatedIds) throws SQLException {
		for (String foreignKey: relatedIds) {
			PreparedStatement prepQuery;
			prepQuery = SqlStatements.getInsertRelation(tableName);	
			prepQuery.setString(uuidColumnNumber, uuid);
			prepQuery.setString((uuidColumnNumber % 2) +1, foreignKey);
			prepQuery.executeUpdate();
		}	
	}


	private static void insertBO(BusinessObject aBusinessObject,
			String tableName) throws SQLException {
		PreparedStatement prepQuery;
		prepQuery = SqlStatements.getInsertInto(tableName);	
		prepQuery.setString(1, aBusinessObject.getUuid());
		prepQuery.setString(2, aBusinessObject.getTitle());
		prepQuery.setString(3, aBusinessObject.getDescription());
		prepQuery.executeUpdate();
	}


	private static void updateDatabase(BusinessObject aBusinessObject)
			throws SQLException {
		String tableName = BusinessObject.getTableNameFromId(aBusinessObject.getUuid());
		updateBO(aBusinessObject, tableName);
		updateRelations(aBusinessObject, tableName);
	}


	private static void updateRelations(BusinessObject aBusinessObject,
			String tableName) throws SQLException {
		if (aBusinessObject.getRelatedBOs().isEmpty()) return;
		switch (tableName) {
		case TABLE_NAME_APPLICATION: {
			Application app = (Application) aBusinessObject;
			updateSingleRelation(TABLE_NAME_APPLICATION_FEATURE, app.getUuid(), 1, app.getRelatedFeatures());
			updateSingleRelation(TABLE_NAME_APPLICATION_CATEGORY, app.getUuid(), 1, app.getRelatedCategories());
			break;
		}
		case TABLE_NAME_CATEGORY: {
			Category cat = (Category) aBusinessObject;
			updateSingleRelation(TABLE_NAME_APPLICATION_CATEGORY, cat.getUuid(), 2, cat.getRelatedApplications());
			break;
		}
		case TABLE_NAME_FEATURE: {
			Feature feat = (Feature) aBusinessObject;
			updateSingleRelation(TABLE_NAME_APPLICATION_FEATURE, feat.getUuid(), 2, feat.getRelatedApplications());
			break;
		}
		default: throw new NotImplementedException();
		}
	}


	private static void updateSingleRelation(
			String tableName, String uuid, int uuidColumnNumber,
			Collection<String> relatedIds) throws SQLException {
		for (String foreignKey: relatedIds) {
			PreparedStatement prepQuery;
			prepQuery = SqlStatements.getInsertRelation(tableName);	
			prepQuery.setString(uuidColumnNumber, uuid);
			prepQuery.setString((uuidColumnNumber % 2) +1, foreignKey);
			prepQuery.executeUpdate();
		}	
	}


	private static void updateBO(BusinessObject aBusinessObject,
			String tableName) throws SQLException {
		PreparedStatement prepQuery;
		prepQuery = SqlStatements.getUpdate(tableName);
		prepQuery.setString(1, aBusinessObject.getTitle());
		prepQuery.setString(2, aBusinessObject.getDescription());
		prepQuery.setString(3, aBusinessObject.getUuid());
		prepQuery.executeUpdate();
	}


	public static void deleteFromDatabase(String id) throws SQLException {
		String tableName = BusinessObject.getTableNameFromId(id);
		
		PreparedStatement prepQuery = SqlStatements.getDeleteFrom(tableName);
		
		prepQuery.setString(1, id);
		prepQuery.executeUpdate();
	}


}

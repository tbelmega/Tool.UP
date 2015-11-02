package de.unipotsdam.cs.toolup.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;
import de.unipotsdam.cs.toolup.model.NullBusinessObject;

public class DatabaseController {
		
	private static final String TABLE_NAME_FEATURE = "feature";
	private static final String TABLE_NAME_CATEGORY = "category";
	private static final String TABLE_NAME_APPLICATION = "application";


	public static BusinessObject load(String uuid) throws SQLException {
		String tableName = BusinessObject.getTableNameFromId(uuid);
		
		PreparedStatement prepQuery = SqlStatements.getSelectAllFrom(tableName);
		
		prepQuery.setString(1, uuid);
		ResultSet res = prepQuery.executeQuery();
		
		return BusinessObjectFactory.createBusinessObjectFromSingleResult(res);
	}


	public static Set<String> loadRelatedBusinessObjectsForId(String getObjectType, String id) throws SQLException {

		String selectBy = BusinessObject.getTableNameFromId(id);
		
		PreparedStatement prepQuery = SqlStatements.getRelationAbyB(getObjectType, selectBy);

		prepQuery.setString(1, id);
		prepQuery.toString();
		
		ResultSet res = prepQuery.executeQuery();

		return getRelatedIdsFromResultSet(getObjectType + "_uuid",
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
		return loadRelatedBusinessObjectsForId(TABLE_NAME_CATEGORY, id);
	}

	public static Set<String> loadRelatedApplicationsForCat(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION, id);
	}

	public static Set<String> loadRelatedFeaturesForApp(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId(TABLE_NAME_FEATURE, id);
	}

	public static Collection<String> loadRelatedApplicationsForFeat(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION, id);
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

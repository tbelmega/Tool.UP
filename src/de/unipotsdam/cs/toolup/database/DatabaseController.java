package de.unipotsdam.cs.toolup.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.unipotsdam.cs.toolup.model.BusinessObject;

public class DatabaseController {
		
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
		return loadRelatedBusinessObjectsForId("category", id);
	}

	public static Set<String> loadRelatedApplicationsForCat(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId("application", id);
	}

	public static Set<String> loadRelatedFeaturesForApp(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId("feature", id);
	}

	public static Collection<String> loadRelatedApplicationsForFeat(String id) throws SQLException {
		return loadRelatedBusinessObjectsForId("application", id);
	}

//	public static void storeToDatabase(Application anApplication) throws SQLException {
//		
//		
//		String query = "INSERT INTO " + getTableNameFromId(anApplication.getUuid()) + " VALUES ('"+ anApplication.getUuid() +"','"+ anApplication.getTitle() +"','"+ anApplication.getDescription()+"');";
//		executeUpdate(query);
//	}

}

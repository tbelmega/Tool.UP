package de.unipotsdam.cs.toolup.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.unipotsdam.cs.toolup.model.BusinessObject;

public class DatabaseController {
	
	private static ResultSet executeQuery(String query) throws SQLException {
		Connection connection = DriverManager.getConnection(ToolUpProperties.getDatabaseUrl());
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery(query);
		return res;
	}
	
	static String getTableNameFromId(String uuid) {
		int indexOfFirstSlash = uuid.indexOf('/');
		return uuid.substring(0, indexOfFirstSlash);
	}

	public static BusinessObject load(String uuid) throws SQLException {
		String tableName = getTableNameFromId(uuid);
		String query = "select * from " + tableName + " WHERE uuid = '" + uuid + "';";
		ResultSet res = executeQuery(query);
		res.first();
		String id = res.getString("uuid");
		String title = res.getString("title");
		String description = res.getString("description");
		
		return BusinessObjectFactory.createBusinessObject(id,title,description);
	}

	/**
	 * loads relations from a given table. selects id from key column, returns result column.
	 * @param tableName
	 * @param keyColumnName
	 * @param resultColumnName
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static Set<String> loadRelatedIds(String tableName, String keyColumnName, String resultColumnName, String id) throws SQLException {
		String query = "SELECT * FROM " + tableName + " WHERE " + keyColumnName + " = '" + id + "';";
		ResultSet res = executeQuery(query);
		Set<String> relatedIds = new HashSet<String>();
		while (res.next()) {
			relatedIds.add(res.getString(resultColumnName));
		}
		return relatedIds;
	}

	public static Set<String> loadRelatedCategoriesForApp(String id) throws SQLException {
		return loadRelatedIds("application_belongs_to_category", "application_uuid", "category_uuid", id);
	}

	public static Set<String> loadRelatedApplicationsForCat(String id) throws SQLException {
		return loadRelatedIds("application_belongs_to_category", "category_uuid", "application_uuid", id);
	}

	public static Set<String> loadRelatedFeaturesForApp(String id) throws SQLException {
		return loadRelatedIds("application_has_feature", "application_uuid", "feature_uuid", id);
	}

	public static Collection<String> loadRelatedApplicationsForFeat(String id) throws SQLException {
		return loadRelatedIds("application_has_feature", "feature_uuid", "application_uuid",  id);
	}

}

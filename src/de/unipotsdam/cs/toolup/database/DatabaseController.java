package de.unipotsdam.cs.toolup.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import de.unipotsdam.cs.toolup.model.BusinessObject;

public class DatabaseController {

	public static BusinessObject load(String uuid) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&pw=");
		Statement statement = connection.createStatement();
		String tableName = getTableNameFromId(uuid);
		String query = "select * from " + tableName + " WHERE uuid = '" + uuid + "';";
		ResultSet res = statement.executeQuery(query);
		res.first();
		String id = res.getString("uuid");
		String title = res.getString("title");
		String description = res.getString("description");
		
		return BusinessObjectFactory.createBusinessObject(id,title,description);
	}

	static String getTableNameFromId(String uuid) {
		int indexOfFirstSlash = uuid.indexOf('/');
		return uuid.substring(0, indexOfFirstSlash);
	}

	public static Set<String> loadRelation(String tableName, String keyColumnName, String resultColumnName, String id) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&pw=");
		Statement statement = connection.createStatement();
		String query = "SELECT * FROM " + tableName + " WHERE " + keyColumnName + " = '" + id + "';";
		ResultSet res = statement.executeQuery(query);
		Set<String> relatedIds = new HashSet<String>();
		while (res.next()) {
			relatedIds.add(res.getString(resultColumnName));
		}
		return relatedIds;
	}

	public static Set<String> loadRelatedCategoriesForApp(String id) throws SQLException {
		return loadRelation("application_belongs_to_category", "application_uuid", "category_uuid", id);
	}

	public static Set<String> loadRelatedApplicationsForCat(String id) throws SQLException {
		return loadRelation("application_belongs_to_category", "category_uuid", "application_uuid", id);
	}

}

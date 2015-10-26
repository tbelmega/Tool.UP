package de.unipotsdam.cs.toolup.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

}

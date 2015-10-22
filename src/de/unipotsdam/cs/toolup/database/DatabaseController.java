package de.unipotsdam.cs.toolup.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.unipotsdam.cs.toolup.model.Application;

public class DatabaseController {

	public static Application load(String uuid) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?user=root&pw=");
		Statement statement = connection.createStatement();
		ResultSet res = statement.executeQuery("select * from application;");
		res.first();
		String id = res.getString("uuid");
		String title = res.getString("title");
		String description = res.getString("description");
		
		return new Application(id,title,description);
	}

}

package de.unipotsdam.cs.toolup.database;

public class ToolUpProperties {

	private static final String DB_USERNAME = "root";
	private static final String DB_SCHEMA = "test";
	private static final String SERVER_PORT = "3306";
	private static final String SERVER_ADRESS = "localhost";
	private static final String DATABASE_CONNECTOR = "jdbc:mysql://";
	private static final String DB_PASSWORD = "";

	public static String getDatabaseUrl() {
		return DATABASE_CONNECTOR+SERVER_ADRESS+":"+SERVER_PORT+"/"+DB_SCHEMA+"?user="+DB_USERNAME+"&pw=" + DB_PASSWORD;
	}

}

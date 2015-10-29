package de.unipotsdam.cs.toolup.model;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BusinessObject {

	protected String uuid;
	protected String title;
	protected String description;

	public BusinessObject(String uuid, String title, String description) {
		this.uuid = uuid;
		this.title = title;
		this.description = description;
	}

	public String getUuid() {
		return this.uuid;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * A BusinesObject is defined to equal an other, if the uuid is identical.
	 * @param anOtherApp
	 * @return
	 */
	public boolean equals(BusinessObject anOtherApp) {
		if (this.uuid.equals(anOtherApp.getUuid())){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Extract the table name out of the uuid.
	 * The uuid is structured like this: tablename/1234567
	 * @param uuid
	 * @return an SQL table name
	 */
	public static String getTableNameFromId(String uuid) {
		int indexOfFirstSlash = uuid.indexOf('/');
		return uuid.substring(0, indexOfFirstSlash);
	}
	
	/**
	 * Extract the table name out of the uuid.
	 * The uuid is structured like this: tablename/1234567
	 * @return an SQL table name
	 */
	public String getTableName(){
		return getTableNameFromId(this.uuid);
	}
	
	/**
	 * Creates a JSONObject with the values of this business objects fields.
	 */
	public abstract JSONObject convertToJson() throws JSONException;

}

package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.Map;

import org.json.JSONArray;
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
	 * Creates a JSONObject with the values of this business objects' fields.
	 */
	public abstract JSONObject convertToJson() throws JSONException;

	/**
	 * Converts a collection of uuids into a JSONArray, which contains JSONObjects of the pattern {"id":<uuid>"}
	 * @param relation
	 * @return
	 * @throws JSONException
	 */
	protected JSONArray relationAsArray(Collection<String> relation) throws JSONException {
		JSONArray categories = new JSONArray();
		for (String s: relation){
			categories.put(new JSONObject("{\"id\":\"" + s + "\"}"));
		}
		return categories;
	}


	protected JSONObject createJSONObjectFromAttributes(Map<String, JSONArray> relations)
			throws JSONException {
				JSONObject result = createJSONObjectForBusinessObject();
				
				for (String relationName: relations.keySet()){
					result.put(relationName, relations.get(relationName));
				}				
				return result;
	}

	/**
	 * Fill a new JSONObject with the values of a BusinessObject.
	 * @return
	 * @throws JSONException
	 */
	private JSONObject createJSONObjectForBusinessObject() throws JSONException {
		JSONObject result = new JSONObject();		
		result.put("id", this.uuid);
		result.put("title", this.title);
		result.put("description", this.description);
		return result;
	}

}

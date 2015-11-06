package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BusinessObject {

	static final String KEY_ID = "id";
	static final String KEY_DESCRIPTION = "description";
	static final String KEY_TITLE = "title";
	static final String KEY_FEATURES = "features";
	static final String KEY_CATEGORIES = "categories";
	static final String KEY_APPLICATIONS = "applications";
	
	protected String uuid;
	protected String title;
	protected String description;

	public BusinessObject(String uuid, String title, String description) {
		this.uuid = uuid;
		this.title = title;
		this.description = description;
	}

	public BusinessObject(String uuid) {
		this(uuid,"","");
	}

	public String getUuid() {
		return this.uuid;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
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
		result.put(KEY_ID, this.uuid);
		result.put(KEY_TITLE, this.title);
		result.put(KEY_DESCRIPTION, this.description);
		return result;
	}

	public static BusinessObject createBusinessObjectFromJson(
			JSONObject jsonRepresentation) throws JSONException {
		String id = jsonRepresentation.getString(KEY_ID);		
		BusinessObject newlyCreatedBO = BusinessObjectFactory.createInstance(id);
		
		newlyCreatedBO.title = jsonRepresentation.getString(KEY_TITLE);
		newlyCreatedBO.description = jsonRepresentation.getString(KEY_DESCRIPTION);
		
		return newlyCreatedBO;
	}

	public abstract Set<String> getRelatedBOs();

	protected Set<String> getRelatedBOOfAllRelations(Collection<String>[] relations) {
		Set<String> relatedBOs = new HashSet<String>();
		for (int i = 0; i < relations.length; i++) {
			relatedBOs.addAll(relations[i]);
		}
		return relatedBOs;
	}

	public abstract void addRelation(String string);



}

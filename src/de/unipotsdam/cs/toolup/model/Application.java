package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Application extends BusinessObject {

	private final Collection<String> relatedCategories;
	private final Collection<String> relatedFeatures;

	public Application(String uuid, String title, String description, Set<String> relatedCategories,Set<String> relatedFeatures ) {
		super(uuid, title, description);
		this.relatedCategories = relatedCategories;
		this.relatedFeatures = relatedFeatures;
	}

	public Collection<String> getRelatedCategories() {
		return new HashSet<String>(this.relatedCategories);
	}

	public Collection<String> getRelatedFeatures() {	
		return new HashSet<String>(this.relatedFeatures);
	}

	/**
	 * Creates a JSONObject with the values of this applications fields.
	 */
	@Override
	public JSONObject convertToJson() throws JSONException {		
		JSONArray categories = relationAsArray(relatedCategories);	
		JSONArray features = relationAsArray(relatedFeatures);	
		JSONObject result = createJSONObjectFromAttributes(categories, features);		
		return result;		
	}

	private JSONObject createJSONObjectFromAttributes(JSONArray categories,
			JSONArray features) throws JSONException {
		JSONObject result = new JSONObject();
		
		result.put("id", this.uuid);
		result.put("title", this.title);
		result.put("description", this.description);
		result.put("categories", categories);
		result.put("features", features);
		return result;
	}

	private JSONArray relationAsArray(Collection<String> relation) throws JSONException {
		JSONArray categories = new JSONArray();
		for (String s: relation){
			categories.put(new JSONObject("{\"id\":\"" + s + "\"}"));
		}
		return categories;
	}

}

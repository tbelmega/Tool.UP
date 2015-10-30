package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

	public Application(String uuid) {
		super(uuid);
		this.relatedCategories = new HashSet<String>();
		this.relatedFeatures = new HashSet<String>();
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
		Map<String, JSONArray> relations = new HashMap<String, JSONArray>();
		relations.put(KEY_CATEGORIES, relationAsArray(relatedCategories));
		relations.put(KEY_FEATURES, relationAsArray(relatedFeatures));
	
		JSONObject result = createJSONObjectFromAttributes(relations);		
		return result;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getRelatedBOs() {
		return getRelatedBOOfAllRelations(new Collection[] {relatedCategories, relatedFeatures});
	}



}

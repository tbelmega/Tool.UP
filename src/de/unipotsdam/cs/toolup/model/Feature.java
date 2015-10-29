package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Feature extends BusinessObject {

	private final Collection<String> relatedApplications;

	public Feature(String uuid, String title, String description, Collection<String> relatedApplications ) {
		super(uuid, title, description);
		this.relatedApplications = relatedApplications;
	}

	public Collection<String> getRelatedApplications() {
		
		return new HashSet<String>(this.relatedApplications);
	}


	@Override
	public JSONObject convertToJson() throws JSONException {	
		Map<String, JSONArray> relations = new HashMap<String, JSONArray>();
		relations.put("applications", relationAsArray(relatedApplications));

		JSONObject result = createJSONObjectFromAttributes(relations);		
		return result;		
	}

}

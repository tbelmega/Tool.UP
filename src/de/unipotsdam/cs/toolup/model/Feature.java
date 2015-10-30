package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Feature extends BusinessObject {

	private final Collection<String> relatedApplications;

	public Feature(String uuid, String title, String description, Collection<String> relatedApplications ) {
		super(uuid, title, description);
		this.relatedApplications = relatedApplications;
	}

	public Feature(String uuid) {
		super(uuid);
		this.relatedApplications = new HashSet<String>();
	}

	public Collection<String> getRelatedApplications() {
		
		return new HashSet<String>(this.relatedApplications);
	}


	@Override
	public JSONObject convertToJson() throws JSONException {	
		Map<String, JSONArray> relations = new HashMap<String, JSONArray>();
		relations.put(KEY_APPLICATIONS, relationAsArray(relatedApplications));

		JSONObject result = createJSONObjectFromAttributes(relations);		
		return result;		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getRelatedBOs() {
		return getRelatedBOOfAllRelations(new Collection[] { relatedApplications } );
	}

}

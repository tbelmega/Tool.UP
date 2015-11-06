package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Category extends BusinessObject {

	private final Collection<String> relatedApplications;
	
	public Category(String uuid, String title, String description, Set<String> relatedApplications) {
		super(uuid, title, description);
		this.relatedApplications = relatedApplications;
	}

	public Category(String uuid) {
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

	@Override
	public void addRelation(String string) {
		relatedApplications.add(string);
	}


}

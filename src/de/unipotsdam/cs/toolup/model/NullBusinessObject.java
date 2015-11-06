package de.unipotsdam.cs.toolup.model;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * NullBusinessObject represents the absence of a {@link BusinessObject} according to the NullObjectPattern.
 * @author Thiemo
 *
 */
public class NullBusinessObject extends BusinessObject {
	
	private static NullBusinessObject instance = null;


	private NullBusinessObject(){
		super("","Null Object","This object represents null.");
	}

	@Override
	public JSONObject convertToJson() throws JSONException {
		return new JSONObject();
	}

	@Override
	public Set<String> getRelatedBOs() {
		return new HashSet<String>();
	}

	/**
	 * NullBusinessObject represents the absence of a {@link BusinessObject}.
	 * @return The singleton instance of {@link NullBusinessObject}
	 */
	public static BusinessObject getInstance() {
		//Singleton implementation without synchronization, does not matter
		if (instance == null){
			instance = new NullBusinessObject();
		}
		return instance;
	}

	@Override
	public void addRelation(String string) {
	}

}

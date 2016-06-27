package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_APPLICATION;


public class Feature extends BusinessObject {

    public Feature(String uuid, String title, String description, Collection<String> relatedApplications) {
        super(uuid, title, description);
        this.relations.put(TABLE_NAME_APPLICATION, relatedApplications);
    }

    public Feature(String uuid) {
        this(uuid, "", "", new HashSet<String>());
    }

    @Override
    protected void buildSubClassSpecificAttributes(JSONObject jsonRepresentation) throws InvalidIdException {
        addRelationFromJson(jsonRepresentation, JSON_KEY_APPLICATIONS);
    }

    @Override
    protected void addSubclassAttributes(JSONObject result) {
        //nothing to do
    }

    public Collection<String> getRelatedApplications() {
        return new HashSet<>(this.relations.get(TABLE_NAME_APPLICATION));
    }
}

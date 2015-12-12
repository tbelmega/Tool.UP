package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_CATEGORY;
import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_FEATURE;

public class Application extends BusinessObject {

    public Application(String uuid, String title, String description, Set<String> relatedCategories, Set<String> relatedFeatures) {
        super(uuid, title, description);
        this.relations.put(TABLE_NAME_CATEGORY, relatedCategories);
        this.relations.put(TABLE_NAME_FEATURE, relatedFeatures);
    }

    public Application(String uuid) {
        this(uuid, "", "", new HashSet<String>(), new HashSet<String>());
    }

    @Override
    protected void buildSubClassSpecificAttributes(JSONObject jsonRepresentation) throws InvalidIdException {
        addRelationFromJson(jsonRepresentation, JSON_KEY_FEATURES);
        addRelationFromJson(jsonRepresentation, JSON_KEY_CATEGORIES);
    }

    public Collection<String> getRelatedCategories() {
        return new HashSet<>(this.relations.get(TABLE_NAME_CATEGORY));
    }

    public Collection<String> getRelatedFeatures() {
        return new HashSet<>(this.relations.get(TABLE_NAME_FEATURE));
    }

}

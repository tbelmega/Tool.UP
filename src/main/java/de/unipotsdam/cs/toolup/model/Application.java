package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_CATEGORY;
import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_FEATURE;

public class Application extends BusinessObject {

    private String shortDescription;
    private String contact;
    private String provider;


    public Application(String uuid, String title, String description, Set<String> relatedCategories, Set<String> relatedFeatures) {
        this(uuid, title, description, "", "", "", relatedCategories, relatedFeatures);
    }

    public Application(String uuid, String title, String description, String shortDescription, String provider,
                       String contact, Set<String> relatedCategories, Set<String> relatedFeatures) {
        super(uuid, title, description);
        this.shortDescription = shortDescription;
        this.contact = contact;
        this.provider = provider;
        this.relations.put(TABLE_NAME_CATEGORY, relatedCategories);
        this.relations.put(TABLE_NAME_FEATURE, relatedFeatures);
    }

    public Application(String uuid) {
        this(uuid, "", "", "", "", "", new HashSet<String>(), new HashSet<String>());
    }

    @Override
    protected void buildSubClassSpecificAttributes(JSONObject jsonRepresentation) throws InvalidIdException {
        addRelationFromJson(jsonRepresentation, JSON_KEY_FEATURES);
        addRelationFromJson(jsonRepresentation, JSON_KEY_CATEGORIES);
        this.setShortDescription(jsonRepresentation.getString(JSON_KEY_SHORTDESC));
        this.setProvider(jsonRepresentation.getString(JSON_KEY_PROVIDER));
        this.setContact(jsonRepresentation.getString(JSON_KEY_CONTACT));
    }

    public Collection<String> getRelatedCategories() {
        return new HashSet<>(this.relations.get(TABLE_NAME_CATEGORY));
    }

    public Collection<String> getRelatedFeatures() {
        return new HashSet<>(this.relations.get(TABLE_NAME_FEATURE));
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public boolean equalsInAllProperties(BusinessObject anOtherBO) {
        if (!(anOtherBO instanceof Application)) return false;
        Application otherApp = (Application) anOtherBO;

        return super.equalsInAllProperties(anOtherBO)
                && this.shortDescription.equals(otherApp.getShortDescription())
                && this.provider.equals(otherApp.getProvider())
                && this.contact.equals(otherApp.getContact());
    }
}

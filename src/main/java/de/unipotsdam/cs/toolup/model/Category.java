package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_APPLICATION;

public class Category extends BusinessObject {

    private static final String JSON_KEY_SUPERCATEGORY = "supercategory";
    private static final String JSON_KEY_SUBCATEGORIES = "subcategories";
    private String superCategory = "";
    private Collection<String> subCategories = new HashSet<>();

    public Category(String uuid, String title, String description, Set<String> relatedApplications) {
        super(uuid, title, description);
        this.relations.put(TABLE_NAME_APPLICATION, relatedApplications);
    }

    public Category(String uuid) {
        this(uuid, "", "", new HashSet<String>());
    }

    @Override
    protected void buildSubClassSpecificAttributes(JSONObject jsonRepresentation) throws InvalidIdException {
        addRelationFromJson(jsonRepresentation, JSON_KEY_APPLICATIONS);

        this.superCategory = jsonRepresentation.getString(JSON_KEY_SUPERCATEGORY);

        JSONArray subcategories = jsonRepresentation.optJSONArray(JSON_KEY_SUBCATEGORIES);
        if (subcategories == null) return;

        for (int i = 0; i < subcategories.length(); i++) {
            JSONObject subCat = subcategories.getJSONObject(i);
            this.subCategories.add(subCat.getString(JSON_KEY_ID));
        }
    }

    public Collection<String> getRelatedApplications() {
        return new HashSet<>(this.relations.get(TABLE_NAME_APPLICATION));
    }

    public String getSuperCategory() {
        return superCategory;
    }

    public void setSuperCategory(String superCategory) {
        this.superCategory = superCategory;
    }

    public Collection<String> getSubCategories() {
        return new HashSet<>(subCategories);
    }

    public void addSubCategories(Collection<String> subcategories) {
        this.subCategories.addAll(subcategories);
    }

    @Override
    public boolean equalsInAllProperties(BusinessObject anOtherBO) {
        if (!(anOtherBO instanceof Category)) return false;

        Category anotherCat = (Category) anOtherBO;

        if (!this.getSuperCategory().equals(anotherCat.getSuperCategory())) return false;
        if (!this.getSubCategories().equals(anotherCat.getSubCategories())) return false;

        return super.equalsInAllProperties(anOtherBO);
    }

    @Override
    protected void addSubclassAttributes(JSONObject result) {
        result.put(JSON_KEY_SUPERCATEGORY, this.superCategory);

        JSONArray subcategories = new JSONArray();
        for (String catId : this.subCategories) {
            subcategories.put(catId);
        }

        result.put(JSON_KEY_SUBCATEGORIES, subcategories);
    }
}

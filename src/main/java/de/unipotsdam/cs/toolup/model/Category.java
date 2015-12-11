package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_APPLICATION;

public class Category extends BusinessObject {

    private String superCategory = "";
    private Collection<String> subCategories = new HashSet<>();

    public Category(String uuid, String title, String description, Set<String> relatedApplications) {
        super(uuid, title, description);
        this.relations.put(TABLE_NAME_APPLICATION, relatedApplications);
    }

    public Category(String uuid) {
        this(uuid, "", "", new HashSet<String>());
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
}

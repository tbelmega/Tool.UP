package de.unipotsdam.cs.toolup.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_CATEGORY;
import static de.unipotsdam.cs.toolup.database.DatabaseController.TABLE_NAME_FEATURE;

@XmlRootElement
public class Application extends BusinessObject {

    public Application(String uuid, String title, String description, Set<String> relatedCategories, Set<String> relatedFeatures) {
        super(uuid, title, description);
        this.relations.put(TABLE_NAME_CATEGORY, relatedCategories);
        this.relations.put(TABLE_NAME_FEATURE, relatedFeatures);
    }

    public Application(String uuid) {
        this(uuid, "", "", new HashSet<String>(), new HashSet<String>());
    }

    public Collection<String> getRelatedCategories() {
        return new HashSet<>(this.relations.get(TABLE_NAME_CATEGORY));
    }

    public Collection<String> getRelatedFeatures() {
        return new HashSet<>(this.relations.get(TABLE_NAME_FEATURE));
    }

}

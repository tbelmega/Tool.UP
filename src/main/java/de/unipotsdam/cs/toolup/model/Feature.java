package de.unipotsdam.cs.toolup.model;

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

    public Collection<String> getRelatedApplications() {
        return new HashSet<>(this.relations.get(TABLE_NAME_APPLICATION));
    }
}

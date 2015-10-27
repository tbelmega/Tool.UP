package de.unipotsdam.cs.toolup.database;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.unipotsdam.cs.toolup.model.BusinessObject;

public class Category extends BusinessObject {

	private final Collection<String> relatedApplications;

	public Category(String uuid, String title, String description, Set<String> relatedApplications) {
		super(uuid, title, description);
		this.relatedApplications = relatedApplications;
	}

	public Collection<String> getRelatedApplications() {
		return new HashSet<String>(this.relatedApplications);
	}

}

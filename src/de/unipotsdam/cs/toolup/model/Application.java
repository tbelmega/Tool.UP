package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Application extends BusinessObject {

	private final Collection<String> relatedCategories;

	public Application(String uuid, String title, String description, Set<String> relatedCategories) {
		super(uuid, title, description);
		this.relatedCategories = relatedCategories;
	}

	public Collection<String> getRelatedCategories() {
		return new HashSet<String>(this.relatedCategories);
	}
}

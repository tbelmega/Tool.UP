package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Application extends BusinessObject {

	private final Collection<String> relatedCategories;
	private final Collection<String> relatedFeatures;

	public Application(String uuid, String title, String description, Set<String> relatedCategories,Set<String> relatedFeatures ) {
		super(uuid, title, description);
		this.relatedCategories = relatedCategories;
		this.relatedFeatures = relatedFeatures;
	}

	public Collection<String> getRelatedCategories() {
		return new HashSet<String>(this.relatedCategories);
	}

	public Collection<String> getRelatedFeatures() {
		
		return new HashSet<String>(this.relatedFeatures);
	}
}

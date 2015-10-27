package de.unipotsdam.cs.toolup.model;

import java.util.Collection;
import java.util.HashSet;


public class Feature extends BusinessObject {

	private final Collection<String> relatedApplications;

	public Feature(String uuid, String title, String description, Collection<String> relatedApplications ) {
		super(uuid, title, description);
		this.relatedApplications = relatedApplications;
	}

	public Collection<String> getRelatedApplications() {
		
		return new HashSet<String>(this.relatedApplications);
	}

}

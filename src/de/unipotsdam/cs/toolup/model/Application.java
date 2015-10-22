package de.unipotsdam.cs.toolup.model;

public class Application {

	private String uuid;
	private String title;
	private String description;

	public Application(String uuid, String title, String description) {
		this.uuid = uuid;
		this.title = title;
		this.description = description;
	}
	
	private Object getUuid() {
		return this.uuid;
	}
	
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return this.description;
	}

	public boolean equals(Application anOtherApp){
		if (this.uuid.equals(anOtherApp.getUuid())){
			return true;
		} else {
			return false;
		}
	}
}

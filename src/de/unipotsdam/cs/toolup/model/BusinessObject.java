package de.unipotsdam.cs.toolup.model;

public class BusinessObject {

	protected String uuid;
	protected String title;
	protected String description;

	public BusinessObject(String uuid, String title, String description) {
		this.uuid = uuid;
		this.title = title;
		this.description = description;
	}

	protected Object getUuid() {
		return this.uuid;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return this.description;
	}

	public boolean equals(BusinessObject anOtherApp) {
		if (this.uuid.equals(anOtherApp.getUuid())){
			return true;
		} else {
			return false;
		}
	}

}

package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.model.BusinessObject;

public class BusinessObjectBean {

    private String id;
    private String title;
    private String description;

    public BusinessObjectBean(BusinessObject bo) {
        this.id = bo.getUuid();
        this.title = bo.getTitle();
        this.description = bo.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

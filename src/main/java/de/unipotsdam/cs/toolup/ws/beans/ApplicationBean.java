package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.Application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


public class ApplicationBean extends BusinessObjectBean {

    private Collection<String> categories;
    private Collection<String> features;

    public ApplicationBean(Application app) throws InvalidIdException, SQLException, IOException {
        super(app);

        this.categories = app.getRelatedCategories();
        this.features = app.getRelatedFeatures();
    }

    public Collection<String> getCategories() {
        return categories;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public Collection<String> getFeatures() {
        return features;
    }

    public void setFeatures(Collection<String> features) {
        this.features = features;
    }


    public static ApplicationBean getBean(String id) throws InvalidIdException, SQLException, IOException {
        Application app = (Application)DatabaseController.getInstance().load(id);
        return new ApplicationBean(app);

    }
}

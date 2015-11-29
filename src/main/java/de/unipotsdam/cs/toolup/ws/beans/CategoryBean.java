package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.Category;
import de.unipotsdam.cs.toolup.model.Feature;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class CategoryBean extends BusinessObjectBean{

    private Collection<String> applications;

    public CategoryBean(Category cat) {
        super(cat);
        this.applications = cat.getRelatedApplications();
    }


    public static CategoryBean getBean(String id) {
        try {
            Category cat = (Category) DatabaseController.getInstance().load(id);
            return new CategoryBean(cat);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}




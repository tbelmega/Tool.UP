package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.Category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class CategoryBean extends BusinessObjectBean{

    private Collection<String> applications;

    public CategoryBean(Category cat) {
        super(cat);
        this.applications = cat.getRelatedApplications();
    }


    public static CategoryBean getBean(String id) throws IOException, SQLException, InvalidIdException {
        Category cat = (Category) DatabaseController.getInstance().load(id);
        return new CategoryBean(cat);

    }

    public static Collection<CategoryBean> getAllCategories() throws IOException, SQLException {
        Collection<CategoryBean> result = new HashSet<>();
        Map<String, BusinessObject> allCats = DatabaseController.getInstance().loadAllFrom(DatabaseController.TABLE_NAME_CATEGORY);

        for (BusinessObject cat : allCats.values()) {
            result.add(new CategoryBean((Category) cat));
        }
        return result;
    }
}




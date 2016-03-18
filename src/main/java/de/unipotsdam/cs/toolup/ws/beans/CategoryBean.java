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
    private String superCategory;
    private Collection<String> subCategories;

    public CategoryBean(Category cat) throws InvalidIdException, SQLException, IOException {
        super(cat);
        this.applications = getJSONRepresentations(cat.getRelatedApplications());
        this.superCategory = getJSONRepresentation(cat.getSuperCategory());
        this.subCategories = getJSONRepresentations(cat.getSubCategories());
    }

    public Collection<String> getApplications(){
        return applications;
    }

    public String getSuperCategory() {
        return superCategory;
    }

    public Collection<String> getSubCategories() {
        return subCategories;
    }

    public static CategoryBean getBean(String id) throws IOException, SQLException, InvalidIdException {
        Category cat = (Category) DatabaseController.getInstance().load(id);
        return new CategoryBean(cat);

    }

    public static Collection<CategoryBean> getAllCategories() throws IOException, SQLException, InvalidIdException {
        Collection<CategoryBean> result = new HashSet<>();
        Map<String, BusinessObject> allCats = DatabaseController.getInstance().loadAllFrom(DatabaseController.TABLE_NAME_CATEGORY);

        for (BusinessObject cat : allCats.values()) {
            result.add(new CategoryBean((Category) cat));
        }
        return result;
    }

    public static Collection<CategoryBean> getAllCategoriesWithApplication() throws IOException, SQLException, InvalidIdException {
        Collection<CategoryBean> result = new HashSet<>();
        Map<String, BusinessObject> allCats = DatabaseController.getInstance().loadAllCategoriesWithApplication();

        for (BusinessObject cat : allCats.values()) {
            result.add(new CategoryBean((Category) cat));
        }
        return result;
    }

    public static Collection<CategoryBean> getTopLevelCategories() throws IOException, SQLException, InvalidIdException {
        Collection<CategoryBean> result = new HashSet<>();
        Map<String, BusinessObject> allCats = DatabaseController.getInstance().loadTopLevelCategories();

        for (BusinessObject cat : allCats.values()) {
            result.add(new CategoryBean((Category) cat));
        }
        return result;
    }
}




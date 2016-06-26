package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.unipotsdam.cs.toolup.model.BusinessObject.*;

public class BusinessObjectFactory {

    private static final String KEY_UUID = "uuid";
    private static final String KEY_SUPERCATEGORY = "supercategory";
    private static final String TABLENAME_FEATURE = "feature";
    private static final String TABLENAME_CATEGORY = "category";
    private static final String TABLENAME_APPLICATION = "application";

    private static DatabaseController db;

    static {
        try {
            db = DatabaseController.getInstance();
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Could not create DatabaseController.", e);
        }
    }



    /**
     * @param res the result of a database query
     * @return NullBusinessObject if result set set is empty,
     * otherwise an instance of the matching subclass of BusinessObject with the loaded data.
     * @throws SQLException
     * @throws InvalidIdException
     */
    public static BusinessObject createBusinessObjectFromSingleResult(
            ResultSet res) throws SQLException, InvalidIdException {
        if (!res.first()) {
            return NullBusinessObject.getInstance();
        }
        return createBusinessObjectFromResult(res);
    }

    /**
     * @param res the result of a database query
     * @return all the loaded BusinessObjects in a Map from ID to BusinessObject
     * @throws SQLException
     */
    public static Map<String, BusinessObject> createSetOfBusinessObjectsFromAllResults(ResultSet res) throws SQLException {
        Map<String, BusinessObject> loadedBOs = new HashMap<>();

        while (res.next()) {
            BusinessObject bo = createBusinessObjectFromResult(res);
            loadedBOs.put(bo.getUuid(), bo);
        }
        return loadedBOs;
    }

    private static BusinessObject createBusinessObjectFromResult(ResultSet res) throws SQLException {
        try {
            return createBusinessObjectWithLoadedRelations(res);
        } catch (InvalidIdException e) {
            throw new RuntimeException("This should never happen. ID is loaded from DB and cannot be invalid", e);
        }
    }

    private static BusinessObject createBusinessObjectWithLoadedRelations(ResultSet res) throws SQLException, InvalidIdException {
        BusinessObject loadedBO;
        String id = res.getString(KEY_UUID);
        int indexOfFirstSlash = id.indexOf(BusinessObject.ID_DELIMITER_CHAR);
        String className = id.substring(0, indexOfFirstSlash);

        switch (className.toLowerCase()) {
            case TABLENAME_APPLICATION:
                Application loadedApplication = new Application(id, null, null, db.loadRelatedCategoriesForApp(id), db.loadRelatedFeaturesForApp(id));
                loadedApplication.setShortDescription(res.getString(JSON_KEY_SHORTDESC));
                loadedApplication.setContact(res.getString(JSON_KEY_CONTACT));
                loadedApplication.setProvider(res.getString(JSON_KEY_PROVIDER));
                loadedBO = loadedApplication;
                break;
            case TABLENAME_CATEGORY:
                loadedBO = new Category(id, null, null, db.loadRelatedApplicationsForCat(id));
                ((Category) loadedBO).setSuperCategory(res.getString(KEY_SUPERCATEGORY));
                ((Category) loadedBO).addSubCategories(db.loadSubCategories(id));
                break;
            case TABLENAME_FEATURE:
                loadedBO = new Feature(id, null, null, db.loadRelatedApplicationsForFeature(id));
                break;
            default:
                throw new UnsupportedOperationException("No class defined for this prefix:" + className);
        }

        loadedBO.setTitle(res.getString(JSON_KEY_TITLE));
        loadedBO.setDescription(res.getString(JSON_KEY_DESCRIPTION));
        return loadedBO;
    }


    public static BusinessObject createInstance(String id) throws InvalidIdException {
        String type = getTableNameFromId(id);

        switch (type.toLowerCase()) {
            case TABLENAME_APPLICATION:
                return new Application(id);
            case TABLENAME_CATEGORY:
                return new Category(id);
            case TABLENAME_FEATURE:
                return new Feature(id);
            default:
                throw new UnsupportedOperationException("No class defined for this prefix:" + type);
        }
    }

    public static BusinessObject createInstance(String id, String title,
                                                String description) throws InvalidIdException {
        BusinessObject bo = createInstance(id);
        bo.setTitle(title);
        bo.setDescription(description);
        return bo;
    }

    public static BusinessObject createInstanceWithNewUuid(String tablename) throws InvalidIdException {
        return createInstance(tablename + BusinessObject.ID_DELIMITER + UUID.randomUUID());
    }


}

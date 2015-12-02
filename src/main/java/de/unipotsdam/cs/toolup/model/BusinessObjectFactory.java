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

    private static BusinessObject createBusinessObjectWithLoadedRelations(String id, String title,
                                                                          String description) throws SQLException, InvalidIdException {
        int indexOfFirstSlash = id.indexOf(BusinessObject.ID_DELIMITER_CHAR);
        String className = id.substring(0, indexOfFirstSlash);

        switch (className.toLowerCase()) {
            case TABLENAME_APPLICATION:
                return new Application(id, title, description, db.loadRelatedCategoriesForApp(id), db.loadRelatedFeaturesForApp(id));
            case TABLENAME_CATEGORY:
                return new Category(id, title, description, db.loadRelatedApplicationsForCat(id));
            case TABLENAME_FEATURE:
                return new Feature(id, title, description, db.loadRelatedApplicationsForFeat(id));
            default:
                throw new UnsupportedOperationException("No class defined for this prefix:" + className);
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
        String id = res.getString(KEY_UUID);
        String title = res.getString(JSON_KEY_TITLE);
        String description = res.getString(JSON_KEY_DESCRIPTION);

        try {
            return createBusinessObjectWithLoadedRelations(id, title, description);
        } catch (Exception e) {
            throw new RuntimeException("This should never happen. ID is loaded from DB and cannot be invalid", e);
        }
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

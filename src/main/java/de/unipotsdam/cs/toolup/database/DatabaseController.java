package de.unipotsdam.cs.toolup.database;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DatabaseController {

    public static final String TABLE_NAME_FEATURE = "feature";
    public static final String TABLE_NAME_CATEGORY = "category";
    public static final String TABLE_NAME_APPLICATION = "application";
    public static final String TABLE_NAME_APPLICATION_FEATURE = "application_has_feature";
    public static final String TABLE_NAME_APPLICATION_CATEGORY = "application_belongs_to_category";
    public static final String TOOL_UP_CACHE = "toolUpCache";
    private static final String COLUMN_NAME_UUID = "uuid";
    private static final String COLUMN_SUFFIX_UUID = "_" + COLUMN_NAME_UUID;
    private static DatabaseController instance;
    private SqlStatementFactory sqlStatementFactory;
    private CacheManager cacheManager;
    private Cache cache;

    protected DatabaseController() throws IOException, SQLException {
        sqlStatementFactory = new SqlStatementFactory();

        initCache();
    }

    public static DatabaseController getInstance() throws IOException, SQLException {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    private synchronized void initCache() {
        cacheManager = CacheManager.newInstance();
        if (!cacheManager.cacheExists(TOOL_UP_CACHE)) {
            cacheManager.addCacheIfAbsent(TOOL_UP_CACHE);
        }
        cache = cacheManager.getCache(TOOL_UP_CACHE);
    }

    /**
     * Loads all BusinessObjects from a single table.
     * @param tableName name of the table to load from.
     * @return all the loaded BusinessObjects in a map from uuid to BusinessObject
     * @throws SQLException
     */
    public Map<String,BusinessObject> loadAllFrom(String tableName) throws SQLException {
        PreparedStatement prepQuery = sqlStatementFactory.getStatementSelectAllFrom(tableName);

        ResultSet res = prepQuery.executeQuery();

        return BusinessObjectFactory.createSetOfBusinessObjectsFromAllResults(res);
    }

    /**
     * Retrieves a single business object with the given uuid,
     * either from cache or from the db.
     * @param uuid the uuid of the object to load.
     * @return the loaded BusinessObject, or the NullBusinessObject if it doesn't exist
     * @throws SQLException
     * @throws InvalidIdException
     */
    public BusinessObject load(String uuid) throws SQLException, InvalidIdException {
        Element cachedElement = cache.get(uuid);
        if (cachedElement != null) {
            System.out.println("Use BO from cache: " + uuid);
            Object cachedObject = cachedElement.getObjectValue();
            return (BusinessObject) cachedObject;
        } else {
            return loadBOFromDatabase(uuid);
        }
    }

    /**
     * Loads a single business object with the given uuid from the database.
     * The table to load from is evaluated from the id.
     */
    private BusinessObject loadBOFromDatabase(String uuid) throws InvalidIdException, SQLException {
        String tableName = BusinessObject.getTableNameFromId(uuid);

        PreparedStatement prepQuery = sqlStatementFactory.getStatementSelectByUuidFrom(tableName);
        prepQuery.setString(1, uuid);
        ResultSet res = prepQuery.executeQuery();

        BusinessObject businessObjectFromSingleResult = BusinessObjectFactory.createBusinessObjectFromSingleResult(res);

        putBOIntoCache(businessObjectFromSingleResult);
        return businessObjectFromSingleResult;
    }

    private void putBOIntoCache(BusinessObject businessObjectFromSingleResult) {
        Element element = new Element(businessObjectFromSingleResult.getUuid(), businessObjectFromSingleResult);
        cache.put(element);
    }


    private Set<String> loadRelatedBusinessObjectsForId(String tableName, String targetBusinessObjectType, String id) throws SQLException, InvalidIdException {

        String criteriaBusinessObjectType = BusinessObject.getTableNameFromId(id);

        PreparedStatement prepQuery = sqlStatementFactory.getSelectRelation(tableName, criteriaBusinessObjectType + COLUMN_SUFFIX_UUID);

        prepQuery.setString(1, id);

        ResultSet res = prepQuery.executeQuery();

        return getAllIdsFromResultSet(targetBusinessObjectType + COLUMN_SUFFIX_UUID,
                res);
    }

    private Set<String> getAllIdsFromResultSet(
            String resultColumnName, ResultSet res) throws SQLException {
        Set<String> relatedIds = new HashSet<>();
        while (res.next()) {
            relatedIds.add(res.getString(resultColumnName));
        }
        return relatedIds;
    }

    public Set<String> loadRelatedCategoriesForApp(String id) throws SQLException, InvalidIdException {
        return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_CATEGORY, TABLE_NAME_CATEGORY, id);
    }

    public Set<String> loadRelatedApplicationsForCat(String id) throws SQLException, InvalidIdException {
        return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_CATEGORY, TABLE_NAME_APPLICATION, id);
    }

    public Set<String> loadRelatedFeaturesForApp(String id) throws SQLException, InvalidIdException {
        return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_FEATURE, TABLE_NAME_FEATURE, id);
    }

    public Set<String> loadRelatedApplicationsForFeature(String id) throws SQLException, InvalidIdException {
        return loadRelatedBusinessObjectsForId(TABLE_NAME_APPLICATION_FEATURE, TABLE_NAME_APPLICATION, id);
    }


    public Collection<String> loadSubCategories(String uuid) throws SQLException {
        PreparedStatement prepQuery = sqlStatementFactory.getSelectSubcategories();

        prepQuery.setString(1, uuid);
        ResultSet res = prepQuery.executeQuery();

        return getAllIdsFromResultSet(COLUMN_NAME_UUID, res);
    }


    public Map<String,BusinessObject> loadAllCategoriesWithApplication() throws SQLException {
        PreparedStatement prepQuery = sqlStatementFactory.getSelectAllCategoriesWithApplication();

        ResultSet res = prepQuery.executeQuery();

        return BusinessObjectFactory.createSetOfBusinessObjectsFromAllResults(res);
    }


    public Map<String, BusinessObject> loadTopLevelCategories() throws SQLException {
        PreparedStatement prepQuery = sqlStatementFactory.getSelectTopLevelCategories();

        ResultSet res = prepQuery.executeQuery();

        return BusinessObjectFactory.createSetOfBusinessObjectsFromAllResults(res);
    }

    public Map<String, BusinessObject> doFullTextSearch(String searchString) throws SQLException {
        PreparedStatement prepQuery = sqlStatementFactory.getFullTextSearchStatement(searchString);

        prepQuery.setString(1, searchString);
        prepQuery.setString(2, searchString);

        ResultSet res = prepQuery.executeQuery();

        return BusinessObjectFactory.createSetOfBusinessObjectsFromAllResults(res);
    }


    public boolean checkIfExistsInDB(BusinessObject aBusinessObject) throws SQLException, InvalidIdException {
        return checkIfExistsInDB(aBusinessObject.getUuid());
    }

    public boolean checkIfExistsInDB(String id) throws SQLException, InvalidIdException {
        BusinessObject objectFromDB = load(id);
        return !(objectFromDB instanceof NullBusinessObject);
    }


    public void storeToDatabase(BusinessObject aBusinessObject) throws SQLException, InvalidIdException {
        boolean exists = checkIfExistsInDB(aBusinessObject);
        if (exists) {
            cache.remove(aBusinessObject.getUuid());
            updateDatabase(aBusinessObject);
        } else {
            insertIntoDatabase(aBusinessObject);
        }
    }


    private void insertIntoDatabase(BusinessObject aBusinessObject)
            throws SQLException, InvalidIdException {
        String tableName = BusinessObject.getTableNameFromId(aBusinessObject.getUuid());
        insertBO(aBusinessObject, tableName);
        insertRelations(aBusinessObject, tableName);
    }


    private void insertRelations(BusinessObject aBusinessObject,
                                 String tableName) throws SQLException {
        if (aBusinessObject.getRelatedBOs().isEmpty()) return;
        switch (tableName) {
            case TABLE_NAME_APPLICATION: {
                Application app = (Application) aBusinessObject;
                insertSingleRelationInto(TABLE_NAME_APPLICATION_FEATURE, app.getUuid(), 1, app.getRelatedFeatures());
                insertSingleRelationInto(TABLE_NAME_APPLICATION_CATEGORY, app.getUuid(), 1, app.getRelatedCategories());
                break;
            }
            case TABLE_NAME_CATEGORY: {
                Category cat = (Category) aBusinessObject;
                insertSingleRelationInto(TABLE_NAME_APPLICATION_CATEGORY, cat.getUuid(), 2, cat.getRelatedApplications());
                break;
            }
            case TABLE_NAME_FEATURE: {
                Feature feat = (Feature) aBusinessObject;
                insertSingleRelationInto(TABLE_NAME_APPLICATION_FEATURE, feat.getUuid(), 2, feat.getRelatedApplications());
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
    }


    /**
     * @param tableName        the name of the relation table
     * @param uuid             the BO thats relations are to be stored
     * @param uuidColumnNumber column number of the BOs id in the table. 1 if BO is Application, 2 otherwise.
     * @param relatedIds       a collection of the uuid's of related BOs
     * @throws SQLException
     */
    private void insertSingleRelationInto(String tableName, String uuid,
                                          int uuidColumnNumber, Collection<String> relatedIds) throws SQLException {
        for (String foreignKey : relatedIds) {
            PreparedStatement prepQuery;
            prepQuery = sqlStatementFactory.getInsertRelation(tableName);
            prepQuery.setString(uuidColumnNumber, uuid);
            prepQuery.setString((uuidColumnNumber % 2) + 1, foreignKey);
            prepQuery.executeUpdate();
        }
    }

    //TODO fix duplication
    private void updateSingleRelation(
            String tableName, String uuid, int uuidColumnNumber,
            Collection<String> relatedIds) throws SQLException {
        for (String foreignKey : relatedIds) {
            PreparedStatement prepQuery;
            prepQuery = sqlStatementFactory.getInsertRelation(tableName);
            prepQuery.setString(uuidColumnNumber, uuid);
            prepQuery.setString((uuidColumnNumber % 2) + 1, foreignKey);
            prepQuery.executeUpdate();
        }
    }


    private void insertBO(BusinessObject aBusinessObject,
                          String tableName) throws SQLException {
        PreparedStatement prepQuery;
        prepQuery = sqlStatementFactory.getInsertInto(tableName);
        prepQuery.setString(1, aBusinessObject.getUuid());
        prepQuery.setString(2, aBusinessObject.getTitle());
        prepQuery.setString(3, aBusinessObject.getDescription());

        if (aBusinessObject instanceof Application) {
            Application a = (Application) aBusinessObject;
            prepQuery.setString(4, a.getShortDescription());
            prepQuery.setString(5, a.getContact());
            prepQuery.setString(6, a.getProvider());
        }

        prepQuery.executeUpdate();
    }


    private void updateDatabase(BusinessObject aBusinessObject)
            throws SQLException, InvalidIdException {
        String tableName = BusinessObject.getTableNameFromId(aBusinessObject.getUuid());
        updateBO(aBusinessObject, tableName);
        updateRelations(aBusinessObject, tableName);
    }


    private void updateRelations(BusinessObject aBusinessObject,
                                 String tableName) throws SQLException {
        if (aBusinessObject.getRelatedBOs().isEmpty()) return;
        switch (tableName) {
            case TABLE_NAME_APPLICATION: {
                Application app = (Application) aBusinessObject;
                updateSingleRelation(TABLE_NAME_APPLICATION_FEATURE, app.getUuid(), 1, app.getRelatedFeatures());
                updateSingleRelation(TABLE_NAME_APPLICATION_CATEGORY, app.getUuid(), 1, app.getRelatedCategories());
                break;
            }
            case TABLE_NAME_CATEGORY: {
                Category cat = (Category) aBusinessObject;
                updateSingleRelation(TABLE_NAME_APPLICATION_CATEGORY, cat.getUuid(), 2, cat.getRelatedApplications());
                break;
            }
            case TABLE_NAME_FEATURE: {
                Feature feat = (Feature) aBusinessObject;
                updateSingleRelation(TABLE_NAME_APPLICATION_FEATURE, feat.getUuid(), 2, feat.getRelatedApplications());
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void updateBO(BusinessObject aBusinessObject,
                          String tableName) throws SQLException {
        PreparedStatement prepQuery;
        prepQuery = sqlStatementFactory.getUpdate(tableName);
        prepQuery.setString(1, aBusinessObject.getTitle());
        prepQuery.setString(2, aBusinessObject.getDescription());
        prepQuery.setString(3, aBusinessObject.getUuid());
        prepQuery.executeUpdate();
    }

    public void deleteFromDatabase(String id) throws SQLException, InvalidIdException {
        cache.remove(id);

        String tableName = BusinessObject.getTableNameFromId(id);

        PreparedStatement prepQuery = sqlStatementFactory.getDeleteFrom(tableName);

        prepQuery.setString(1, id);
        prepQuery.executeUpdate();
    }

}

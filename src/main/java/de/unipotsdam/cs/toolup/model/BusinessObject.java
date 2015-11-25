package de.unipotsdam.cs.toolup.model;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static de.unipotsdam.cs.toolup.database.DatabaseController.*;

public abstract class BusinessObject {

    public static final Character ID_DELIMITER_CHAR = '-';
    public static final String ID_DELIMITER = ID_DELIMITER_CHAR.toString();
    static final String JSON_KEY_ID = "id";
    static final String JSON_KEY_DESCRIPTION = "description";
    static final String JSON_KEY_TITLE = "title";
    static final String JSON_KEY_FEATURES = "features";
    static final String JSON_KEY_CATEGORIES = "categories";
    static final String JSON_KEY_APPLICATIONS = "applications";
    static Map<String, String> keyMappingSqlJson = new HashMap<>(); //TODO Move to config file

    static {
        keyMappingSqlJson.put(TABLE_NAME_APPLICATION, JSON_KEY_APPLICATIONS);
        keyMappingSqlJson.put(TABLE_NAME_CATEGORY, JSON_KEY_CATEGORIES);
        keyMappingSqlJson.put(TABLE_NAME_FEATURE, JSON_KEY_FEATURES);
    }

    protected final HashMap<String, Collection<String>> relations = new HashMap<>();
    protected String uuid;
    protected String title;
    protected String description;


    public BusinessObject(String uuid, String title, String description) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
    }

    public BusinessObject(String uuid) {
        this(uuid, "", "");
    }

    /**
     * Extract the table name out of the uuid.
     * The uuid is structured like this: tablename-1234567
     *
     * @param uuid the id of a BO
     * @return an SQL table name
     * @throws InvalidIdException
     */
    public static String getTableNameFromId(String uuid) throws InvalidIdException {
        if (!uuid.contains(ID_DELIMITER)) {
            throw new InvalidIdException();
        }
        int indexOfFirstDash = uuid.indexOf(ID_DELIMITER_CHAR);
        return uuid.substring(0, indexOfFirstDash);
    }

    /**
     * Create a new BusinessObject with the values of a JSON representation.
     *
     * @param jsonRepresentation the JSON representation of a BO
     * @return a BO represented by the JSON input
     * @throws JSONException
     * @throws InvalidIdException
     */
    public static BusinessObject createBusinessObjectFromJson(
            JSONObject jsonRepresentation) throws JSONException, InvalidIdException {
        String id = jsonRepresentation.getString(JSON_KEY_ID);
        BusinessObject newlyCreatedBO = BusinessObjectFactory.createInstance(id);

        newlyCreatedBO.title = jsonRepresentation.getString(JSON_KEY_TITLE);
        newlyCreatedBO.description = jsonRepresentation.getString(JSON_KEY_DESCRIPTION);

        addRelationFromJson(newlyCreatedBO, jsonRepresentation, JSON_KEY_APPLICATIONS);
        addRelationFromJson(newlyCreatedBO, jsonRepresentation, JSON_KEY_FEATURES);
        addRelationFromJson(newlyCreatedBO, jsonRepresentation, JSON_KEY_CATEGORIES);

        return newlyCreatedBO;
    }

    private static void addRelationFromJson(BusinessObject newlyCreatedBO,
                                            JSONObject jsonRepresentation, String relationKey) throws JSONException, InvalidIdException {
        if (jsonRepresentation.has(relationKey)) {
            JSONArray relationElements = jsonRepresentation.getJSONArray(relationKey);
            addIdsOfAllElements(newlyCreatedBO, relationElements);
        }
    }

    private static void addIdsOfAllElements(BusinessObject newlyCreatedBO,
                                            JSONArray relationElements) throws JSONException, InvalidIdException {
        for (int i = 0; i < relationElements.length(); i++) {
            JSONObject app = relationElements.getJSONObject(i);
            newlyCreatedBO.addRelation(app.getString(JSON_KEY_ID));
        }
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A BusinesObject is defined to equal an other, if the uuid is equal.
     *
     * @param anOtherBO a BusinessObject instance to compare with
     * @return true, if and only if the uuid is equal.
     */
    public boolean equals(BusinessObject anOtherBO) {
        return this.uuid.equals(anOtherBO.getUuid());
    }

    /**
     * Checks if all the properties of the other BO has the same values as this BO.
     *
     * @param anOtherBO a BusinessObject instance to compare with
     * @return true, if and only if the field values are equal.
     */
    public boolean equalsInAllProperties(BusinessObject anOtherBO) {
        if (!this.uuid.equals(anOtherBO.getUuid())) return false;
        if (!this.title.equals(anOtherBO.getTitle())) return false;
        if (!this.description.equals(anOtherBO.getDescription())) return false;

        for (String relationName : this.relations.keySet()) {
            if (!this.relations.get(relationName).equals(anOtherBO.relations.get(relationName))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Extract the table name out of the uuid.
     * The uuid is structured like this: tablename/1234567
     *
     * @return an SQL table name
     * @throws InvalidIdException
     */
    public String getTableName() throws InvalidIdException {
        return getTableNameFromId(this.uuid);
    }

    /**
     * Converts a collection of uuids into a JSONArray, which contains JSONObjects of the pattern {"id":<uuid>"}
     *
     * @param relation a collection of related business objects
     * @return a JSONArray representation of the input collection
     * @throws JSONException
     */
    protected JSONArray relationAsJsonArray(Collection<String> relation) throws JSONException {
        JSONArray categories = new JSONArray();
        for (String s : relation) {
            categories.put(new JSONObject("{\"id\":\"" + s + "\"}"));
        }
        return categories;
    }

    /**
     * Creates a JSONObject with the values of this business objects' fields.
     */
    public JSONObject convertToJson() throws JSONException {
        JSONObject result = new JSONObject();
        result.put(JSON_KEY_ID, this.uuid);
        result.put(JSON_KEY_TITLE, this.title);
        result.put(JSON_KEY_DESCRIPTION, this.description);

        createRelationRepresentations(result);

        return result;
    }

    private void createRelationRepresentations(JSONObject result)
            throws JSONException {
        for (String key : this.relations.keySet()) {
            String relationName = keyMappingSqlJson.get(key);
            Collection<String> relatedIds = this.relations.get(key);
            result.put(relationName, relationAsJsonArray(relatedIds));
        }
    }

    /**
     * Gets a union of this object's related ids.
     *
     * @return a union of this object's related ids.
     */
    public Set<String> getRelatedBOs() {
        Set<String> relatedBOs = new HashSet<>();
        for (Collection<String> relationToSingleType : relations.values()) {
            relatedBOs.addAll(relationToSingleType);
        }
        return relatedBOs;
    }


    /**
     * Adds the given id to this object's relations.
     *
     * @param id the id of a BO
     * @throws InvalidIdException
     */
    public void addRelation(String id) throws InvalidIdException {
        String tableName = getTableNameFromId(id);

        if (!relations.containsKey(tableName)) {
            throw new IllegalArgumentException("Business Object of this type can not be related to an application: " + id);
        }

        this.relations.get(tableName).add(id);
    }
}

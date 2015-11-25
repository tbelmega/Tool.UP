package de.unipotsdam.cs.toolup.database;

import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.BusinessObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Extends the DatabaseController for testing purposes.
 * This DatabaseController subclass remembers all created objects and can delete them
 * to clean up after a test.
 *
 * @author Thiemo
 */
public class DatabaseControllerHelper extends DatabaseController {

    private static DatabaseControllerHelper instance;

    private Collection<String> createdObjectIds = new HashSet<>();

    protected DatabaseControllerHelper() throws IOException, SQLException {
        super();
    }

    public static DatabaseControllerHelper getInstance() throws IOException, SQLException {
        if (instance == null) {
            instance = new DatabaseControllerHelper();
        }
        return instance;
    }

    /**
     * Invokes the super classes method storeToDatabase,
     * but adds the id to a Collection of created test objects, if it doesn't exist in DB.
     *
     * @throws InvalidIdException
     * @throws SQLException
     */
    public void storeToDatabase(BusinessObject someBusinessObject) throws SQLException, InvalidIdException {
        if (!checkIfExistsInDB(someBusinessObject)) {
            createdObjectIds.add(someBusinessObject.getUuid());
        }
        super.storeToDatabase(someBusinessObject);
    }

    public void deleteCreatedBOsFromDatabase() throws SQLException, InvalidIdException {
        for (String id : createdObjectIds) {
            deleteFromDatabase(id);
        }
    }

}

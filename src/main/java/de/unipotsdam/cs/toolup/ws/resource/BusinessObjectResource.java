package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.NullBusinessObject;

import java.io.IOException;
import java.sql.SQLException;

public class BusinessObjectResource {

    protected static final String PATH_APPLICATION = "application";
    protected static final String PATH_FEATURE = "feature";
    protected static final String PATH_CATEGORY = "category";
    protected static final String PARAM_ID = "id";

    protected BusinessObject getBusinessObject(String id) {
        try {
            return DatabaseController.getInstance().load(id);

        } catch (SQLException | IOException | InvalidIdException e) {
            e.printStackTrace();
            return NullBusinessObject.getInstance();
        }
    }

}

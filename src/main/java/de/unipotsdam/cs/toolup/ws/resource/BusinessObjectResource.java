package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.exceptions.InvalidIdException;
import de.unipotsdam.cs.toolup.model.BusinessObject;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.sql.SQLException;

public class BusinessObjectResource {

    protected static final String PATH_APPLICATION = "/application";
    protected static final String PATH_FEATURE = "/feature";
    protected static final String PATH_CATEGORY = "/category";
    protected static final String PARAM_ID = "id";

    protected Response getBusinessObject(String id) {
        Response response;
        try {
            BusinessObject app = DatabaseController.getInstance().load(id);
            response = Response.ok(app).build();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response = Response.serverError().build();
        } catch (InvalidIdException e) {
            response = Response.status(Status.NOT_FOUND).build();
        }
        return response;
    }

}

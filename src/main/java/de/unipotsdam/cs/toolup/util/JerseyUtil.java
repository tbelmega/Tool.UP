package de.unipotsdam.cs.toolup.util;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Thiemo on 15.03.2016.
 */
public class JerseyUtil {

    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ALL = "*";

    public static Response createResponseOk(Object responseEntity) throws IOException, SQLException {
        return Response.ok(responseEntity).header(ACCESS_CONTROL_ALLOW_ORIGIN, ALL).build();
    }

}

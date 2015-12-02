package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.FeatureBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@Path("feature")
public class FeatureResource {

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<FeatureBean> getAll() throws IOException, SQLException {
        return FeatureBean.getAllFeatures();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public FeatureBean get(@PathParam("id") String id) throws Exception {
        return FeatureBean.getBean(id);
    }

}

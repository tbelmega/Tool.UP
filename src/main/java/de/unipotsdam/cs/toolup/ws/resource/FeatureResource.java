package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.FeatureBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path(BusinessObjectResource.PATH_FEATURE)
public class FeatureResource extends BusinessObjectResource {

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public FeatureBean get(@PathParam(PARAM_ID) String id) {
        return FeatureBean.getBean(id);
    }
}

package de.unipotsdam.cs.toolup.ws.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path(BusinessObjectResource.PATH_CATEGORY)
public class CategoryResource extends BusinessObjectResource {
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response get(@PathParam(PARAM_ID) String id) {
		return getBusinessObject(id);
    }    
}

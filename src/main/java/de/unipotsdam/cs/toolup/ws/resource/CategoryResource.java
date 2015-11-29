package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.CategoryBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path(BusinessObjectResource.PATH_CATEGORY)
public class CategoryResource extends BusinessObjectResource {

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public CategoryBean get(@PathParam(PARAM_ID) String id) {
        return CategoryBean.getBean(id);
    }
}

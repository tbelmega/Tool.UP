package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path(BusinessObjectResource.PATH_APPLICATION)
public class ApplicationResource extends BusinessObjectResource {

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ApplicationBean get(@PathParam(PARAM_ID) String id) {
        return  ApplicationBean.getBean(id);
    }

}

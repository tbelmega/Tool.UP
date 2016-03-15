package de.unipotsdam.cs.toolup.ws.resource;


import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.unipotsdam.cs.toolup.util.JerseyUtil.createResponseOk;

@Path("search")
public class SearchResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response post(@FormParam("search string") String searchString) throws Exception {
        return createResponseOk(ApplicationBean.searchFor(searchString));
    }
}

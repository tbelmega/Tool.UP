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

    /**
     * Search for all applications, that match the search String in their title or description
     * or the title or description of one of its features.
     *
     * @param searchString
     * @return
     * @throws Exception
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response post(@FormParam("search string") String searchString) throws Exception {
        return createResponseOk(ApplicationBean.searchFor(searchString));
    }
}

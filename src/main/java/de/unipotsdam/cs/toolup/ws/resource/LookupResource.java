package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.LookupResultBean;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.unipotsdam.cs.toolup.util.JerseyUtil.createResponseOk;


@Path("lookup")
public class LookupResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response post(@FormParam("features") String features) throws Exception {
        return createResponseOk(LookupResultBean.getBean(features));
    }


}



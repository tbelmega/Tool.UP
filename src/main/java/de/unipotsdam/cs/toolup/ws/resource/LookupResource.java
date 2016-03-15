package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.ApplicationLookup;
import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

import static de.unipotsdam.cs.toolup.util.JerseyUtil.createResponseOk;


@Path("lookup")
public class LookupResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response post(@FormParam("features") String features) throws Exception {
        ApplicationLookup lookup = new ApplicationLookup(features);
        Set<Application> applications = lookup.getApplications();
        return createResponseOk(ApplicationBean.getBeans(applications));
    }


}



package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.model.Application;
import de.unipotsdam.cs.toolup.model.ApplicationLookup;
import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Set;


@Path("lookup")
public class LookupResource {

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<ApplicationBean> post(@FormParam("features") String features) throws Exception {
        ApplicationLookup lookup = new ApplicationLookup(features);
        Set<Application> applications = lookup.getApplications();
        return ApplicationBean.getBeans(applications);
    }


}



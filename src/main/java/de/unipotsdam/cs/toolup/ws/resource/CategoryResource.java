package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.CategoryBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

import static de.unipotsdam.cs.toolup.util.JerseyUtil.createResponseOk;


@Path("category")
public class CategoryResource {

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAll() throws Exception {
        return createResponseOk(CategoryBean.getAllCategories());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response get(@PathParam("id") String id) throws Exception{
        return createResponseOk(CategoryBean.getBean(id));
    }

    @GET
    @Path("/withApplication")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllWithApplication() throws Exception {
        return  createResponseOk(CategoryBean.getAllCategoriesWithApplication());
    }

    @GET
    @Path("/toplevel")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getTopLevelCategories() throws Exception {
        return createResponseOk(CategoryBean.getTopLevelCategories());
    }


}

package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.CategoryBean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


@Path("category")
public class CategoryResource {


    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<CategoryBean> getAll() throws Exception {
        return CategoryBean.getAllCategories();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public CategoryBean get(@PathParam("id") String id) throws Exception{
        return CategoryBean.getBean(id);
    }

    @GET
    @Path("/withApplication")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<CategoryBean> getAllWithApplication() throws Exception {
        return  CategoryBean.getAllCategoriesWithApplication();
    }

    @GET
    @Path("/toplevel")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<CategoryBean> getTopLevelCategories() throws IOException, SQLException {
        return CategoryBean.getTopLevelCategories();
    }
}

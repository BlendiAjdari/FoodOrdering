package org.foodordering.resource;

import com.google.gson.Gson;
import org.foodordering.domain.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.foodordering.service.Categories_DB.*;

@Path("/categories")
public class CategoryResource {
    Gson gson = new Gson();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories(Category category) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(getAllCategorydb())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(Category category) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        saveCategory(category);
        return Response.status(Response.Status.CREATED).entity(category).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategory(Category category) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(updateCategorydb(category))).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCategory(Category category) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(deleteCategorydb(category))).build();
    }
}

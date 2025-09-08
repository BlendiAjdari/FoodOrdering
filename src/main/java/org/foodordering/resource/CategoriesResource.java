package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Category;
import org.foodordering.service.CategoriesService;
import org.foodordering.service.CategoriesServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/Categories")
public class CategoriesResource extends AbstractResource {
    CategoriesService categoryService = new CategoriesServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() throws Exception{
        List<Category> categories = new ArrayList<>(categoryService.getAllCategories());
        return Response.ok(gson().toJson(categories)).build();
    }
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(String payload) throws Exception{
        Category category = gson().fromJson(payload,Category.class);
        category.setId(category.getId());
        category.setName(category.getName());
        categoryService.addCategory(category);
        return Response.ok(gson().toJson(category)).build();
    }
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCategory(Category category) throws Exception{
        categoryService.deleteCategory(category);
        return Response.ok("Deleted").build();
    }
    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") int id ,String payload) throws Exception{
        Category category = gson().fromJson(payload,Category.class);
        category.setId(id);
        category.setName(category.getName());
        categoryService.updateCategory(category);
        return Response.ok(gson().toJson(category)).build();

    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") int id) throws Exception{
        Category category = categoryService.getCategoryById(id);
        return Response.ok(gson().toJson(category)).build();
    }
}

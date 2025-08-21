package org.foodordering.resource;

import org.foodordering.domain.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.foodordering.service.Product_DB.*;


@Path("/products")
public class ProductResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) throws SQLException {
        postProducts(product);
        return Response.status(Response.Status.CREATED).entity(product).build();

    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() throws SQLException {
        return  Response.status(Response.Status.OK).entity(getAllProducts()).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putProduct(Product product) throws SQLException {
        return Response.status(Response.Status.CREATED).entity(updateProduct(product)).build();
    }
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProducts(Product product) throws SQLException {
        deleteProduct(product.getId());
        return Response.status(Response.Status.OK).build();
    }

}

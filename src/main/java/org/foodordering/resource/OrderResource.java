package org.foodordering.resource;

import com.google.gson.Gson;
import org.foodordering.domain.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.foodordering.service.Order_DB.*;

@Path("/Orders")
public class OrderResource {
    Gson gson = new Gson();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postOrder(Order order) throws SQLException {
        saveOrder(order);
        return Response.status(Response.Status.OK).entity(gson.toJson(order)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() throws SQLException {
        List<Order> orders = new ArrayList<>(getAllOrders());
        return Response.status(Response.Status.OK).entity(gson.toJson(orders)).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putOrder(Order order) throws SQLException {
        updateOrder(order);
        return Response.status(Response.Status.OK).entity(gson.toJson(order)).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrders(Order order) throws SQLException {
        deleteOrderdb(order);
        return Response.status(Response.Status.OK).entity(gson.toJson(order)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("id") int id) throws SQLException {

        return Response.status(Response.Status.OK).entity(gson.toJson(getOrderdbyID(id))).build();
    }

}

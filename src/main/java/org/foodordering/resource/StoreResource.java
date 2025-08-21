package org.foodordering.resource;

import com.google.gson.Gson;
import org.foodordering.domain.Store;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static org.foodordering.service.Store_DB.*;
@Path("/Stores")
public class StoreResource {
    Gson gson = new Gson();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStores() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);}
        List<Store> stores = new ArrayList<>(getAllStores());
        return Response.status(Response.Status.OK).entity(gson.toJson(stores)).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postStore(Store store) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);}
        saveStoredb(store);
        return Response.status(Response.Status.CREATED).entity(gson.toJson(store)).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStore(Store store) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        deleteStoredb(store);
        return Response.status(Response.Status.OK).entity(gson.toJson(store)).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putStore(Store store) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        updateStore(store);
        return Response.status(Response.Status.OK).entity(gson.toJson(store)).build();
    }

}

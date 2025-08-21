package org.foodordering.resource;

import org.foodordering.domain.Addresses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.foodordering.service.Address_DB.*;


@Path("/Addresses")
public class AddressResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAddress(Addresses address) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        saveAddress(address);
        return Response.status(Response.Status.OK).entity(address).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddresses() throws SQLException {
       return Response.status(Response.Status.OK).entity(getAllAddresses()).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response putAddress(Addresses address) throws SQLException {
        updateAddress(address);
        return Response.status(Response.Status.OK).entity(address).build();

    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletedAddress(Addresses address) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        deleteAddress(address);
        return Response.status(Response.Status.OK).entity(address).build();
    }
    @GET
    @Path("/{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddress(@PathParam("city") String city) throws SQLException {
        return Response.status(Response.Status.OK).entity(getAddressByCity(city)).build();
    }
}

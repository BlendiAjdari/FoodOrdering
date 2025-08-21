package org.foodordering.resource;

import com.google.gson.Gson;
import org.foodordering.domain.Costumer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.foodordering.service.Costumer_DB.*;

@Path("/costumer")
public class CostumerResource {
    Gson gson = new Gson();
    @POST
    @Consumes("application/JSON")
    public Response createCostumer(Costumer costumer) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        saveCostumer(costumer);
        return Response.status(Response.Status.CREATED).entity(gson.toJson(costumer)).build();
    }

    @GET
    @Produces("application/JSON")
    public Response getCostumers() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Costumer> costumers = new ArrayList<>(getAllcostumers());
        return Response.status(Response.Status.OK).entity(gson.toJson(costumers)).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putCostumer(Costumer costumer) throws SQLException {
        updateCostumer(costumer);
        return Response.status(Response.Status.OK).entity(costumer).build();
    }
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCostumers(Costumer costumer) throws SQLException {
        deleteCostumer(costumer);
        return Response.status(Response.Status.OK).entity(costumer).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCostumersByID(@PathParam("id") int id) throws SQLException {
        return Response.status(Response.Status.OK).entity(gson.toJson(getCostumerbyID(id))).build();
    }

}

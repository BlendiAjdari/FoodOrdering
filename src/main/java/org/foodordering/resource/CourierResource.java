package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Courier;
import org.foodordering.service.CourierService;
import org.foodordering.service.CourierServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Courier")
public class CourierResource extends AbstractResource {
    CourierService courierService = new CourierServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourier() throws Exception {
        return Response.ok(gson().toJson(courierService.getAllCouriers())).build();
    }
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCourier(String payload) throws Exception {
        Courier courier = gson().fromJson(payload, Courier.class);
        courier.setId(courier.getId());
        courier.setName(courier.getName());
        courier.setCurrent_location(courier.getCurrent_location());
        courier.setStatus(courier.getStatus());
        courierService.addCourier(courier);
        return Response.ok(gson().toJson(courier)).build();
    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCourier(Courier courier) throws Exception {
        courierService.deleteCourier(courier);
        return Response.ok().build();
    }
    @PUT
    @Path("/{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourier(@PathParam("id") int id ,String payload) throws Exception {
        Courier courier = gson().fromJson(payload, Courier.class);
        courier.setId(id);
        courier.setName(courier.getName());
        courier.setCurrent_location(courier.getCurrent_location());
        courier.setStatus(courier.getStatus());
        courierService.updateCourier(courier);
        return Response.ok(gson().toJson(courier)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourierById(@PathParam("id") int id) throws Exception {
        Courier courier = courierService.getCourierById(id);
        return Response.ok(gson().toJson(courier)).build();
    }
}

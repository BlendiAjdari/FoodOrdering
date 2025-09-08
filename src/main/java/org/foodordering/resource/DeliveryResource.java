package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Delivery;
import org.foodordering.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/Delivery")
public class DeliveryResource extends AbstractResource {
    DeliveryService deliveryService = new DeliveryServiceImpl();
    OrderService orderService = new OrderServiceImpl();
    CourierService courierService = new CourierServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDelivery() throws Exception {
        List<Delivery> deliveries = new ArrayList<>(deliveryService.getAllDelivery());
        return Response.ok(gson().toJson(deliveries)).build();
    }
    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDelivery(String payload) throws Exception {
        Delivery delivery = gson().fromJson(payload, Delivery.class);
        delivery.setId(delivery.getId());
        delivery.setCustomer_id(delivery.getCustomer_id());
        delivery.setOrders(orderService.getOrdersByCustomerId(delivery.getCustomer_id()));

        delivery.setCourier_id(delivery.getCourier_id());
        delivery.setCourier(courierService.getCourierById(delivery.getCourier_id()));
        delivery.setStatus(delivery.getStatus());
        delivery.setPickup_time(delivery.getPickup_time());
        delivery.setDelivery_time(delivery.getDelivery_time());

        deliveryService.addDelivery(delivery);
        return Response.ok(gson().toJson(delivery)).build();
    }

    @PUT
    @Path("/{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDelivery(@PathParam("id") int id, String payload) throws Exception {
        Delivery delivery = gson().fromJson(payload, Delivery.class);
        delivery.setId(id);
        delivery.setCustomer_id(delivery.getCustomer_id());
        delivery.setOrders(orderService.getOrdersByCustomerId(delivery.getCustomer_id()));
        delivery.setCourier_id(delivery.getCourier_id());
        delivery.setCourier(courierService.getCourierById(delivery.getCourier_id()));
        delivery.setStatus(delivery.getStatus());
        delivery.setPickup_time(delivery.getPickup_time());
        delivery.setDelivery_time(delivery.getDelivery_time());
        deliveryService.updateDelivery(delivery);
        return Response.ok(gson().toJson(delivery)).build();

    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDelivery(Delivery delivery) throws Exception {
        deliveryService.deleteDelivery(delivery);
        return  Response.ok("Deleted").build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliveryById(@PathParam("id") int id) throws Exception {
        Delivery delivery = deliveryService.getDeliveryById(id);
        return Response.ok(gson().toJson(delivery)).build();
    }
}

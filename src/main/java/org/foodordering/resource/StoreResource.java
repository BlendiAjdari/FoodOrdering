package org.foodordering.resource;


import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Category;
import org.foodordering.domain.Order;
import org.foodordering.domain.Store;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import org.foodordering.service.OrderService;
import org.foodordering.service.OrderServiceImpl;
import org.foodordering.service.StoreServiceImpl;


@Path("/Stores")
public class StoreResource extends AbstractResource {

    StoreServiceImpl storeService = new StoreServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStores() throws Exception {
            List<Store> stores = storeService.getAllStores();
            return Response.ok(gson().toJson(stores)).build();

    }
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postStore(String payload) throws Exception {
     Store store = gson().fromJson(payload,Store.class);
     store.setId(store.getId());
     store.setName(store.getName());
     store.setAddress(store.getAddress());
     store.setContact(store.getContact());
     store.setDelivery_options(store.getDelivery_options());
     store.setOpens(store.getOpens());
     store.setCloses(store.getCloses());
     storeService.addStore(store);
     return Response.ok(gson().toJson(store)).build();

    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStore(Store store) throws Exception {
        storeService.deleteStore(store);
        return Response.ok().build();


    }
    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putStore(@PathParam("id") int id,String payload) throws Exception {
        Store store = gson().fromJson(payload,Store.class);
        store.setId(store.getId());
        store.setName(store.getName());
        store.setAddress(store.getAddress());
        store.setContact(store.getContact());
        store.setDelivery_options(store.getDelivery_options());
        store.setOpens(store.getOpens());
        store.setCloses(store.getCloses());
        storeService.updateStore(store);
        return Response.ok().build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStore(@PathParam("id") int id) throws Exception {
        Store n = storeService.getStoreById(id);
        return Response.ok(gson().toJson(n)).build();
    }

    @GET
    @Path("/dashboard/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoreDashboard(@PathParam("id") int id) throws Exception {
        OrderService orderService = new OrderServiceImpl();
        List<Order> ordersByStore =orderService.ordersByStore(id);
        return Response.ok(gson().toJson(ordersByStore)).build();
    }


}

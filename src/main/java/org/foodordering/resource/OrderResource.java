package org.foodordering.resource;


import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;
import org.foodordering.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("/Orders")
public class OrderResource extends AbstractResource {
    OrderServiceImpl orderService = new OrderServiceImpl();
    OrderItemService orderItemService = new OrderItemServiceImpl();
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertOrder(String payload) throws Exception {
        Order order = gson().fromJson(payload, Order.class);
        order.setId(order.getId());
        order.setDate(Date.valueOf(LocalDate.now()));
        order.setAmount(order.getAmount());
        order.setStatus(order.getStatus());
        order.setCostumer_id(order.getCostumer_id());
        CustomerServiceImpl  customerService = new CustomerServiceImpl();
        order.setCustomer(customerService.getCustomerById(order.getCostumer_id()));
        order.setStore_id(order.getStore_id());
        StoreServiceImpl storeService = new StoreServiceImpl();
        order.setStore(storeService.getStoreById(order.getStore_id()));
        orderService.addOrder(order);
        return Response.ok(gson().toJson(order)).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>(orderService.getAllOrders());
        return Response.ok(gson().toJson(orders)).build();
    }
    @DELETE
    @Path("/delete/{customer_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteOrdersByCustomerId(@PathParam("customer_id")int customer_id) throws Exception {
        orderService.deleteOrderByCustomerId(customer_id);
        return Response.ok("Deleted").build();
    }
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteOrder(Order order) throws Exception {
        orderService.deleteOrder(order);
        return Response.ok().build();
    }
    @PUT
    @Path("/{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("id") int id, String payload) throws Exception {
        Order order = gson().fromJson(payload, Order.class);
        order.setId(id);
        order.setDate(order.getDate());
        order.setStatus(order.getStatus());
        order.setCostumer_id(order.getCostumer_id());
        CustomerServiceImpl  customerService = new CustomerServiceImpl();
        order.setCustomer(customerService.getCustomerById(order.getId()));
        order.setStore_id(order.getStore_id());
        StoreServiceImpl storeService = new StoreServiceImpl();
        order.setStore(storeService.getStoreById(order.getStore_id()));
        orderService.updateOrder(order);
        return Response.ok(gson().toJson(order)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") int id) throws Exception {
        Order order = orderService.getOrderById(id);
        return Response.ok(gson().toJson(order)).build();
    }

}

package org.foodordering.resource;


import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Order;
import org.foodordering.domain.OrderItem;
import org.foodordering.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        order.setAmount(order.getAmount());
        order.setDate(order.getDate());
        order.setStatus(order.getStatus());
        order.setCostumer_id(order.getCostumer_id());
        CustomerServiceImpl  customerService = new CustomerServiceImpl();
        order.setCustomer(customerService.getCustomerById(order.getId()));
        order.setStore_id(order.getStore_id());
        StoreServiceImpl storeService = new StoreServiceImpl();
        order.setStore(storeService.getStoreById(order.getStore_id()));
        orderService.addOrder(order);
        return Response.ok(order).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>(orderService.getAllOrders());
        return Response.ok(gson().toJson(orders)).build();
    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
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
        order.setId(order.getId());
        order.setAmount(order.getAmount());
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


    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrderItems() throws Exception {
        List<OrderItem> orderItems = new ArrayList<OrderItem>(orderItemService.getAllOrderItems());
        return Response.ok(gson().toJson(orderItems)).build();
    }
    @POST
    @Path("/items/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertOrderItems(String payload) throws Exception {
        ProductServiceImpl productService = new ProductServiceImpl();
        OrderItem orderItem = gson().fromJson(payload, OrderItem.class);
        orderItem.setId(orderItem.getId());
        orderItem.setOrder_id(orderItem.getOrder_id());
        orderItem.setProduct_id(orderItem.getProduct_id());
        orderItem.setQuantity(orderItem.getQuantity());
        orderItem.setUnit_price(orderItem.getUnit_price());
        orderItemService.addOrderItem(orderItem);
        return Response.ok(gson().toJson(orderItem)).build();

    }
    @DELETE
    @Path("items/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrderItems(OrderItem orderItem) throws Exception {
        orderItemService.deleteOrderItem(orderItem);
        return Response.ok().build();
    }

    @PUT
    @Path("items/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderItems(@PathParam("id") int id, String payload) throws Exception {
        OrderItem orderItem = gson().fromJson(payload, OrderItem.class);
        orderItem.setId(orderItem.getId());
        orderItem.setOrder_id(orderItem.getOrder_id());
        orderItem.setProduct_id(orderItem.getProduct_id());
        orderItem.setQuantity(orderItem.getQuantity());
        orderItem.setUnit_price(orderItem.getUnit_price());
        orderItemService.updateOrderItem(orderItem);
        return Response.ok(gson().toJson(orderItem)).build();
    }

}

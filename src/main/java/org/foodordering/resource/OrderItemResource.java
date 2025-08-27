package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.OrderItem;
import org.foodordering.service.OrderItemService;
import org.foodordering.service.OrderItemServiceImpl;
import org.foodordering.service.ProductServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/OrderItem")
public class OrderItemResource extends AbstractResource {
    OrderItemService orderItemService = new OrderItemServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrderItems() throws Exception {
        List<OrderItem> orderItems = new ArrayList<OrderItem>(orderItemService.getAllOrderItems());
        return Response.ok(orderItems).build();
    }
    @POST
    @Path("/insert")
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
        return Response.ok(orderItem).build();

    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrderItems(OrderItem orderItem) throws Exception {
        orderItemService.deleteOrderItem(orderItem);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/update")
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
        return Response.ok(orderItem).build();
    }

}

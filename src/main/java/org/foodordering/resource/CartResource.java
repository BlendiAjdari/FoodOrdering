package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Cart;
import org.foodordering.domain.CartItem;
import org.foodordering.domain.Order;
import org.foodordering.domain.Product;
import org.foodordering.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Path("/cart")
public class CartResource extends AbstractResource {
    CartService cartService = new CartServiceImpl();
    CartItemService cartItemService = new CartItemServiceImpl();
    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCart(String payload) throws Exception {
        Cart cart = gson().fromJson(payload, Cart.class);
        cart.setCustomer_id(cart.getCustomer_id());
        cart.setCreated_at(cart.getCreated_at());
        cartService.addCart(cart);
        return  Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart() throws Exception {
        List<Cart> carts = new ArrayList<>(cartService.getAllCarts());
        return Response.ok(carts).build();
    }
    @PUT
    @Path("/{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putCart(@PathParam("id") int id,String payload) throws Exception {
        Cart cart = gson().fromJson(payload, Cart.class);
        cart.setCustomer_id(cart.getCustomer_id());
        cart.setCustomer( cart.getCustomer() );
        cart.setCreated_at(cart.getCreated_at());
        cartService.updateCart(cart);
        return  Response.ok().build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCarts(Cart cart) throws Exception {
        cartService.deleteCart(cart);
        return  Response.ok().build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartById(@PathParam("id") int id) throws Exception {
        Cart cart = cartService.getCartById(id);
        return Response.ok(cart).build();
    }
    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartItems() throws Exception {
        List<CartItem> items = new ArrayList<>(cartItemService.getAllCartItems());
        return Response.ok(items).build();
    }
    @POST
    @Path("/items/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCartItem(String Payload) throws Exception {
        CartItem  item = gson().fromJson(Payload, CartItem.class);
        item.setId(item.getId());
        item.setCart_id(item.getCart_id());
        item.setProduct_id(item.getProduct_id());
        item.setQuantity(item.getQuantity());
        cartItemService.addCartItem(item);

        return  Response.ok(item).build();

    }
    @DELETE
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCartItems(CartItem cartItem) throws Exception {
        cartItemService.deleteCartItem(cartItem);
        return  Response.ok("Deleted").build();
    }
    @PUT
    @Path("/items/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCartItem(@PathParam("id") int id,String payload) throws Exception {
        CartItem item = gson().fromJson(payload, CartItem.class);
        item.setId(item.getId());
        item.setCart_id(item.getCart_id());
        item.setProduct_id(item.getProduct_id());
        item.setQuantity(item.getQuantity());
        cartItemService.updateCartItem(item);
        return  Response.ok("Updated").build();
    }
    @Context

    UriInfo uriInfo;

//    @GET
//    @Path("/{id}/items/Order/")
//    public Response redirectToOrder(@PathParam("id") int id) throws Exception {
//        OrderService orderService = new OrderServiceImpl();
//        CartService  cartService = new CartServiceImpl();
//        CartItemService cartItemService = new CartItemServiceImpl();
//        CartItem cartItem = cartItemService.getCartItemById(id);
//        Cart cart = cartService.getCartById(id);
//        Order order = new Order();
//        order.setAmount(new BigDecimal(3));
//        order.setDate(Date.valueOf(LocalDate.now()));
//        order.setStatus("Preparing");
//        order.setCostumer_id(cart.getCustomer_id());
//        order.setStore_id(orderService.StoreIdFromProduct(product.getId()));
//        orderService.addOrder(order);
//        orderService.getOrderById(order.getId());
//        String n = "/items/Order";
//        if (n.equals("/items/Order")){
//            URI uri = uriInfo.getBaseUriBuilder().path("Orders").path(String.valueOf(order.getId())).build();
//            return Response.seeOther(uri).build();
//        }return null;
//    }

}

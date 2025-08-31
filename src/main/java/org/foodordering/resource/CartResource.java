package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.*;
import org.foodordering.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        cart.setCreated_at(Time.valueOf(LocalTime.now()));
        cartService.addCart(cart);
        return  Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart() throws Exception {
        List<Cart> carts = new ArrayList<>(cartService.getAllCarts());
        return Response.ok(gson().toJson(carts)).build();
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
        return Response.ok(gson().toJson(cart)).build();
    }
    @GET
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartItems() throws Exception {
        List<CartItem> items = new ArrayList<>(cartItemService.getAllCartItems());
        return Response.ok(gson().toJson(items)).build();
    }
    @POST
    @Path("/items/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCartItem(String Payload) throws Exception {
        ProductService productService = new ProductServiceImpl();
        CartItem  item = gson().fromJson(Payload, CartItem.class);
        item.setId(item.getId());
        item.setCart_id(item.getCart_id());
        item.setProduct_id(item.getProduct_id());
        item.setQuantity(item.getQuantity());
        item.setPrice(productService.getProductById(item.getProduct_id()).getPrice());
        cartItemService.addCartItem(item);

        return  Response.ok(gson().toJson(item)).build();

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

    @GET
    @Path("/{id}/finalise")
    public Response redirectToCheckout(@PathParam("id") int id) throws Exception {
        String n = "/"+id+"finalise";
        if (n.equals("/"+id+"finalise")){

            URI uri = uriInfo.getBaseUriBuilder().path("Checkout").path(String.valueOf(redirectToCheckout(id))).build();
            return Response.seeOther(uri).build();
        }return null;
    }




}

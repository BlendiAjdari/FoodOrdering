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


@Path("/Cart")
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
        return  Response.ok(gson().toJson(cart)).build();
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
        cart.setCustomer_id(id);
        cart.setCustomer( cart.getCustomer() );
        cart.setCreated_at(cart.getCreated_at());
        cartService.updateCart(cart);
        return  Response.ok(gson().toJson(cart)).build();
    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCarts(Cart cart) throws Exception {
        cartService.deleteCart(cart);
        return  Response.ok("Delete").build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartById(@PathParam("id") int id) throws Exception {
        Cart cart = cartService.getCartById(id);
        return Response.ok(gson().toJson(cart)).build();
    }
    @Context
    public UriInfo uriInfo;

    @GET
    @Path("/{id}/finalise")
    public Response toCheckout(@PathParam("id") int id) throws Exception {
        String n = "/"+id+"/finalise";
        if (n.equals("/"+id+"/finalise")){

            URI uri = uriInfo.getBaseUriBuilder().path("Checkout").path(String.valueOf(cartService.redirectToCheckout(id))).build();
            return Response.seeOther(uri).build();
        }return null;
    }




}

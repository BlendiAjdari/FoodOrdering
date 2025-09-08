package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.CartItem;
import org.foodordering.service.CartItemService;
import org.foodordering.service.CartItemServiceImpl;
import org.foodordering.service.ProductService;
import org.foodordering.service.ProductServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/Cartitem")
public class CartItemResource extends AbstractResource {
    CartItemService cartItemService = new CartItemServiceImpl();
    ProductService productService = new ProductServiceImpl();
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCartItem(String Payload) throws Exception {
        CartItem item = gson().fromJson(Payload, CartItem.class);
        item.setId(item.getId());
        item.setCart_id(item.getCart_id());
        item.setProduct_id(item.getProduct_id());
        item.setQuantity(item.getQuantity());
        item.setPrice(productService.getProductById(item.getProduct_id()).getPrice());
        cartItemService.addCartItem(item);
        return  Response.ok(gson().toJson(item)).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartItems() throws Exception {
        List<CartItem> items = new ArrayList<>(cartItemService.getAllCartItems());
        return Response.ok(gson().toJson(items)).build();
    }
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCartItems(CartItem cartItem) throws Exception {
        cartItemService.deleteCartItem(cartItem);
        return  Response.ok("Deleted").build();
    }
    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCartItem(@PathParam("id") int id,String payload) throws Exception {
        CartItem item = gson().fromJson(payload, CartItem.class);
        item.setId(id);
        item.setCart_id(item.getCart_id());
        item.setProduct_id(item.getProduct_id());
        item.setQuantity(item.getQuantity());
        item.setPrice(productService.getProductById(item.getProduct_id()).getPrice());
        cartItemService.updateCartItem(item);
        return Response.ok(gson().toJson(item)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartItem(@PathParam("id") int id) throws Exception {
        return  Response.ok(gson().toJson(cartItemService.getCartItemById(id))).build();
    }
}

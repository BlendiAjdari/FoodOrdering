package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Checkout;
import org.foodordering.domain.OrderItem;
import org.foodordering.service.CheckoutService;
import org.foodordering.service.CheckoutServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/Checkout")
public class CheckoutResource extends AbstractResource {
    CheckoutService checkoutService = new CheckoutServiceImpl();
   @POST
   @Path("/insert")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response insertCheckout(String payload) throws Exception{
       Checkout checkout = gson().fromJson(payload, Checkout.class);
       checkout.setId(checkout.getId());
       checkout.setCustomer_id(checkout.getCustomer_id());
       checkout.setAddress(checkout.getAddress());
       checkoutService.addCheckout(checkout);
       return Response.ok(gson().toJson(checkout)).build();
   }
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getAllCheckouts() throws Exception {
       List<Checkout> checkouts = new ArrayList<Checkout>(checkoutService.getCheckouts());
       return Response.ok(gson().toJson(checkouts)).build();
   }
   @PUT
   @Path("/{id}/update")
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateCheckout(@PathParam("id") Integer id, String payload) throws Exception {
       Checkout checkout = gson().fromJson(payload, Checkout.class);
       checkout.setId(id);
       checkout.setCustomer_id(checkout.getCustomer_id());
       checkout.setAddress_id(checkout.getAddress_id());
       checkoutService.updateCheckout(checkout);
       return Response.ok(gson().toJson(checkout)).build();
   }
   @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCheckout(Checkout checkout) throws Exception {
       checkoutService.deleteCheckout(checkout);
       return Response.ok().build();
   }
   @GET
   @Path("/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getCheckout(@PathParam("id") Integer id) throws Exception {
       Checkout checkout =checkoutService.getCheckoutById(id);
       return Response.ok(gson().toJson(checkout)).build();
   }
}


package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Card;
import org.foodordering.service.CardService;
import org.foodordering.service.CardServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/Card")
public class CardResource extends AbstractResource {
    CardService cardService = new CardServiceImpl();
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCard(String payload) throws Exception {
        Card card = gson().fromJson(payload,Card.class);
        card.setId(card.getId());
        card.setCustomer_id(card.getCustomer_id());
        card.setExpiryDate(card.getExpiryDate());
        card.setCardVerificationValue(card.getCardVerificationValue());
        card.setCardName(card.getCardName());
        card.setCardNumber(card.getCardNumber());
        cardService.addCard(card);
        return Response.ok(gson().toJson(card)).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCards() throws Exception {
        List<Card>cards =new ArrayList<>(cardService.getCards());
        return Response.ok(gson().toJson(cards)).build();
    }
    @GET
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCardChecked(){
        return Response.ok("Post your Card number and CVV to confirm").build();
    }
    @POST
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkCard(String payload) throws Exception {
        Card card = gson().fromJson(payload,Card.class);
        card.setCardNumber(card.getCardNumber());
        card.setCardVerificationValue(card.getCardVerificationValue());
        cardService.cardCheck(card);
        return Response.ok(gson().toJson("Payment done successfully!")).build();
    }
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCardById(@PathParam("id") int id) throws Exception {
        Card card = cardService.getCardById(id);
        return Response.ok(gson().toJson(card)).build();
    }
    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCardById(@PathParam("id") int id,String payload) throws Exception {
        Card card = gson().fromJson(payload,Card.class);
        card.setId(id);
        card.setCustomer_id(card.getCustomer_id());
        card.setExpiryDate(card.getExpiryDate());
        card.setCardVerificationValue(card.getCardVerificationValue());
        card.setCardName(card.getCardName());
        card.setCardNumber(card.getCardNumber());
        cardService.updateCard(card);
        return Response.ok(gson().toJson(card)).build();

    }
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCardById(Card card) throws Exception {
        cardService.deleteCard(card);
        return Response.ok().build();
    }

}

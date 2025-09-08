package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Card;
import org.foodordering.domain.Encryption;
import org.foodordering.domain.Ewallet;
import org.foodordering.domain.Payment;
import org.foodordering.service.*;
import org.glassfish.jersey.server.Uri;


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
import java.util.List;

@Path("/Payment")
public class PaymentResource extends AbstractResource {
    PaymentService paymentService= new PaymentServiceImpl();
    CardService cardService= new CardServiceImpl();
    OrderService orderService= new OrderServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayment() throws Exception {
        List<Payment> payments = new ArrayList<Payment>(paymentService.getAllPayments());
        return Response.ok(gson().toJson(payments)).build();

    }
    @Context
    UriInfo uriInfo;
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(@QueryParam("method") String method, String payload) throws Exception {
        if(method.equals("Cash")){
            Payment payment = gson().fromJson(payload, Payment.class);
            payment.setId(payment.getId());
            payment.setCustomerId(payment.getCustomerId());
            BigDecimal totalAmount = orderService.orderAmountByCustomerId(payment.getCustomerId());
            payment.setAmount(totalAmount);
            payment.setMethod(method);
            payment.setStatus(payment.getStatus());
            payment.setOrders(orderService.getOrdersByCustomerId(payment.getCustomerId()));
            payment.setDate(Date.valueOf(LocalDate.now()));
            paymentService.addPayment(payment);
        return Response.ok(gson().toJson("Payment will be done at hand")).build();}

        if(method.equals("Card")) {
            Payment payment = gson().fromJson(payload, Payment.class);
            payment.setId(payment.getId());
            payment.setCustomerId(payment.getCustomerId());
            payment.setCard_id(payment.getCard_id());
            BigDecimal totalAmount = orderService.orderAmountByCustomerId(payment.getCustomerId());
            payment.setAmount(totalAmount);
            payment.setMethod(method);
            payment.setStatus(payment.getStatus());
            payment.setOrders(orderService.getOrdersByCustomerId(payment.getCustomerId()));
            payment.setDate(Date.valueOf(LocalDate.now()));
            if(payment.getCard_id()!=0){

            Card card1 = gson().fromJson(payload, Card.class);
            card1.setCardNumber(card1.getCardNumber());
            card1.setCardVerificationValue(card1.getCardVerificationValue());

            Card card = cardService.getCardById(payment.getCard_id());
            String n = Encryption.encrypt(card1.getCardNumber());
            String n1 = Encryption.encrypt(card1.getCardVerificationValue());
            if (n.equals(card.getCardNumber()) && n1.equals(card.getCardVerificationValue())) {
                    paymentService.addPayment(payment);
                    return Response.ok(gson().toJson(payment)).build();
            } else {
                    return Response.status(401).entity("Incorrect card details!").build();
            }}
            else {
                throw new Exception("Card id cannot be empty");
            }

        }
        if(method.equals("E-Wallet")){
            Payment payment = gson().fromJson(payload, Payment.class);
            payment.setId(payment.getId());
            payment.setE_walletId(payment.getE_walletId());
            payment.setCustomerId(payment.getCustomerId());
            BigDecimal totalAmount = orderService.orderAmountByCustomerId(payment.getCustomerId());
            payment.setAmount(totalAmount);
            payment.setMethod(method);
            payment.setStatus(payment.getStatus());
            payment.setOrders(orderService.getOrdersByCustomerId(payment.getCustomerId()));
            payment.setDate(Date.valueOf(LocalDate.now()));

            if(payment.getE_walletId()!=0){
                Ewallet e = gson().fromJson(payload,Ewallet.class);
                e.setPassword(e.getPassword());
                e.setUsername(e.getUsername());


                EwalletService ewalletService = new EwalletServiceImpl();
                Ewallet eWallet = ewalletService.getEwallet(payment.getE_walletId());
                String n = Encryption.encrypt(e.getUsername());
                String n1 = Encryption.encrypt(e.getPassword());

                if(n.equals(eWallet.getUsername()) && n1.equals(eWallet.getPassword())) {
                    payment.setCard_id(eWallet.getCard_id());
                    paymentService.addPayment(payment);
                    return Response.ok(gson().toJson(payment)).build();
                }else{
                    return Response.status(401).entity("Incorrect E-Wallet details!").build();
                }
            }else{
                throw new Exception("E-wallet id cannot be empty");
            }


        }
        else {
            return Response.status(405).entity(gson().toJson("Payment method not allowed")).build();
        }

    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentsById(@PathParam("id") int id) throws Exception{
        return Response.ok(gson().toJson(paymentService.getPaymentById(id))).build();

    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePaymentById(Payment payment) throws Exception {
        paymentService.deletePayment(payment);
        return Response.ok("Deleted").build();
    }

    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePayment(@PathParam("id") int id, String payload) throws Exception {
        Payment payment = gson().fromJson(payload, Payment.class);
        payment.setId(id);
        payment.setCustomerId(payment.getCustomerId());
        payment.setCard_id(payment.getCard_id());
        payment.setE_walletId(payment.getE_walletId());
        payment.setAmount(payment.getAmount());
        payment.setMethod(payment.getMethod());
        payment.setStatus(payment.getStatus());
        payment.setDate(payment.getDate());
        paymentService.updatePayment(payment);
        return Response.ok(gson().toJson(payment)).build();
    }



}

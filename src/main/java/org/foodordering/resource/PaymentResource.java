package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Payment;
import org.foodordering.service.PaymentService;
import org.foodordering.service.PaymentServiceImpl;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/Payment")
public class PaymentResource extends AbstractResource {
    PaymentService paymentService= new PaymentServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayment() throws Exception {
        List<Payment> payments = new ArrayList<Payment>(paymentService.getAllPayments());
        return Response.ok(gson().toJson(payments)).build();

    }
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(String payload) throws Exception {
        Payment payment = gson().fromJson(payload, Payment.class);
        payment.setId(payment.getId());
        payment.setOrder_id(payment.getOrder_id());
        payment.setAmount(payment.getAmount());
        payment.setStatus(payment.getStatus());
        payment.setDate(payment.getDate());
        paymentService.addPayment(payment);
        return Response.ok(gson().toJson(payment)).build();
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
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePayment(@PathParam("id") int id, String payload) throws Exception {
        Payment payment = gson().fromJson(payload, Payment.class);
        payment.setId(id);
        payment.setOrder_id(payment.getOrder_id());
        payment.setAmount(payment.getAmount());
        payment.setMethod(payment.getMethod());
        payment.setStatus(payment.getStatus());
        payment.setDate(payment.getDate());
        paymentService.updatePayment(payment);
        return Response.ok(gson().toJson(payment)).build();
    }


}

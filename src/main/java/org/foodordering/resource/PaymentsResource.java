package org.foodordering.resource;

import com.google.gson.Gson;
import org.foodordering.domain.Payment;
import org.foodordering.service.Payment_DB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.foodordering.service.Payment_DB.*;


@Path("/payments")
public class PaymentsResource {
    Gson gson = new Gson();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPayment(Payment payment) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        SavePayment(payment);

        return Response.status(Response.Status.OK).entity(payment).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayments() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Payment> payments3 = new ArrayList<>(getAllPayment());
        return Response.status(Response.Status.OK).entity(payments3).build();



    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePayment(Payment payment) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(Payment_DB.deletePayment(payment.getId()))).build();

    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePayment(Payment payment) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(updatePaymentdb(payment))).build();
    }


}

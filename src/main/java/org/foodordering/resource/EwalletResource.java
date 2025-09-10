package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Ewallet;
import org.foodordering.service.EwalletService;
import org.foodordering.service.EwalletServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Path("/E-wallet")
public class EwalletResource extends AbstractResource {
    EwalletService ewalletService =  new EwalletServiceImpl();
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEwallets() throws Exception {
        List<Ewallet> ewallets =new ArrayList<>(ewalletService.getEwallets());
        return Response.ok(gson().toJson(ewallets)).build();
    }
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertEwallet(String payload) throws Exception {
        Ewallet ewallet = gson().fromJson(payload, Ewallet.class);
        ewallet.setId(ewallet.getId());
        ewallet.setUsername(ewallet.getUsername());
        ewallet.setPassword(ewallet.getPassword());
        ewallet.setCard_id(ewallet.getCard_id());
        ewalletService.addEwallet(ewallet);
        return Response.ok(gson().toJson(ewallet)).build();
    }
    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEwallet(@PathParam("id") int id, String payload) throws Exception {
        Ewallet ewallet = gson().fromJson(payload, Ewallet.class);
        ewallet.setId(id);
        ewallet.setUsername(ewallet.getUsername());
        ewallet.setPassword(ewallet.getPassword());
        ewallet.setCard_id(ewallet.getCard_id());
        ewalletService.updateEwallet(ewallet);
        return Response.ok(gson().toJson(ewallet)).build();

    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEwallet(@PathParam("id") int id) throws Exception {
        Ewallet ewallet = ewalletService.getEwallet(id);
        return Response.ok(gson().toJson(ewallet)).build();
    }
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteEwallet(Ewallet ewallet) throws Exception {
        ewalletService.deleteEwallet(ewallet);
        return Response.ok("Deleted").build();
    }
}

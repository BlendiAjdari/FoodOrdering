package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Address;
import org.foodordering.service.AddressService;
import org.foodordering.service.AddressServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Address")
public class AddressResource extends AbstractResource {
    AddressService addressService = new AddressServiceImpl();
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postAddress(String payload) throws Exception {
    Address address = gson().fromJson(payload, Address.class);
    address.setId(address.getId());
    address.setCustomer_id(address.getCustomer_id());
    address.setAddress_line(address.getAddress_line());
    address.setCity(address.getCity());
    address.setZip(address.getZip());
    addressService.addAddress(address);
    return Response.ok(gson().toJson(address)).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddresses() throws Exception {
        List<Address> addresses = addressService.getAddresses();
        return Response.ok(gson().toJson(addresses)).build();
    }
    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putAddress(@PathParam("id") int id, String payload) throws Exception {
        Address address = gson().fromJson(payload, Address.class);
        address.setId(id);
        address.setCustomer_id(address.getCustomer_id());
        address.setAddress_line(address.getAddress_line());
        address.setCity(address.getCity());
        address.setZip(address.getZip());
        addressService.updateAddress(address);
        return Response.ok(gson().toJson(address)).build();
    }
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAddress(Address address) throws Exception {
        addressService.deleteAddress(address);
        return Response.ok("Deleted").build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddress(@PathParam("id") int id) throws Exception {
        Address address=addressService.getAddressById(id);
        return Response.ok(gson().toJson(address)).build();
    }

}

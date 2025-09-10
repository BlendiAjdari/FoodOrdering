package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Customer;
import org.foodordering.domain.Order;
import org.foodordering.service.CustomerService;
import org.foodordering.service.CustomerServiceImpl;
import org.foodordering.service.OrderService;
import org.foodordering.service.OrderServiceImpl;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/Customers")
public class CustomerResource extends AbstractResource {
    CustomerServiceImpl customerService = new CustomerServiceImpl();
    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveCostumer(String payload) throws  Exception {
       Customer customer = gson().fromJson(payload,Customer.class);
       customer.setId(customer.getId());
       customer.setName(customer.getName());
       customer.setEmail(customer.getEmail());
       customer.setPhone(customer.getPhone());
       customer.setPassword(customer.getPassword());
       customerService.addCustomer(customer);
       return Response.ok(gson().toJson(customer)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCostumers() throws Exception {

        return Response.ok(gson().toJson(customerService.getAllCustomers())).build();
    }
    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putCostumer(@PathParam("id") int id, String payload) throws Exception {
        Customer customer = gson().fromJson(payload,Customer.class);
        customer.setId(id);
        customer.setName(customer.getName());
        customer.setEmail(customer.getEmail());
        customer.setPhone(customer.getPhone());
        customerService.updateCustomer(customer);
        return Response.ok(gson().toJson(customer)).build();
    }
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCostumers(Customer customer) throws Exception {
        customerService.deleteCustomer(customer);
        return Response.ok("Deleted").build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCostumersByID(@PathParam("id") int id) throws Exception {
        Customer c = customerService.getCustomerById(id);
        return Response.ok(gson().toJson(c)).build();
    }
    @GET
    @Path("/dashboard/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboardByID(@PathParam("id") int id) throws Exception {
        OrderService orderService = new OrderServiceImpl();
        List<Order>orders=orderService.getOrdersByCustomerId(id);
        return  Response.ok(gson().toJson(orders)).build();
    }

}

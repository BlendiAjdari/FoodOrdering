package Resources;

import org.foodordering.domain.Customer;
import org.foodordering.resource.CustomerResource;
import org.foodordering.service.CustomerService;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class CustomerResourceTest {
    CustomerResource customerResource = new CustomerResource();
    @Test
    void postCustomer() throws Exception {
        String payload = """
                {
                "customer_name":"William",
                "customer_email":"Will@i@am",
                "customer_phone_number":"075 555 835",
                "customer_password":"testestest",
                "id":7
                }
                """;
        Response response = customerResource.saveCostumer(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void getAllCustomers() throws Exception {
        Response response = customerResource.getCostumers();
        System.out.println(response.getEntity());
    }
    @Test
    void getCustomerById() throws Exception {
        int id =7;
        Response response = customerResource.getCostumersByID(id);
        System.out.println(response.getEntity());
    }
    @Test
    void putCustomer() throws Exception {
        int id =7;
        String payload = """
                {
                "customer_name":"William",
                "customer_email":"Will@iam@Sheakspear",
                "customer_phone_number":"075 555 835",
                "customer_password":"testestest",
                "id":7
                }
                """;
        Response response = customerResource.putCostumer(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteCustomer() throws Exception {
        int id =7;
        Customer  customer = new Customer();
        customer.setId(id);
        Response response = customerResource.deleteCostumers(customer);
        System.out.println(response.getEntity());
    }
    @Test
    void getCustomerDashboard() throws Exception {
        int id =5;
        Response response = customerResource.getDashboardByID(id);
        System.out.println(response.getEntity());
    }
}

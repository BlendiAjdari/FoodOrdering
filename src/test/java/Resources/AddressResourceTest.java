package Resources;

import org.foodordering.domain.Address;
import org.foodordering.resource.AddressResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class AddressResourceTest {
    AddressResource addressResource = new AddressResource();
    @Test
    void getAllAddresses()throws Exception {
        Response response =addressResource.getAddresses();
        System.out.println(response.getEntity());

    }
    @Test
    void getAddressById()throws Exception {
        int id=1;
        Response response =addressResource.getAddress(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postAddress()throws Exception {
        String payload = """
                {"c_id":5,
                "address_line":" Bronx 302",
                "city":"New Jersey",
                "zip":997,
                "id":7}
                """;
        Response response =addressResource.postAddress(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateAddress()throws Exception {
        int id=7;
        String payload = """
                {"c_id":5,
                "address_line":"New Road 408",
                "city":"New Jersey",
                "zip":997,
                "id":7}
                """;
        Response response = addressResource.putAddress(id, payload);
        System.out.println(response.getEntity());

    }
    @Test
    void deleteAddress()throws Exception {
        int id=7;
        Address address = new Address();
        address.setId(id);
        Response response =addressResource.deleteAddress(address);
        System.out.println(response.getEntity());
    }
}

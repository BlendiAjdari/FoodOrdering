package Resources;

import org.foodordering.domain.Checkout;
import org.foodordering.domain.OrderItem;
import org.foodordering.resource.CheckoutResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class CheckoutResourceTest {
    CheckoutResource checkoutResource = new CheckoutResource();
    @Test
    void getAllCheckouts() throws Exception {
        Response response = checkoutResource.getAllCheckouts();
        System.out.println(response.getEntity());
    }
    @Test
    void getCheckoutById() throws Exception {
        int id = 1;
        Response response=checkoutResource.getCheckout(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postCheckout() throws Exception {
        String payload= """
                {
                "id":1,
                "c_id":2
                }""";
        Response response = checkoutResource.insertCheckout(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateCheckout() throws Exception {
        int id = 1;
        String payload= """
                {"c_id":1,
                "id":1}""";
        Response response = checkoutResource.updateCheckout(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteCheckout() throws Exception {
        int id = 8;
        Checkout checkout = new Checkout();
        checkout.setId(id);
        Response response = checkoutResource.deleteCheckout(checkout);
        System.out.println(response.getEntity());
    }

}

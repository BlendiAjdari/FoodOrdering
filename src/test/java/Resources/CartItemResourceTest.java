package Resources;

import org.foodordering.domain.CartItem;
import org.foodordering.resource.CartItemResource;
import org.foodordering.resource.PaymentResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;

public class CartItemResourceTest {
    CartItemResource cartItemResource=new CartItemResource();
    @Test
    void getAllCartItems() throws Exception {
        Response response = cartItemResource.getCartItems();
        System.out.println(response.getEntity());
    }
    @Test
    void getCartItemById() throws Exception {
        int id =10;
        Response response = cartItemResource.getCartItem(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postCartItem() throws Exception {
        String payload= """
                {"c_id":5,
                "p_id":3,
                "quantity":1,
                "id":10}
                """;
        Response response = cartItemResource.insertCartItem(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateCartItem() throws Exception {
        int id = 10;
      String payload= """
              {"c_id":5,
              "p_id":4,
              "quantity":2,
              "id":10}""";
      Response response = cartItemResource.updateCartItem(id,payload);
      System.out.println(response.getEntity());
    }
    @Test
    void deleteCartItem() throws Exception {
        int id =10;
        CartItem cartItem=new CartItem();
        cartItem.setId(id);
        Response response = cartItemResource.deleteCartItems(cartItem);
        System.out.println(response.getEntity());
    }
}

package Resources;

import org.foodordering.domain.Cart;
import org.foodordering.resource.CartResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import static org.mockito.Mockito.*;


public class CartResourceTest {
    CartResource cartResource = new CartResource();
    @Test
    void postCart() throws Exception {
        String payload = """
                {
                "c_id":6,
                 "id":6
                }
                """;
        Response response = cartResource.createCart(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void putCart() throws Exception {
        String payload = """
                {
                "c_id":5,
                "created_at":"01:42:10",
                "id":6}
        """;
        int id =6;
        Response response = cartResource.putCart(6,payload);
        System.out.println(response.getEntity());
    }
    @Test
    void getAllCart() throws Exception {
       Response response = cartResource.getCart();
        System.out.println(response.getEntity());
    }
    @Test
    void getCardById() throws Exception {
        int id = 6;
        Response response = cartResource.getCartById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteCart() throws Exception {
        int id = 6;
        Cart cart =  new Cart();
        cart.setId(id);
        Response response = cartResource.deleteCarts(cart);
        System.out.println(response.getEntity());
    }
    @Test
    void getCartToFinalise() throws Exception {
        int id =5;
        UriInfo mockUriInfo = mock(UriInfo.class);
        when(mockUriInfo.getBaseUriBuilder())
                .thenReturn(UriBuilder.fromUri("http://localhost/api/"));

        cartResource.uriInfo = mockUriInfo;

        Response response = cartResource.toCheckout(id);
        System.out.println(response.getEntity());
    }

}

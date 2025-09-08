package Resources;

import org.foodordering.domain.Ewallet;
import org.foodordering.resource.EwalletResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class EwalletResourceTest {
    EwalletResource ewalletResource = new EwalletResource();
    @Test
    void getAllEWallet() throws Exception {
        Response response = ewalletResource.getEwallets();
        System.out.println(response.getEntity());
    }
    @Test
    void getEWalletById() throws Exception {
        int id =1;
        Response response = ewalletResource.getEwallet(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postEWallet()throws Exception {
        String payload ="""
                {"username":"Blendinho",
                "password":"testtest",
                "card_id":9,
                "id":2}
                """;
        Response response = ewalletResource.insertEwallet(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateEWallet()throws Exception {
        int id=2;
        String payload ="""
                {"username":"Blendinho",
                "password":"test2test2",
                "card_id":9,
                "id":2}
        """;
        Response response = ewalletResource.updateEwallet(id,payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteEWalletById()throws Exception {
        int id=2;
        Ewallet ewallet =new Ewallet();
        ewallet.setId(id);
        Response response = ewalletResource.deleteEwallet(ewallet);
        System.out.println(response.getEntity());
    }
}

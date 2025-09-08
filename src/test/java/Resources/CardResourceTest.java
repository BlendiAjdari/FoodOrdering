package Resources;

import org.foodordering.domain.Card;
import org.foodordering.resource.CardResource;
import org.foodordering.resource.CartResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class CardResourceTest {
    CardResource cardResource = new CardResource();
    @Test
    void getAllCards()throws Exception{
        Response response = cardResource.getAllCards();
        System.out.println(response.getEntity());
    }
    @Test
    void getCardById()throws Exception{
        int id =11;
        Response response = cardResource.getCardById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postCard()throws Exception{
        String payload = """
                {"card_number":"0123 0123 0123 0123",
                "card_name":"Test Test",
                "cvv":"433",
                "expiry_date":"01/29",
                "c_id":5,
                "id":11}
                
                """;
        Response response = cardResource.insertCard(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void putCard()throws Exception{
        int id =11;
        String payload = """
                {"card_number":"ŠǓɘ˯΄юԛ׺۫ߚࣾਥ୞಩ෲ཰ჱኄᐩ",
                "card_name":"Test2 Test2",
                "cvv":"ŤǕə",
                "expiry_date":"01/29",
                "c_id":5,
                "id":11}""";
        Response response =cardResource.updateCardById(id,payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteCardById()throws Exception{
        int id =11;
        Card card = new Card();
        card.setId(id);
        Response response = cardResource.deleteCardById(card);
        System.out.println(response.getEntity());
    }
}

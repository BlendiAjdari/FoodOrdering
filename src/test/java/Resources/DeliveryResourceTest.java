package Resources;

import org.foodordering.domain.Delivery;
import org.foodordering.resource.DeliveryResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class DeliveryResourceTest {
    DeliveryResource deliveryResource = new DeliveryResource();
    @Test
    void getAllDelivery() throws Exception {
        Response response = deliveryResource.getDelivery();
        System.out.println(response.getEntity());
    }
    @Test
    void getDeliveryById() throws Exception {
        int id =1;
        Response response = deliveryResource.getDeliveryById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postDelivery() throws Exception {
        String payload = """
                {"id" :2,
                "customer_id":5,
                "courier_id":1,
                "status":"delivering...",
                "pickup_time":"17:00:00",
                "delivery_time":"17:10:00"}
                """;
        Response response = deliveryResource.addDelivery(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateDelivery() throws Exception {
        int id =2;
        String payload= """
                {"customer_id":5,
                "courier_id":1,
                "status":"delivering...",
                "pickup_time":"05:05:00",
                "delivery_time":"05:10:00",
                "id":2}""";
        Response response = deliveryResource.updateDelivery(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteDelivery() throws Exception {
        int id =2;
        Delivery delivery = new Delivery();
        delivery.setId(id);
        Response response =deliveryResource.deleteDelivery(delivery);
        System.out.println(response.getEntity());

    }
}

package Resources;

import org.foodordering.domain.OrderItem;
import org.foodordering.resource.OrderItemResource;
import org.foodordering.resource.OrderResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class OrderItemResourceTest {
    OrderItemResource orderItemResource = new OrderItemResource();
    @Test
    void getAllOrderItems()throws Exception {
        Response response = orderItemResource.getAllOrderItems();
        System.out.println(response.getEntity());
    }
    @Test
    void getOrderItemById()throws Exception {
        int id =3;
        Response response =orderItemResource.getOrderItemById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postOrderItem()throws Exception {
        String payload = """
                {"o_id":6,
                "p_id":3,
                "quantity":1,
                "id":4}""";
        Response response = orderItemResource.insertOrderItems(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void putOrderItem()throws Exception {
        int id =4;
        String payload= """
                {"o_id":6,
                "p_id":3,
                "quantity":4,
                "id":4}""";
        Response response = orderItemResource.updateOrderItems(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteOrderItemById()throws Exception {
        int id =4;
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        Response response = orderItemResource.deleteOrderItems(orderItem);
        System.out.println(response.getEntity());
    }


}

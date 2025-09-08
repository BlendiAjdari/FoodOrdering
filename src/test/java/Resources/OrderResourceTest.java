package Resources;

import org.foodordering.domain.Order;
import org.foodordering.resource.OrderResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class OrderResourceTest {
    OrderResource orderResource = new OrderResource();
    @Test
    void getAllOrders() throws Exception {
        Response response = orderResource.getAllOrders();
        System.out.println(response.getEntity());
    }
    @Test
    void getOrderById()throws Exception {
        int id =36;
        Response response = orderResource.getOrderById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postOrder()throws Exception {
        String payload = """
                {"order_status":"ordered",
                "c_id":5,
                "s_id":6,
                "id":37}""";
        Response response = orderResource.insertOrder(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void putOrder()throws Exception {
        int id =37;
        String payload = """
                {"order_date":"2025-09-01",
                "order_status":"On the way",
                "c_id":5,
                "s_id":6
                ,"id":37}""";
        Response response = orderResource.updateOrder(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteOrderById()throws Exception {
        int id =37;
        Order order = new Order();
        order.setId(id);
        Response response = orderResource.deleteOrder(order);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteOrderByCustomerId()throws Exception {
        int customer_id =6;
        Response response = orderResource.deleteOrdersByCustomerId(customer_id);
        System.out.println(response.getEntity());
    }

}

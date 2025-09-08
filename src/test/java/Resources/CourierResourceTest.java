package Resources;

import org.foodordering.domain.Courier;
import org.foodordering.resource.CourierResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class CourierResourceTest {
    CourierResource courierResource =  new CourierResource();
    @Test
    void getAllCouriers()throws Exception{
        Response response = courierResource.getCourier();
        System.out.println(response.getEntity());
    }
    @Test
    void getCourierById()throws Exception{
        int id = 1;
        Response response = courierResource.getCourierById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void addCourier()throws Exception{
        String payload = """
                {"courier_name":"Henry",
                "courier_current_location":"Tetove 101 xhamia e pashes",
                "delivery_status":"Delivering",
                "id":2}
                """;
        Response response = courierResource.addCourier(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateCourier()throws Exception{
        int id = 2;
        String payload =  """
                {"courier_name":"James",
                "courier_current_location":"Tetove 101 xhamia e pashes",
                "delivery_status":"Delivering",
                "id":2}
                """;
        Response response = courierResource.updateCourier(id,payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteCourier()throws Exception{
        int id = 2;
        Courier courier = new Courier();
        courier.setId(id);
        Response response = courierResource.deleteCourier(courier);
        System.out.println(response.getEntity());
    }
}

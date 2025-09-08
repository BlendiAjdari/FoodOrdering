package Resources;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Store;
import org.foodordering.resource.StoreResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class StoreResourceTest {
 StoreResource storeResource = new StoreResource();
    @Test
    void getAllstores() throws Exception {
        Response response = storeResource.getStores();
        System.out.println(response.getEntity());
    }
    @Test
    void getStoreById() throws Exception {
        int id = 1;
        Response response = storeResource.getStore(id);
        System.out.println(response.getEntity());
    }
    @Test
    void getStoreDashboard() throws Exception {
        int id = 6;
        Response response = storeResource.getStoreDashboard(id);
        System.out.println(response.getEntity());
    }
    @Test
    void saveStore() throws Exception {
        String payload= """
                {
                "store_name":"Grish",
                "store_address":"Tetove Rruga Komuna 101",
                "store_contact_number":"071 355 555",
                "store_delivery_options":"Bike",
                "store_opening_time":"07:00:00",
                "store_closing_time":"24:00:00",
                 "id": "8"}""";
        Response response =storeResource.postStore(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateStore() throws Exception {
        String payload= """
                {
                "store_name":"Grish",
                "store_address":"Tetove Rruga Komuna 101",
                "store_contact_number":"071 355 555",
                "store_delivery_options":"MotorBike",
                "store_opening_time":"07:00:00",
                "store_closing_time":"24:00:00",
                 "id": "8"}""";
        int id =8;
        Response response = storeResource.putStore(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteStore() throws Exception {
        int id = 8;
        Store store = new Store();
        store.setId(id);
        Response response = storeResource.deleteStore(store);
        System.out.println(response.getEntity());
    }
}

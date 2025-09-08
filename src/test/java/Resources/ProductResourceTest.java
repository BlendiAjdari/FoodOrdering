package Resources;

import org.foodordering.domain.Product;
import org.foodordering.resource.ProductResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class ProductResourceTest {
    ProductResource productResource = new ProductResource();
    @Test
    void getAllProducts() throws Exception {
        Response response = productResource.getProducts();
        System.out.println(response.getEntity());
    }
    @Test
    void getProductByName() throws Exception {
        Response response = productResource.getProducts("D");
        System.out.println(response.getEntity());
    }
    @Test
    void getProductById() throws Exception {
        int id =3;
        Response response = productResource.getProductsById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void getProductByCategory() throws Exception {
        String category = "Fast Food";
        Response response = productResource.getProductsByCategory(category);
        System.out.println(response.getEntity());
    }
    @Test
    void postProduct() throws Exception {
        String payload= """
                {"id":5,
                "product_name":"French fries",
                "product_description":"Crispy and Salty",
                "product_price": 40.00,
                "product_stock_quantity":150,
                "s_id":7,
                "ctgr_id":5,
                "product_image":"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.recipetineats.com%2Ffrench-fries%2F&psig=AOvVaw3yd8xubf_zHBaRjkBIvgQp&ust=1756814967351000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCPDmmPLDt48DFQAAAAAdAAAAABAE"
              }
              
                """;
        Response response = productResource.addProduct(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateProduct() throws Exception {
        int id =5;
        String payload= """
                {"id":5,
                "product_name":"French fries",
                "product_description":"Crispy and Salty",
                "product_price": 40.00,
                "product_stock_quantity":100,
                "s_id":7,
                "ctgr_id":5,
                "product_image":"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.recipetineats.com%2Ffrench-fries%2F&psig=AOvVaw3yd8xubf_zHBaRjkBIvgQp&ust=1756814967351000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCPDmmPLDt48DFQAAAAAdAAAAABAE"
              }
              
                """;
        Response response = productResource.updateProduct(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteProduct() throws Exception {
        int id =5;
        Product product = new Product();
        product.setId(id);
        Response response = productResource.deleteProduct(product);
        System.out.println(response.getEntity());
    }
}

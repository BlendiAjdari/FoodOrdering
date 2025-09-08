package Resources;

import org.foodordering.domain.Category;
import org.foodordering.resource.CategoriesResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class CategoryResourceTest {
    CategoriesResource categoryResource = new CategoriesResource();
    @Test
    void getCategories() throws Exception {
        Response response = categoryResource.getCategories();
        System.out.println(response.getEntity());
    }
    @Test
    void getCategoryById() throws Exception {
        int id =1;
        Response response = categoryResource.getCategory(id);
        System.out.println(response.getEntity());
    }
    @Test
    void postCategory() throws Exception {
        String payload = """
                {"id":8,
                "Category_name":"Desserts"
                }""";
        Response response = categoryResource.addCategory(payload);
        System.out.println(response.getEntity());
    }
    @Test
    void updateCategory() throws Exception {
        String payload = """
                {"id":8,
                "Category_name":"Dessert"
                }""";
        int id = 8;
        Response response = categoryResource.updateCategory(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deleteCategory() throws Exception {
        int id = 8;
        Category category = new Category();
        category.setId(id);
        Response response = categoryResource.deleteCategory(category);
        System.out.println(response.getEntity());
    }
}

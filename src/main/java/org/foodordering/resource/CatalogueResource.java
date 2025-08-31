package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Category;
import org.foodordering.domain.Product;
import org.foodordering.domain.Store;
import org.foodordering.service.*;
import org.glassfish.jersey.process.internal.Stages;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/Catalogue")
public class CatalogueResource extends AbstractResource {
    CategoriesService categoryService = new CategoriesServiceImpl();
    ProductService productService = new ProductServiceImpl();
    StoreServiceImpl storeService = new StoreServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCatalogue() throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Store> stores = new ArrayList<>(storeService.getAllStores());

        for (Store s : stores) {
            Map<String, Object> storeWithProducts = new HashMap<>();
            storeWithProducts.put("store", s);
            storeWithProducts.put("products", productService.productsByStoreId(s.getId()));

            result.add(storeWithProducts);
        }

        return Response.ok(gson().toJson(result)).build();
    }
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCatalogue(@QueryParam("name")String name,@QueryParam("category") String category) throws Exception {
        Set<Product> products = new HashSet<>();
        products.addAll(productService.getProductByName(name));
        products.addAll(productService.getProductByCategory(category));
        return Response.ok(gson().toJson(products)).build();
    }
    @GET
    @Path("/Stores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStores() throws Exception {
        List<Store>stores=new ArrayList<>(storeService.getAllStores());
        return Response.ok(stores).build();
    }

    @GET
    @Path("/Stores/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStore(@QueryParam("name")String name) throws Exception {
        StoreServiceImpl storeService=new StoreServiceImpl();
        return Response.ok(storeService.searchStore(name)).build();
    }
    @GET
    @Path("/Categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() throws Exception{
        List<Category> categories = new ArrayList<>(categoryService.getAllCategories());
        return Response.ok(gson().toJson(categories)).build();
    }
    @POST
    @Path("/Categories/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(String payload) throws Exception{
        Category category = gson().fromJson(payload,Category.class);
        category.setId(category.getId());
        category.setName(category.getName());
        categoryService.addCategory(category);
        return Response.ok(category).build();
    }
    @DELETE
    @Path("/Categories")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCategory(Category category) throws Exception{
        categoryService.deleteCategory(category);
        return Response.ok().build();
    }
    @PUT
    @Path("/Categories/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("id") int id ,String payload) throws Exception{
        Category category = gson().fromJson(payload,Category.class);
        category.setId(category.getId());
        category.setName(category.getName());
        categoryService.updateCategory(category);
        return Response.ok(category).build();

    }
    @GET
    @Path("/Categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") int id) throws Exception{
        Category category = categoryService.getCategoryById(id);
        return Response.ok(category).build();
    }
    @GET
    @Path("/Categories/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProductsWithCategory(@QueryParam("category")String category) throws Exception {
        List<Product> products = new ArrayList<>(productService.getProductByCategory(category));
        return Response.ok(gson().toJson(products)).build();
    }

    @GET
    @Path("/Products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() throws Exception{
        List<Product> p = new ArrayList<>(productService.getAllProducts());
        return Response.ok(p).build();
    }
    @POST
    @Path("/Products/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(String payload) throws Exception{
        Product product = gson().fromJson(payload,Product.class);
        product.setId( product.getId());
        product.setName( product.getName());
        product.setDescription( product.getDescription());
        product.setPrice(product.getPrice());
        product.setStock_quantity(product.getStock_quantity());
        product.setStore_id(product.getStore_id());
        product.setStore(storeService.getStoreById((product.getStore_id())));
        product.setCategory_id(product.getCategory_id());
        product.setCategory( categoryService.getCategoryById(product.getCategory_id()));
        productService.addProduct(product);
        return Response.ok(product).build();
    }
    @PUT
    @Path("/Products/{id}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") int id ,String payload) throws Exception{
        Product product = gson().fromJson(payload,Product.class);
        product.setId( product.getId());
        product.setName( product.getName());
        product.setDescription( product.getDescription());
        product.setPrice(product.getPrice());
        product.setStock_quantity(product.getStock_quantity());
        product.setStore_id(product.getStore_id());
        product.setStore(storeService.getStoreById(product.getStore_id()));
        product.setCategory_id(product.getCategory_id());
        product.setCategory( categoryService.getCategoryById(product.getCategory_id()));
        productService.updateProduct(product);
        return Response.ok(product).build();
    }
    @DELETE
    @Path("/Products/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProduct(Product product) throws Exception{
        productService.deleteProduct(product);
        return Response.ok().build();
    }
    @GET
    @Path("/Products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") int id) throws Exception{
        Product product = productService.getProductById(id);
        return Response.ok(product).build();
    }
    @GET
    @Path("/Products/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@QueryParam("name") String name, @QueryParam("category") String category) throws Exception{
        Set<Product> products = new HashSet<>();
            products.addAll(productService.getProductByName(name));
            products.addAll(productService.getProductByCategory(category));
        return Response.ok(gson().toJson(products)).build();
    }




}

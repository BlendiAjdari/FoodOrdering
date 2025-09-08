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
    @Consumes(MediaType.APPLICATION_JSON)
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
        return Response.ok(gson().toJson(storeService.searchStore(name))).build();
    }
    @GET
    @Path("/Categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() throws Exception{
        List<Category> categories = new ArrayList<>(categoryService.getAllCategories());
        return Response.ok(gson().toJson(categories)).build();
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
        return Response.ok(gson().toJson(p)).build();
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

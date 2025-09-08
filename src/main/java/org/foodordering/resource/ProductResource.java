package org.foodordering.resource;

import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Product;
import org.foodordering.service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/Product")
public class ProductResource extends AbstractResource {
    ProductService productService = new ProductServiceImpl();
    StoreService storeService = new StoreServiceImpl();
    CategoriesService categoriesService = new CategoriesServiceImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() throws Exception{
        List<Product> products =new ArrayList<>(productService.getAllProducts());
        return Response.ok(gson().toJson(products)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsById(@PathParam("id") int id) throws Exception{
        Product product = productService.getProductById(id);
        return Response.ok(gson().toJson(product)).build();
    }
    @POST
    @Path("/insert")
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
        product.setCategory( categoriesService.getCategoryById(product.getCategory_id()));
        productService.addProduct(product);
        return Response.ok(gson().toJson(product)).build();
    }
    @PUT
    @Path("/{id}/update")
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
        product.setCategory( categoriesService.getCategoryById(product.getCategory_id()));
        productService.updateProduct(product);
        return Response.ok(gson().toJson(product)).build();
    }
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProduct(Product product) throws Exception{
        productService.deleteProduct(product);
        return Response.ok("Deleted").build();
    }
    @GET
    @Path("/search_N")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@QueryParam("name") String name) throws Exception{
        return Response.ok(gson().toJson(productService.getProductByName(name))).build();
    }
    @GET
    @Path("/search_C")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@QueryParam("category") String category) throws Exception{
        Set<Product> products = productService.getProductByCategory(category);
        return Response.ok(gson().toJson(products)).build();
    }




}

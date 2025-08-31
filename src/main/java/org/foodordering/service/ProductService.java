package org.foodordering.service;

import org.foodordering.domain.OrderItem;
import org.foodordering.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> getAllProducts() throws Exception;
    Product getProductById(int id) throws Exception;
    void addProduct(Product product) throws Exception;
    void updateProduct(Product product) throws Exception;
    void deleteProduct(Product product) throws Exception;

    Set<Product>getProductByCategory(String category) throws Exception;

    void stockChanges(Product product, OrderItem orderitem) throws Exception;
    Set<Product> getProductByName(String name) throws Exception;
    BigDecimal returnProductPrice(Product product) throws Exception;
    List<Product>productsByStoreId(int storeId) throws Exception;
}

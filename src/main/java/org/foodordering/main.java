package org.foodordering;


import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Product;
import org.foodordering.service.ProductService;
import org.foodordering.service.ProductServiceImpl;

import java.util.HashSet;
import java.util.Set;


public class main extends AbstractResource {
    public static void main(String[] args)  throws Exception {
        ProductService productService = new ProductServiceImpl();

        Set<Product> products = new HashSet<>();
        System.out.println(products);
    }
}

package org.foodordering;


import org.foodordering.common.AbstractResource;
import org.foodordering.domain.Card;
import org.foodordering.domain.Encryption;
import org.foodordering.domain.Product;
import org.foodordering.service.*;

import java.util.HashSet;
import java.util.Set;


public class main extends AbstractResource {
    public static void main(String[] args)  throws Exception {
        OrderService orderService = new OrderServiceImpl();
        System.out.println(orderService.orderAmountByCustomerId(5));
    }
}

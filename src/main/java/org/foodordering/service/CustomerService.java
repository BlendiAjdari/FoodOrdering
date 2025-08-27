package org.foodordering.service;

import org.foodordering.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers() throws Exception;
    Customer getCustomerById(int id) throws Exception;
    void addCustomer(Customer customer) throws Exception;
    void updateCustomer(Customer customer) throws Exception;
    void deleteCustomer(Customer customer) throws Exception;
}

package org.foodordering.service;

import org.foodordering.domain.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddresses() throws Exception;
    Address getAddressById(int id) throws Exception;
    void addAddress(Address address) throws Exception;
    void updateAddress(Address address) throws Exception;
    void deleteAddress(Address address) throws Exception;
    Address getAddressByCustomerId(int id) throws Exception;
}

package org.foodordering.service;

import org.foodordering.domain.Store;

import java.util.List;

public interface StoreService {
    void deleteStore(Store store ) throws Exception;
    List<Store> getAllStores() throws Exception;
    void updateStore(Store store)  throws Exception;
    Store getStoreById(int id) throws Exception;
    void addStore(Store store) throws Exception;
    public List<Store> searchStore(String name)throws  Exception;


}

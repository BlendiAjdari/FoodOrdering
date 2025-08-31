package org.foodordering.service;

import org.foodordering.domain.Ewallet;

import java.security.spec.ECField;
import java.util.List;

public interface EwalletService {
    List<Ewallet> getEwallets() throws Exception;
    Ewallet getEwallet(int id) throws Exception;
    void addEwallet(Ewallet e) throws Exception;
    void updateEwallet(Ewallet e) throws Exception;
    void deleteEwallet(Ewallet e) throws Exception;
}

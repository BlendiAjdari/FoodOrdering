package org.foodordering.service;

import org.foodordering.domain.Order;
import org.foodordering.domain.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments() throws Exception;
    Payment getPaymentById(int id) throws Exception;
    void addPayment(Payment payment) throws Exception;
    void updatePayment(Payment payment) throws Exception;
    void deletePayment(Payment payment) throws Exception;
}

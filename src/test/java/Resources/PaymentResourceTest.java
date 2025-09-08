package Resources;

import org.foodordering.domain.Payment;
import org.foodordering.resource.PaymentResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

public class PaymentResourceTest {
    PaymentResource paymentResource =  new PaymentResource();
    @Test
    void getAllPayments() throws Exception {
        Response response = paymentResource.getPayment();
        System.out.println(response.getEntity());
    }
    @Test
    void getPaymentById() throws Exception {
        int id =10;
        Response response = paymentResource.getPaymentsById(id);
        System.out.println(response.getEntity());
    }
    @Test
    void createPayment() throws Exception {
        String method="Card";
        String payload = """
                {"id":10,
                "customer_id":5,
                "card_id":9,
                "payment_status":"going through",
                "card_number":"1212 3434 5656",
                "cvv":"442"
                }
                """;
        Response response = paymentResource.createPayment(method, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void createPaymentCash() throws Exception {
        String method="Cash";
        String payload = """
                {"id":10,
                "customer_id":5,
                "payment_status":"going through"
                }
                """;
        Response response = paymentResource.createPayment(method, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void createPaymentEwallet() throws Exception {
        String method="Ewallet";
        String payload = """
                {"id":10,
                "customer_id":5,
                "e_wallet_id":,
                "payment_status":"going through",
                }
                """;

    }
    @Test
    void updatePayment() throws Exception {
        String payload = """
                {"customer_id":5,
                "payment_amount":566.40,
                "payment_method":"Card",
                "payment_status":"status ok"
                ,"payment_date":"2025-09-01",
                "card_id":9,
                "e_wallet_id":0,
                "id":10}
                """;
        int id = 10;
        Response response = paymentResource.updatePayment(id, payload);
        System.out.println(response.getEntity());
    }
    @Test
    void deletePayment() throws Exception {
        int id =10;
        Payment payment = new Payment();
        payment.setId(id);
        Response response = paymentResource.deletePaymentById(payment);
        System.out.println(response.getEntity());
    }
}

package project;

import project.model.payment.PaymentMethod;
import project.model.product.Product;
import project.model.user.container.Customer;
import project.model.user.container.Owner;
import project.model.user.container.Transaction;
import project.model.user.container.User;
import project.model.user.container.Transaction.TransactionBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.*;

public class TransactionTest {
    private Transaction tester;

    @BeforeEach
    public void setUp() {
        LocalDateTime date = LocalDateTime.of(2025, 5, 15, 10, 35);  

        Product product = new Product("Candies", "Candy", "4444", 4.44);
        double amount = 1;
        double change = 5.0;
        int quantity = 1;
        PaymentMethod paymentMethod = PaymentMethod.CASH;
        User user = new Customer();
        String reason = "Yes";


        tester = new Transaction(date, product, amount, change, quantity, paymentMethod, user, reason);
    }

    @AfterEach
    public void tearDown() {
        tester = null;
    }

    @Test
    public void getterSetters() {
        assertTrue(tester.date() instanceof LocalDateTime);

        LocalDateTime date = LocalDateTime.of(2025, 5, 15, 10, 35);  
        assertEquals(date, tester.date());

        String name = "Candy";
        String category = "Candies";
        String code = "4444";
        double price = 4.44; 
        
        assertEquals(name, tester.product().name());
        assertEquals(code, tester.product().code());
        assertEquals(category, tester.product().category());
        assertEquals(price, tester.product().price());

        assertEquals(1, tester.amount());
        assertEquals(5.0, tester.change());
        assertEquals(1, tester.quantity());
        assertEquals(PaymentMethod.CASH, tester.paymentMethod());
        assertTrue(tester.user() instanceof Customer);  
        assertEquals("Yes", tester.cancellationReason());
        assertEquals(true, tester.isCancelled());
        
        LocalDateTime date2 = LocalDateTime.of(9999, 5, 15, 10, 35);  
        tester.setDate(date2);
        assertEquals(date2, tester.date());

        Product product2 = new Product("Chips", "BURGERMAN", "9999", 4.20);
        tester.setProduct(product2);
        assertEquals(product2, tester.product());

        tester.setAmount(50.0);
        assertEquals(50.0, tester.amount());

        tester.setQuantity(50);
        assertEquals(50, tester.quantity());
        
        tester.setChange(50.0);
        assertEquals(50.0, tester.change());

        tester.setPaymentMethod(PaymentMethod.CARD);
        assertEquals(PaymentMethod.CARD, tester.paymentMethod());

        User user2 = new Owner();
        tester.setUser(user2);
        assertEquals(user2, tester.user());

        String reason2 = "The mitochondria is not the powerhouse of the cell!";
        tester.setCancellationReason(reason2);
        assertEquals(reason2, tester.cancellationReason());  
    }

    @Test
    public void builder() {
        TransactionBuilder builder = new TransactionBuilder();

        LocalDateTime date = LocalDateTime.of(9999, 5, 15, 10, 35);  
        builder.setDate(date);

        Product product = new Product("Chips", "BURGERMAN", "9999", 4.20);
        builder.setProduct(product);

        builder.setAmount(5.0);
        builder.setChange(5.0);
        builder.setQuantity(1);
        builder.setPaymentMethod(PaymentMethod.CASH);
        
        User user = new Customer();
        builder.setUser(user);
        
        String reason = "BUT WHAT IF I TOLD YOU";
        builder.setCancellationReason(reason);

        Transaction t = builder.build();
        assertEquals(product, t.product());
        assertEquals(5.0, t.amount());
        assertEquals(5.0, t.change());
        assertEquals(1, t.quantity());
        assertEquals(PaymentMethod.CASH, t.paymentMethod());
        assertEquals(user, t.user());
        assertEquals(reason, t.cancellationReason());

        String reason2 = null;
        t.setCancellationReason(reason2);
        assertEquals(reason2, t.cancellationReason());
    }
}

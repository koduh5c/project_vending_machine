package project;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.VendingMachine;
import project.model.payment.Card;
import project.model.user.UserType;
import project.model.user.container.Transaction;
import project.model.user.container.User;

public class VendingMachineTest {
    // private UserManager fixture;
    private VendingMachine fixture;

    @BeforeEach
    public void setUp() {
        fixture = new VendingMachine();
    }

    @AfterEach
    public void tearDown() {
        fixture = null;
    }

     @Test
    public void testAnonymousUserAttributes() {
        assertTrue(fixture.loginAsAnonymous());
        assertNotNull(fixture.currentUser());
        assertEquals(UserType.ANONYMOUS, fixture.currentUser().userType());
        // assertNull(fixture.currentUser().myManager());
    }

/*     @Test
    public void testAnonymousUserListMethod() {
        assertTrue(fixture.loginAsAnonymous());
        User anonymousUser = fixture.currentUser();
        List<Transaction> tList = IntStream.range(0, 15).mapToObj(i -> new Transaction.TransactionBuilder().setQuantity(i).build()).collect(Collectors.toList());
        tList.forEach(t -> anonymousUser.addTransaction(t));
        Collections.reverse(tList);
        List<String> expected = tList.subList(0, 10).stream().map(Transaction::toString).collect(Collectors.toList());
        List<String> actual = anonymousUser.list();
        assertEquals(expected, actual);
    } */

    // @Test
    // public void testLoginWithValidCredentialCombinations() {
    //     String validUsername = "charles123";
    //     String validPassword = "abc40691";
    //     User u = fixture.getUser(validUsername, validPassword);
    //     assertEquals("charles123", u.username());
    //     assertEquals("abc40691", u.password());
    // }

    // @Test
    // public void testLoginWithInvalidCredentialCombinations() {
    //     String validUsername = "charles123";
    //     String validPassword = "abc40691";
    //     String invalidUsername = "invalidUsername";
    //     String invalidPassword = "invalidPassword";
    //     assertNull(fixture.getUser(invalidUsername, invalidPassword));
    //     assertNull(fixture.getUser(validUsername, invalidPassword));
    //     assertNull(fixture.getUser(invalidUsername, validPassword));
    // }

    // @Test
    // public void testCreateAccount() {
    //     String existingUsername = "charles123";
    //     String notExistingUsername = "notExistingUsername";
    //     String somePassword = "somePassword";
    //     assertNull(fixture.createAccount(existingUsername, somePassword, UserType.CUSTOMER));

    //     User newUser = fixture.createAccount(notExistingUsername, somePassword, UserType.CUSTOMER);
    //     assertEquals(newUser, fixture.getUser(notExistingUsername, somePassword));
    // }

    // @Test
    // public void testCashier() {
    //     String username = "vincent123";
    //     String password = "abc59141";
    //     User cashier = fixture.getUser(username, password);
    //     cashier.injectPaymentManager(new PaymentManager());
    //     cashier.injectProductManager(new ProductManager());
    //     cashier.injectUserManager(fixture);
    //     assertEquals(Cashier.class, cashier.getClass());
    //     assertEquals(PaymentManager.class, cashier.myManager().getClass());
    //     System.out.println(cashier.list());
    // }

    @Test
    public void validLogin() {
        boolean expected = true;

        //customer
        String user = "charles123";
        String pass = "abc40691";
        boolean actual = fixture.loginAsUser(user, pass);
        assertEquals(expected, actual);

        //owner
        user = "ruth123";
        pass = "abc55134";
        actual = fixture.loginAsUser(user, pass);
        assertEquals(expected, actual);

        //seller
        user = "kasey123";
        pass = "abc60146";
        actual = fixture.loginAsUser(user, pass);
        assertEquals(expected, actual);
    }

    @Test
    public void invalidLogin() {
        boolean expected = false;

        String user = "INVALID";
        String pass = "INVALIDPASS";
        boolean actual = fixture.loginAsUser(user, pass);

        assertEquals(expected, actual);
    }

    @Test
    public void validCreateAccount() {
        boolean expected = true;

        String user = "NEW_USER";
        String pass = "PASS";
        String type = "OWNER";
        boolean actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual);

        user = "NEW_USER2";
        pass = "PASS";
        type = "owner";
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual);

        user = "NEW_USER3";
        pass = "PASS";
        type = "cashier";
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual);

        user = "NEW_USER4";
        pass = "PASS";
        type = "seller";
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual);

        user = "NEW_USER5";
        pass = "PASS";
        type = "customer";
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual);
    }

    @Test
    public void invalidCreateAccount() {
        boolean expected = false;

        //user already exists
        String user = "vincent123";
        String pass = "abc59141";
        String type = "cashier";
        boolean actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual);

        //invalid fields
        user = "NEW_USER";
        pass = null;
        type = "cashier";
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual, "pass null");

        user = null;
        pass = null;
        type = "cashier";
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual, "user null pass null");

        user = null;
        pass = null;
        type = null;
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual, "all null");

        user = "test";
        pass = "pass";
        type = null;
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual, "type null");

        user = "test";
        pass = "pass";
        type = "invalid user type";
        actual = fixture.createAccount(user, pass, type);
        assertEquals(expected, actual, "invalid user type");

    }

    @Test
    public void validRemoveAccount() {
        boolean actual = fixture.removeAccount("vincent123");
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void invalidRemoveAccount() {
        boolean actual = fixture.removeAccount("DOESNTEXIST");
        boolean expected = false;
        assertEquals(expected, actual);

        actual = fixture.removeAccount(null);
        expected = false;
        assertEquals(expected, actual, "remove account is null");
    }

    @Test
    public void validPay() {
        double cost = 50.0;
        Map<Double, Integer> paidCash = null;
        Card card = new Card("Kasey", "60146");

        Map<Double, Integer> actual = fixture.pay(cost, paidCash, card);
        Map<Double, Integer> expected = new HashMap<>();
        
        assertEquals(expected.size(), actual.size());
        
        //paying by card
        cost = 50.0;
        paidCash = null;
        card = new Card("Kasey", "60146");

        actual = fixture.pay(cost, paidCash, card);
        expected = new HashMap<>();
        
        assertEquals(expected, actual, "paying by card");

        //paying by cash
        cost = 50.0;
        paidCash = new HashMap<>();
        paidCash.put(100.0, 1);
        paidCash.put(50.0, 1);
        card = null;

        actual = fixture.pay(cost, paidCash, card);
        int actualNum = actual.get(100.0);
        int expectedNum = 1;
        
        assertEquals(expectedNum, actualNum);

        //paying by cash 2
        cost = 50.0;
        paidCash = new HashMap<>();
        paidCash.put(100.0, 1);
        card = null;

        actual = fixture.pay(cost, paidCash, card);
        actualNum = actual.get(50.0);
        expectedNum = 1;
        
        assertEquals(expectedNum, actualNum);
    }

    @Test
    public void invalidPay() {
        double cost = 0.0;
        Map<Double, Integer> paidCash = null;
        Card card = new Card("Kasey", "60146");

        Map<Double, Integer> actual = fixture.pay(cost, paidCash, card);
        Map<Double, Integer> expected = null;
        
        assertEquals(expected, actual, "cost is 0.0");
    }

    @Test 
    public void validCalculateChange() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(50.0, 1);
        input.put(20.0, 1);
        input.put(5.0, 1);

        double expected = 75.0;
        double actual = fixture.calculateChange(input);
        assertEquals(expected, actual);
    }

    @Test
    public void invalidCalculateChange() {
        Map<Double, Integer> input = null;

        double expected = 0.0;
        double actual = fixture.calculateChange(input);
        assertEquals(expected, actual, "input is null");

        input = new HashMap<>();
        input.put(50.0, 1);
        input.put(20.0, 1);
        input.put(5.0, -5);
        input.put(-10.0, 1);

        expected = 70.0;
        actual = fixture.calculateChange(input);
        assertEquals(expected, actual);
    }

    

    
}

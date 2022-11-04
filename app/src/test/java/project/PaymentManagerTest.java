package project;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.payment.Card;
import project.model.payment.CardManager;
import project.model.payment.CashManager;
import project.model.payment.PaymentManager;

import java.util.*;

public class PaymentManagerTest {
    private static PaymentManager tester;

    @BeforeEach
    public void setUp() {
        tester = new PaymentManager();
    }

    @AfterAll
    public static void tearDown() {
        tester = null;
    }  

    @Test
    public void getCard() {
        String cName = "NA";
        String cNum = "NA";
        
        Card actual = tester.getCard(cName, cNum);
        Card expected = null;
        assertEquals(expected, actual);

        tester.addCard(cName, cNum);
        actual = tester.getCard(cName, cNum);
        
        assertEquals(cName, actual.getName());
        assertEquals(cNum, actual.getNumber());

        cName = "Kasey";
        cNum = "60146";
        actual = tester.getCard(cName, cNum);
        assertEquals(cName, actual.getName());
        assertEquals(cNum, actual.getNumber());
    }

    @Test
    public void invalidPayCash() {
        Map<Double, Integer> paidNotes = new HashMap<>();
        paidNotes.put(10.0, -5);
        double total = 9999999.00;

        Map<Double, Integer> actual = tester.payCash(paidNotes, total);
        assertEquals(null, actual);

        Map<Double, Integer> paidNotes2 = new HashMap<>();
        paidNotes2.put(5.0, 1);
        total = 100.0;
        actual = tester.payCash(paidNotes2, total);
        assertEquals(null, actual);


    }

    @Test 
    public void invalidPayCard() {
        Card card = new Card("NO", "NO");
        boolean actual = tester.payCard(card); 
        assertEquals(false, actual);

        Card card2 = null;
        actual = tester.payCard(card2); 
        assertEquals(false, actual);
    }

    @Test
    public void payCard() {
        Card card2 = new Card("Sergio", "42689");
        boolean actual = tester.payCard(card2); 
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void getters() {
        assertTrue(tester.cardManager() instanceof CardManager); 
        assertTrue(tester.cashManager() instanceof CashManager); 
        ;
    }
}

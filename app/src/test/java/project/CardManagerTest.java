package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.payment.CardManager;
import project.model.payment.Card;
import java.util.*;

public class CardManagerTest {
    private static CardManager tester;

    @BeforeEach
    public void setUp() {
        tester = new CardManager();
    }

    @AfterAll
    public static void tearDown() {
        tester = null;
    }

    @Test
    public void lenCardDB() {
        int expected = 50;

        ArrayList<Card> db = tester.db();
        int actual = db.size();

        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsStringsNormal() {
        String name = "Maxine";
        String number = "34402";

        Card actual = tester.lookupCard(name, number);

        String actualName = actual.getName();
        String actualNumber = actual.getNumber();

        assertEquals(name, actualName);
        assertEquals(number, actualNumber);
    }

    @Test
    public void cardExistsStringsNotExist() {
        String name = "Does not Exist";
        String number = "34402";

        Card actual = tester.lookupCard(name, number);

        Card expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsStringsNotExist2() {
        String name = "Maxine";
        String number = "000000000";

        Card actual = tester.lookupCard(name, number);

        Card expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsStringsNotNum() {
        String name = "Maxine";
        String number = "NOTANUM";

        Card actual = tester.lookupCard(name, number);

        Card expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsStringsNull() {
        String name = null;
        String number = "000000000";

        Card actual = tester.lookupCard(name, number);

        Card expected = null;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsStringsNull2() {
        String name = "Maxine";
        String number = null;

        Card actual = tester.lookupCard(name, number);

        Card expected = null;
        assertEquals(expected, actual);
    }

    //```````````````````````
    @Test
    public void cardExistsCardNormal() {
        String name = "Maxine";
        String number = "34402";
        Card card = new Card(name, number);

        boolean actual = tester.cardExists(card);

        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsCardNotExist() {
        String name = "Does not Exist";
        String number = "34402";
        Card card = new Card(name, number);

        boolean actual = tester.cardExists(card);

        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsCardNotExist2() {
        String name = "Maxine";
        String number = "000000000";
        Card card = new Card(name, number);

        boolean actual = tester.cardExists(card);

        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsCardNotNum() {
        String name = "Maxine";
        String number = "NOTANUM";
        Card card = new Card(name, number);

        boolean actual = tester.cardExists(card);

        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsCardNull() {
        String name = null;
        String number = "000000000";
        Card card = new Card(name, number);

        boolean actual = tester.cardExists(card);

        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsCardNull2() {
        String name = "Maxine";
        String number = null;
        Card card = new Card(name, number);

        boolean actual = tester.cardExists(card);

        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void cardExistsCardNull3() {
        Card card = null;

        boolean actual = tester.cardExists(card);

        boolean expected = false;
        assertEquals(expected, actual);
    }
}

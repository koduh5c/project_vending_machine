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

import project.model.payment.Card;
import java.util.*;

public class CardTest {
    private static Card tester;

    @BeforeEach
    public void setUp() {
        tester = new Card("Tester", "1234");
    }

    @AfterAll
    public static void tearDown() {
        tester = null;
    }

    @Test
    public void getDetails() {
        String name = tester.getName();
        String number = tester.getNumber();

        String expected = "Tester";
        String expectedNum = "1234";

        assertEquals(expected, name);
        assertEquals(expectedNum, number);
    }

}

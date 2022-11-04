package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.payment.CashManager;
import java.util.*;

public class CashManagerTest {
    private static CashManager tester;

    @BeforeEach
    public void setUp() {
        tester = new CashManager();
    }

    @AfterAll
    public static void tearDown() {
        tester = null;
    }

    @Test void initRegisterNormal() {
        int expectedAmount = 2;
        boolean actual = tester.initRegister(expectedAmount);
        boolean expected = true;

        assertEquals(expected, actual);

        Map<Double, Integer> notes = tester.register();

        for(Map.Entry<Double, Integer> note: notes.entrySet()) {
            int actualAmount = note.getValue();
            assertEquals(expectedAmount, actualAmount);
        }
    }

    @Test
    public void initRegisterZero() {
        boolean actual = tester.initRegister(-1);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void paidEnoughNormal() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1);
        double inputCost = 10;

        boolean actual = tester.paidEnough(input, inputCost);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void paidEnoughNotEnough() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 0);
        double inputCost = 10;

        boolean actual = tester.paidEnough(input, inputCost);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void paidEnoughNullNotes() {
        Map<Double, Integer> input = null;
        double inputCost = 10;

        boolean actual = tester.paidEnough(input, inputCost);
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void paidEnoughProductIsFree() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 0);
        double inputCost = 0;

        boolean actual = tester.paidEnough(input, inputCost);
        boolean expected = true;
        assertEquals(expected, actual);
    }


    @Test
    public void calculateChangeNoInput() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 0);
        double cost = 5.0;

        double actual = tester.calculateChange(input, cost);
        double expected = -1.0;
        assertEquals(expected, actual);
    }

    @Test
    public void calculateChangeNullNotes() {
        Map<Double, Integer> input = null;
        double cost = 5.0;

        double actual = tester.calculateChange(input, cost);
        double expected = -1.0;
        assertEquals(expected, actual);
    }

    @Test
    public void calculateChangeCostFree() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1);
        double cost = 0.0;

        double actual = tester.calculateChange(input, cost);
        double expected = 0.0;
        assertEquals(expected, actual);
    }

    @Test
    public void calculateChangeNormal() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1);
        double cost = 5.0;

        double actual = tester.calculateChange(input, cost);
        double expected = 5.0;
        assertEquals(expected, actual);
    }

    @Test
    public void validNoteAmountNormal() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1); 

        boolean actual = tester.validNotesAmount(input);
        boolean expected = true;
        assertEquals(expected, actual);

        Map<Double, Integer> input2 = new HashMap<>();
        input.put(10.0, 0); 
        actual = tester.validNotesAmount(input2);
        expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void invalidNoteAmount() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, -5); 
        boolean actual = tester.validNotesAmount(input);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void invalidNoteAmount2() {
        Map<Double, Integer> input = null;
        boolean actual = tester.validNotesAmount(input);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void checkSufficientChange0Change() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1);

        boolean actual = tester.checkSufficientChange(input, 0.0);
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void checkSufficientChangeNullMap() {
        Map<Double, Integer> input = null;

        boolean actual = tester.checkSufficientChange(input, 0.0);
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void checkSufficientChangeNotEnough() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1);

        boolean actual = tester.checkSufficientChange(input, 1000000.0);
        boolean expected = false;

        assertEquals(expected, actual);
    }

/*     @Test
    public void checkSufficientChangeNo10s() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 0);

        Map<Double, Integer> subtractMap = new HashMap<>();
        input.put(10.0, 5);
        tester.subtractNotes(subtractMap);

        boolean actual = tester.checkSufficientChange(input, 10.0);
        boolean expected = true;

        assertEquals(expected, actual);

        Map<Double, Integer> returned = tester.returnChange(input, 10.0);
        int actual10s = returned.get(10.0);

        int actual5s = returned.get(5.0);
        int expected5s = 2;

        //assertThrows(, actual10s);
        assertEquals(expected5s, actual5s);
    } */

    @Test
    public void subtractNotesNormal() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1);

        tester.subtractNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    public void subtractNotesNormal2() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 5);

        tester.subtractNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void subtractNotesNull() {
        Map<Double, Integer> input = null;

        tester.subtractNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 5;
        assertEquals(expected, actual);
    }

    //AMONGUS SUS
    @Test
    public void subtractNotesNegative() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, -5);

        tester.subtractNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    public void subtractNotesLessThanZero() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 6);

        tester.subtractNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void addNotesTest() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, 1);

        tester.addNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    public void addNotesNull() {
        Map<Double, Integer> input = null;

        tester.addNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 5;
        assertEquals(expected, actual);
    }
    
    //AMONGUS SUS
    @Test
    public void addNotesNegative() {
        Map<Double, Integer> input = new HashMap<>();
        input.put(10.0, -5);

        tester.addNotes(input);

        int actual = tester.register().get(10.0);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void situation() {

        Map<Double, Integer> input = new HashMap<>();
        input.put(5.0, 1);
        input.put(0.05, 1);

        double change = tester.calculateChange(input, 5.0);
        double expected = 0.05;
        assertEquals(expected, change);

        Map<Double, Integer> result = tester.returnChange(input, 0.05);

        int amount5c = result.get(0.05);
        assertEquals(1, amount5c);

        /* int amount5 = result.get(5.0);
        assertEquals(0, amount5); */

        Map<Double, Integer> result2 = tester.returnChange(input, 0.05);

        amount5c = result2.get(0.05);
        assertEquals(1, amount5c);

/*         int amount5 = result2.get(5.0);
        assertEquals(0, amount5);  */
        
    }

    @Test
    public void setNotes() {
        Map<Double, Integer> set = new HashMap<>();
        set.put(100.0, 50);
        tester.setNotes(set);

        int actual = tester.getDenomAmount(100.0);
        assertEquals(50.0, actual);

        Map<Double, Integer> set2 = new HashMap<>();
        set.put(100.0, -5);
        tester.setNotes(set2);
        actual = tester.getDenomAmount(100.0);
        assertEquals(50, actual);

        Map<Double, Integer> set3 = null;
        tester.setNotes(set3);
        actual = tester.getDenomAmount(100.0);
        assertEquals(50, actual);

        tester.setNotes(50.0, 50);
        actual = tester.getDenomAmount(50.0);
        assertEquals(50, actual);

        tester.setNotes(-50.0, 50);
        actual = tester.getDenomAmount(-50.0);
        assertEquals(0, actual);

        tester.setNotes(86.0, 1);
        actual = tester.getDenomAmount(86.0);
        assertEquals(0, actual);

        tester.setNotes(50.0, -50);
        actual = tester.getDenomAmount(50.0);
        assertEquals(0, actual);

    }

}
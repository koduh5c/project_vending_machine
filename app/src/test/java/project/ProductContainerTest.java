package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;

import project.model.product.Product;
import project.model.product.ProductManager;
import project.model.product.ProductContainer;

public class ProductContainerTest {
    private ProductContainer tester;
    private Product test;

    @BeforeEach
    public void setUp() {
        test = new Product("Drink","cola","1000",3.5);
        tester = new ProductContainer(test,5);
    }

    @AfterEach
    public void tearDown() {
        tester = null;
    }

    @Test
    public void productContainerTest(){
        ProductContainer tester = new ProductContainer(test,6);
    }

    @Test
    public void productTest(){
        assertEquals(test,this.tester.product());
    }

    @Test
    public void quantityLeftTest(){
        assertEquals(5,this.tester.quantityLeft());
    }

    @Test
    public void quantitySoldTest(){
        assertEquals(0,this.tester.quantitySold());
    }

    @Test
    public void setQuantityTest(){
        this.tester.setQuantity(0);
        assertEquals(0, this.tester.quantitySold());

        this.tester.setQuantity(0);
        assertEquals(0, this.tester.quantitySold());
    }

    @Test
    public void setQuantitySold() {
        this.tester.setQuantity(10);
        int actual = this.tester.quantitySold();
        int expected = 0;

        assertEquals(expected, actual);

        this.tester.setQuantitySold(5);
        actual = this.tester.quantitySold();
        assertEquals(5, actual);

        test = new Product("Drink","cola","1000",3.5);
        tester = new ProductContainer(test,5);
    }

    @Test
    public void setQuantityLeft() {
        this.tester.setQuantityLeft(5);
        int actual = this.tester.quantityLeft();

        assertEquals(5, actual);
    }


    // @Test
    // public void sellTest(){
    //     tester.setQuantity(-1);
    //     boolean result1 = tester.sell(1);
    //     assertEquals(false,result1);
    //     tester.setQuantity(2);
    //     boolean result2 = tester.sell(3);
    //     assertEquals(false,result2);
    //     tester.setQuantity(3);
    //     boolean result3 = tester.sell(2);
    //     assertEquals(true,result3);
    // }

}

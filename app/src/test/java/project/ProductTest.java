package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import project.model.product.Product;
import project.model.product.ProductManager;
import project.model.product.ProductContainer;


public class ProductTest {
    private Product tester;

    @BeforeEach
    public void setUp() {
        tester = new Product("Drink","cola","1000",3.5);
    }

    @AfterEach
    public void tearDown() {
        tester = null;
    }

    @Test
    public void productTest(){
        Product test = new Product("Drink","water","1001",4.5);
    }

    @Test
    public void setCategoryTest(){
        this.tester.setCategory("Candy");
        assertEquals("Candy",this.tester.category());
    }

    @Test
    public void setNameTest(){
        this.tester.setName("test");
        assertEquals("test",this.tester.name());

        tester.setName(null);
        assertEquals(null, tester.name());
    }

    @Test
    public void setCodeTest(){
        this.tester.setCode("1002");
        assertEquals("1002",this.tester.code());
    }

    @Test
    public void setPriceTest(){
        this.tester.setPrice(6.0);
        assertEquals(6.0,this.tester.price());

        tester.setPrice(-5);
        assertEquals(0, this.tester.price());
    }

    @Test
    public void categoryTest(){
        assertEquals("Drink",this.tester.category());
    }

    @Test
    public void nameTest(){
        assertEquals("cola",this.tester.name());

    }

    @Test
    public void codeTest(){
        assertEquals("1000",this.tester.code());
    }

    @Test
    public void priceTest(){
        assertEquals(3.5,this.tester.price());
    }
}

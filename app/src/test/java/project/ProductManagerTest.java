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

public class ProductManagerTest {
    private ProductContainer tester;
    private Product test;
    private ProductManager manger;

    @BeforeEach
    public void setUp() {
        manger = new ProductManager();
    }

    @AfterEach
    public void tearDown() {
        tester = null;
    }

    @Test
    public void loadDBTest(){
        manger.db();
        List<ProductContainer> db = manger.db();
        assertEquals(16,db.size());
    }

    @Test
    public void lookup() {
        ProductContainer sprite1 = manger.lookupProductContainer("Sprite");
        ProductContainer sprite2 = manger.lookupProductContainer("1002");

        assertEquals(sprite1, sprite2);
        assertEquals(7, sprite1.quantityLeft());
        assertEquals(0, sprite1.quantitySold());
    }

    @Test
    public void invalidLookup() {
        ProductContainer invalid = manger.lookupProductContainer("SpriteMixedWithCoke");

        assertEquals(null, invalid);
    }



}
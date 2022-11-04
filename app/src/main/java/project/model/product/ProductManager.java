package project.model.product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import project.model.Manager;

public class ProductManager implements Manager {
    private final int initialQuantity = 7;
    private final String dbDir = getClass().getResource("/product_db.json").getPath();
    private List<ProductContainer> db;

    public ProductManager() {
        loadDB();
    }

    private boolean loadDB() {
        db = ProductDBParser.loadDB(dbDir).stream()
                .map(e -> new ProductContainer(e, initialQuantity))
                .collect(Collectors.toList());
        return db != null;
    }

    public List<ProductContainer> db() {
        return db;
    }

    public boolean codeExists(String code) {
        return db.stream().anyMatch(e -> e.product().code().equals(code));
    }

    /***
     * 
     * @param productName
     * @return a ProductContainer matching the name or code else return null
     */
    public ProductContainer lookupProductContainer(String productNameOrCode) {
        List<ProductContainer> res = db.stream()
                .filter(pc -> pc.product().name().equalsIgnoreCase(productNameOrCode) || pc.product().code().equalsIgnoreCase(productNameOrCode))
                .collect(Collectors.toList());
        if (res == null || res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    public ProductContainer sell(String productName, int quantity, double paidAmount) {
        ProductContainer res = lookupProductContainer(productName);
        if (res == null || res.quantityLeft() < quantity || paidAmount < res.product().price() * quantity) {
            return null;
        }
        res.setQuantityLeft(res.quantityLeft() - quantity);
        res.setQuantitySold(res.quantitySold() + quantity);
        return res;
    }

    public Map<String, List<ProductContainer>> categoriseDB() {
        return db.stream().collect(Collectors.groupingBy(e -> e.product().category()));
    }
}

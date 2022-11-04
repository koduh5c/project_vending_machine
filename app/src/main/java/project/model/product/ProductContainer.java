package project.model.product;

public class ProductContainer {
    private final int quantityLimit = 15;
    private final Product product;
    private int quantityLeft;
    private int quantitySold;

    public ProductContainer(Product product, int quantityLeft) {
        this.product = product;
        this.quantityLeft = quantityLeft;
        this.quantitySold = 0;
    }

    public int quantityLimit() {
        return quantityLimit;
    }

    public Product product() {
        return product;
    }

    public int quantityLeft() {
        return quantityLeft;
    }

    public int quantitySold() {
        return quantitySold;
    }

    public void setQuantity(int quantity) {
        quantityLeft = quantity;
        quantitySold = 0;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    @Override
    public String toString() {
        return "ProductContainer [product=" + product + ", quantityLeft=" + quantityLeft + ", quantitySold="
                + quantitySold + "]";
    }
    
}

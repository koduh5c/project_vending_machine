package project.model.product;

public class Product {
    private String category;
    private String name;
    private String code;
    private double price;

    public Product(String category, String name, String code, double price) {
        this.category = category;
        this.name = name;
        this.code = code;
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        if (name == null) {
            this.name = null;
        } else{
            this.name = name;
        }
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(double price) {
        if (price < 0.0) {
            this.price = 0.0;
        } else{
            this.price = price;
        }
    }

    public String category() {
        return category;
    }

    public String name() {
        return name;
    }

    public String code() {
        return code;
    }

    public double price() {
        return price;
    }

    @Override
    public String toString() {
        return "Product [category=" + category + ", name=" + name + ", code=" + code + ", price=" + price + "]";
    }
}

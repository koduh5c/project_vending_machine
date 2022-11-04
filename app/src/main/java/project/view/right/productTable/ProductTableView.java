package project.view.right.productTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import project.model.product.ProductContainer;
import project.view.UserInterface;
import project.view.template.RightViewTemplate;

public class ProductTableView extends RightViewTemplate {
    private Map<String, ProductTable> productTableMap = new HashMap<>();
    public ProductTableView(UserInterface userInterface) {
        super(userInterface);
        createTables();
        addTables();
        header.setText("Products");
    }

    private void createTables() {
        // create Tables based on categories
        Map<String, List<ProductContainer>> productContainerMap = userInterface.model().categorisedProducts();
        for (String key : productContainerMap.keySet()) {
            productTableMap.put(key, new ProductTable(productContainerMap.get(key)));
        }
    }

    private void addTables() {
        for (String key : productTableMap.keySet()) {
            Label label = new Label(key);
            label.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
            getChildren().addAll(label, productTableMap.get(key));
        }
    }


}

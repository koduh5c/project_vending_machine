package project.view.left.modifier;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import project.model.product.ProductContainer;
import project.view.UserInterface;
import project.view.right.productTable.ProductTableView;
import project.view.template.LeftViewTemplate;

public class ProductModifierView {
    private final UserInterface userInterface;

    private LeftViewTemplate choosingProductView;
    private ProductContainer pc;
    private String prevPCString;

    private LeftViewTemplate modifyingProductView;
    // private TextField category;
    private TextField name;
    private TextField code;
    private TextField price;
    private TextField quantity;

    public ProductModifierView(UserInterface userInterface) {
        this.userInterface = userInterface;

        initChoosingProductView();
        initModifyingProductView();
    }

    private void initChoosingProductView() {
        // List<ProductContainer> ls =
        // userInterface.model().currentUser().productManager().db();
        // userInterface.printToConsole(ls.stream().map(ProductContainer::toString).collect(Collectors.joining("\n")));
        userInterface.printToConsole("Please use the Product Table on the right ---->>>>>>");
        choosingProductView = new LeftViewTemplate(userInterface);
        choosingProductView.setHeader("Choose the product you wish to modify");
        TextField input = new TextField();
        Button modify = new Button("Modify");
        modify.setOnAction(e -> {
            ProductContainer res = userInterface.model().currentUser().productManager()
                    .lookupProductContainer(input.getText());
            if (res == null) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("That product does not exist");
                a.showAndWait();
                return;
            }
            pc = res;
            prevPCString = res.toString();
            userInterface.updateLeftView(modifyingProductView);
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> userInterface.returnToMenu());

        choosingProductView.getChildren().addAll(
                new HBox(new Text("Product name/code"), input),
                new HBox(modify, cancel));
        choosingProductView.getChildren().forEach(e -> e.setStyle("-fx-alignment: center; -fx-spacing: 10;"));
        userInterface.updateLeftView(choosingProductView);
    }

    private void initModifyingProductView() {
        // category = new TextField();
        name = new TextField();
        code = new TextField();
        price = new TextField();
        quantity = new TextField();
        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            if (userInterface.model().productManager().codeExists(code.getText())) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("Code already exists");
                a.showAndWait();
                return;
            }

            double parsedPrice = -1.0;
            if (!price.getText().isBlank()) {
                try {
                    parsedPrice = Double.parseDouble(price.getText());
                } catch (NumberFormatException f) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("Price must be a float");
                    a.showAndWait();
                    return;
                }
                if (parsedPrice < 0.0) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("Price must be a postive float");
                    a.showAndWait();
                    return;
                }
            }

            int parsedQuantity = -1;
            if (!quantity.getText().isBlank()) {
                try {
                    parsedQuantity = Integer.parseInt(quantity.getText());
                } catch (NumberFormatException f) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("Quantity must be an integer");
                    a.showAndWait();
                    return;
                }
                if (parsedQuantity < 0) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("Quantity must be a positive integer");
                    a.showAndWait();
                    return;
                }
                if (parsedQuantity > pc.quantityLimit()) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("Quantity must be less than the allowed limit: " + pc.quantityLimit());
                    a.showAndWait();
                    return;
                }
            }

            // if (!category.getText().isBlank()) {
            // pc.product().setCategory(category.getText());
            // }
            if (!name.getText().isBlank()) {
                pc.product().setName(name.getText());
            }
            if (!code.getText().isBlank()) {
                pc.product().setCode(code.getText());
            }
            if (parsedPrice > 0.0) {
                pc.product().setPrice(parsedPrice);
            }
            if (parsedQuantity > 0) {
                pc.setQuantity(parsedQuantity);
            }

            userInterface.returnToMenu();
            if (pc.toString().equals(prevPCString)) {
                Alert a = new Alert(AlertType.CONFIRMATION);
                a.setHeaderText("Nothing was changed");
                a.showAndWait();
            } else {
                userInterface.updateRightView(new ProductTableView(userInterface));
                userInterface.printToConsole(String.format(
                        """
                                The following changes were made to this product:

                                Before:

                                %s

                                After:

                                %s
                                        """, prevPCString, pc.toString()));
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> userInterface.returnToMenu());

        modifyingProductView = new LeftViewTemplate(userInterface);
        modifyingProductView.setHeader("Modify product");
        GridPane gp = new GridPane();
        // gp.add(new Text("Category:"), 0, 1);
        gp.add(new Text("Name:"), 0, 2);
        gp.add(new Text("Code:"), 0, 3);
        gp.add(new Text("Price:"), 0, 4);
        gp.add(new Text("Quantity:"), 0, 5);

        // gp.add(category, 1, 1);
        gp.add(name, 1, 2);
        gp.add(code, 1, 3);
        gp.add(price, 1, 4);
        gp.add(quantity, 1, 5);

        gp.setHgap(10);
        gp.setVgap(10);

        modifyingProductView.getChildren().addAll(
                gp,
                // new HBox(new Text("Category:"), category),
                // new HBox(new Text("Name:"), name),
                // new HBox(new Text("Code:"), code),
                // new HBox(new Text("Price:"), price),
                // new HBox(new Text("Quantity:"), quantity),
                new HBox(confirm, cancel));

        modifyingProductView.getChildren().forEach(e -> e.setStyle("-fx-alignment: center; -fx-spacing: 10;"));
    }

}

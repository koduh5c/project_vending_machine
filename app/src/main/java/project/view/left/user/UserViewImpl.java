package project.view.left.user;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.model.product.ProductContainer;
import project.model.product.ProductManager;
import project.model.user.UserType;
import project.model.user.container.Transaction;
import project.view.UserInterface;
import project.view.left.SaveCardView;
import project.view.left.transaction.PaymentMethodView;
import project.view.template.LeftViewTemplate;

abstract class UserViewImpl extends LeftViewTemplate implements UserView {
    protected final int recentTransactionsLimit = 5;

    public UserViewImpl(UserInterface userInterface) {
        super(userInterface);
        header.setText(userInterface.model().currentUser().username());
        init();
        setAllButtonWidth(200);
        buildLogoutButton();
        displayStartupTransactionOutput();
    }

    @Override
    public Button buildRecentProductsButton() {
        Button b = new Button("My Last " + recentTransactionsLimit + " Product(s)");
        b.setOnAction(e -> {
            List<String> res = userInterface.model().recentTransactionsFromCurrentUserSorted(0, recentTransactionsLimit).stream()
                    .map(f -> f.product().name())
                    .collect(Collectors.toList());
            if (res.isEmpty()) {
                userInterface.printToConsole("No product to show");
            } else {
                userInterface.printToConsole(listToConsoleFormat(res));
            }
        });
        return b;
    }

    public Button buildAllTransactionButton() {
        Button b = new Button("All My Transaction(s)");
        b.setOnAction(e -> {
            List<String> res = userInterface.model().currentUser().transactions().stream()
                    .map(Transaction::toString)
                    .collect(Collectors.toList());
            if (res.isEmpty()) {
                userInterface.printToConsole("No transactions to print");
            } else {
                userInterface.printToConsole(listToConsoleFormat(res));
            }
        });
        return b;
    }

    @Override
    public HBox buildPurchaseBox() {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        TextField buyProduct = new TextField("Smiths");
        buyProduct.setMaxWidth(120);
        TextField quantity = new TextField();
        quantity.setPromptText("Qty");
        quantity.setMaxWidth(30);
        Button button = new Button("Buy");
        button.setOnAction(e -> {
            // TransactionBuilder tb = new TransactionBuilder();
            ProductManager productManager = userInterface.model().currentUser().productManager();
            String product = buyProduct.getText();
            ProductContainer container = productManager.lookupProductContainer(product);
            if (container != null) {
                int qty;
                try {
                    qty = Integer.parseInt(quantity.getText());
                    if (qty < 0) {
                        userInterface.printToConsole("Please enter a positive number for quantity!");
                        return;
                    }
                } catch (NumberFormatException f) {
                    userInterface.printToConsole("Please enter a valid number for quantity!");
                    return;
                }

                if (container.quantityLeft() >= qty) {
                    userInterface.updateLeftView(new PaymentMethodView(userInterface, container, qty));
                    userInterface.printToConsole("Buying " + quantity.getText() + " " +
                            product + ". . .");
                } else {
                    userInterface.printToConsole("We don't sell that many." +
                            "\n We only have " + container.quantityLeft() +
                            " " + product + " left!");
                }
            } else {
                userInterface.printToConsole("We don't sell this product.");
            }

        });
        box.getChildren().addAll(buyProduct, quantity, button);
        return box;
    }

    @Override
    public Button buildSaveCardButton() {
        Button button = new Button("Save a Credit Card");
        button.setOnAction(e -> {
            userInterface.updateLeftView(new SaveCardView(userInterface));
            userInterface.printToConsole("Saving your credit card details . . .");
        });
        return button;
    }

    protected void setAllButtonWidth(int width) {
        getChildren().forEach(e -> {
            if (e.getClass().equals(Button.class)) {
                ((Button) e).setPrefWidth(width);
            }
        });
    }

    protected void displayStartupTransactionOutput() {
        if (Arrays.asList(UserType.ANONYMOUS, UserType.CUSTOMER)
                .contains(userInterface.model().currentUser().userType())) {
            List<String> res = userInterface.model().currentUser().uniqueList();
            if (res.isEmpty()) {
                userInterface.printToConsole("No transactions to list yet..");
            } else {
                String list;
                if (res.size() < 5) {
                    System.out.println("res.size()");
                    list = listToConsoleFormat(res.subList(0, res.size()));
                } else {
                    list = listToConsoleFormat(res.subList(0, 5));
                }
                userInterface.printToConsole(String.format(
                        """
                                Last Five Transactions:
                                --------------------------
                                %s""",
                        list));
            }
        }
    }

    protected String listToConsoleFormat(List<String> ls) {
        return ls.stream().collect(Collectors.joining("\n"));
    }

    protected void buildLogoutButton() {
        VBox box = new VBox();
        Button res = new Button("Log Out");
        box.setAlignment(Pos.CENTER);
        VBox.setMargin(res, new Insets(100, 0, 0, 0));
        res.setOnAction(e -> {
            userInterface.logout("Logged out");
        });
        box.getChildren().add(res);
        getChildren().add(box);
    }
}

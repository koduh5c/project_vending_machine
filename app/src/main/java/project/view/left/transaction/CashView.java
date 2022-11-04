package project.view.left.transaction;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import project.model.payment.PaymentMethod;
import project.model.product.ProductContainer;
import project.model.user.container.Transaction;
import project.view.UserInterface;
import project.view.right.productTable.ProductTableView;
import project.view.template.LeftViewTemplate;

public class CashView extends LeftViewTemplate {
    private TextField fiveCents;
    private TextField tenCents;
    private TextField twentyCents;
    private TextField fiftyCents;
    private TextField oneDollars;
    private TextField twoDollars;
    private TextField fiveDollars;
    private TextField tenDollars;
    private TextField twentyDollars;
    private TextField fiftyDollars;
    private TextField hundredDollars;

    private ProductContainer productContainer;
    private int quantity;

    public CashView(UserInterface userInterface, ProductContainer container, int qty) {
        super(userInterface);
        this.productContainer = container;
        this.quantity = qty;

        header.setText("ENTER NOTES/COINS");
        buildForm();
    }

    public HBox buttons() {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(payButton(), backButton(), cancelButton());
        return box;
    }

    public Button backButton() {
        Button backButton = new Button("Back");

        backButton.setOnAction(e -> userInterface.setLeft(new PaymentMethodView(
                userInterface, productContainer, quantity)));
        return backButton;
    }
    public Button cancelButton() {
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            Transaction t = new Transaction.TransactionBuilder()
                    .setAmount(productContainer.product().price() * quantity)
                    .setQuantity(quantity)
                    .setProduct(productContainer.product())
                    .setPaymentMethod(PaymentMethod.CASH)
                    .setDate(LocalDateTime.now())
                    .setUser(userInterface.model().currentUser())
                    .setChange(0)
                    .setCancellationReason("Cancelled during cash payment.")
                    .build();
            userInterface.model().currentUser().addTransaction(t);
            userInterface.logout("You Have Logged Out Due To Cancellation!");
        });
        return cancelButton;
    }

    public void timeOut() {
        Transaction t = new Transaction.TransactionBuilder()
                .setAmount(productContainer.product().price() * quantity)
                .setQuantity(quantity)
                .setProduct(productContainer.product())
                .setPaymentMethod(PaymentMethod.CASH)
                .setDate(LocalDateTime.now())
                .setUser(userInterface.model().currentUser())
                .setChange(0)
                .setCancellationReason("Timeout during cash payment.")
                .build();
        userInterface.model().currentUser().addTransaction(t);
        userInterface.logout("You Have Logged Out Due To Timeout!");
    }


    public Button payButton() {
        Button payButton = new Button("Pay");
        payButton.setOnAction(e -> processPayment());
        return payButton;
    }

    private void processPayment() {
        Map<Double, String> notesStr = new HashMap<>();
                notesStr.put(0.05, fiveCents.getText());
                notesStr.put(0.1, tenCents.getText());
                notesStr.put(0.2, twentyCents.getText());
                notesStr.put(0.5, fiftyCents.getText());
                notesStr.put(1.0, oneDollars.getText());
                notesStr.put(2.0, twoDollars.getText());
                notesStr.put(5.0, fiveDollars.getText());
                notesStr.put(10.0, tenDollars.getText());
                notesStr.put(20.0, twentyDollars.getText());
                notesStr.put(50.0, fiftyDollars.getText());
                notesStr.put(100.0, hundredDollars.getText());

                Map<Double, Integer> notes = new HashMap<>();
                for (Double key : notesStr.keySet()) {
                    try {
                        notes.put(key, Integer.parseInt(notesStr.get(key)));
                    } catch (NumberFormatException e) {
                        notes.put(key, 0);
                    }
                }

                double userPaid = userInterface.model().calculateChange(notes);

                Map<Double, Integer> changeMap = userInterface.model().pay(
                        productContainer.product().price() * quantity,
                        notes,
                        null);

                if (changeMap == null) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("Insufficient cash inserted! \n" +
                    "Please Try Again.");
                    a.show();
                    return;
                }

                productContainer.setQuantityLeft(productContainer.quantityLeft() - quantity);
                Transaction t = new Transaction.TransactionBuilder()
                        .setAmount(productContainer.product().price() * quantity)
                        .setQuantity(quantity)
                        .setProduct(productContainer.product())
                        .setPaymentMethod(PaymentMethod.CASH)
                        .setDate(LocalDateTime.now())
                        .setUser(userInterface.model().currentUser())
                        .setChange(userInterface.model().calculateChange(changeMap))
                        .build();
                
                userInterface.model().currentUser().addTransaction(t);
                userInterface.updateRightView(new ProductTableView(userInterface));

                double change = userInterface.model().calculateChange(changeMap);

                String changeMessage;
                if (change == 0) {
                    changeMessage = "You have no change. ";
                } else {
                    changeMessage = String.format("Your change is:\nNote/Coin : Count\n %s",
                            stringifyChangeMap(changeMap));
                }

                userInterface.logout(String.format("""
                THANK YOU FOR YOUR PURCHASE!
                You have purchased %s %s!
                Total cost: %s
                You paid: %s
                %s
                You are logged out!
                """,
                quantity,
                productContainer.product().name(),
                productContainer.product().price() * quantity,
                userPaid,
                changeMessage
                ));
    }

    private String stringifyChangeMap(Map<Double, Integer> changeMap) {
        return changeMap.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                .map(e -> String.format("\t%.2f : %d\n", e.getKey(), e.getValue()))
                .collect(Collectors.joining());
    }

    public void buildForm() {
        getChildren().addAll(
                fiveCentsBox(),
                tenCentsBox(),
                twentyCentsBox(),
                fiftyCentsBox(),
                oneDollarBox(),
                twoDollarBox(),
                fiveDollarBox(),
                tenDollarBox(),
                twentyDollarBox(),
                fiftyDollarBox(),
                hundredDollarBox(),
                buttons()
        );
    }
    public HBox fiveCentsBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("5c");
        label.setMinWidth(25);
        fiveCents = new TextField();

        cashBox.getChildren().addAll(label, fiveCents);
        return cashBox;
    }

    public HBox tenCentsBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("10c");
        label.setMinWidth(25);
        tenCents = new TextField();

        cashBox.getChildren().addAll(label, tenCents);
        return cashBox;
    }

    public HBox twentyCentsBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("20c");
        label.setMinWidth(25);
        twentyCents = new TextField();

        cashBox.getChildren().addAll(label, twentyCents);
        return cashBox;
    }

    public HBox fiftyCentsBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("50c");
        label.setMinWidth(25);
        fiftyCents = new TextField();

        cashBox.getChildren().addAll(label, fiftyCents);
        return cashBox;
    }

    public HBox oneDollarBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("$1");
        label.setMinWidth(25);
        oneDollars = new TextField();

        cashBox.getChildren().addAll(label, oneDollars);
        return cashBox;
    }

    public HBox twoDollarBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("$2");
        label.setMinWidth(25);
        twoDollars = new TextField();

        cashBox.getChildren().addAll(label, twoDollars);
        return cashBox;
    }

    public HBox fiveDollarBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("$5");
        label.setMinWidth(25);
        fiveDollars = new TextField();

        cashBox.getChildren().addAll(label, fiveDollars);
        return cashBox;
    }

    public HBox tenDollarBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("$10");
        label.setMinWidth(25);
        tenDollars = new TextField();

        cashBox.getChildren().addAll(label, tenDollars);
        return cashBox;
    }

    public HBox twentyDollarBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("$20");
        label.setMinWidth(25);
        twentyDollars = new TextField();

        cashBox.getChildren().addAll(label, twentyDollars);
        return cashBox;
    }

    public HBox fiftyDollarBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("$50");
        label.setMinWidth(25);
        fiftyDollars = new TextField();

        cashBox.getChildren().addAll(label, fiftyDollars);
        return cashBox;
    }

    public HBox hundredDollarBox() {
        HBox cashBox = new HBox();
        cashBox.setSpacing(10);
        cashBox.setAlignment(Pos.CENTER);

        Label label = new Label("$100");
        label.setMinWidth(25);
        hundredDollars = new TextField();

        cashBox.getChildren().addAll(label, hundredDollars);
        return cashBox;
    }

}

package project.view.left.transaction;

import java.time.LocalDateTime;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import project.model.payment.Card;
import project.model.payment.PaymentMethod;
import project.model.product.ProductContainer;
import project.model.user.container.Transaction;
import project.model.user.container.Transaction.TransactionBuilder;
import project.view.UserInterface;
import project.view.right.productTable.ProductTableView;
import project.view.template.LeftViewTemplate;

public class CardView extends LeftViewTemplate {
    private PasswordField cardNumber;
    private TextField cardHolderName;
    private ProductContainer productContainer;
    private int quantity;

    public CardView(UserInterface userInterface, ProductContainer container, int qty) {
        super(userInterface);
        this.productContainer = container;
        this.quantity = qty;

        header.setText("Credit Card Details");
        buildForm();
    }

    public void buildForm() {
        getChildren().addAll(
                numberBox(),
                nameBox(),
                buttons(),
        buildUseSavedCardButton());
    }

    public HBox buttons() {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
            payButton(),
            backButton(),
            cancelButton());
        return box;
    }

    public Button buildUseSavedCardButton() {
        Button b = new Button("Use Saved Card");
        b.setOnAction(e -> {
            Card registeredCard = userInterface.model().currentUser().registeredCard();
            if (registeredCard == null) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("No Saved Card To Use");
                a.showAndWait();
                return;
            }
            processPayment(registeredCard);
        });
        return b;
    }

    public Button cancelButton() {
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            Transaction t = new Transaction.TransactionBuilder()
                    .setAmount(productContainer.product().price() * quantity)
                    .setQuantity(quantity)
                    .setProduct(productContainer.product())
                    .setPaymentMethod(PaymentMethod.CARD)
                    .setDate(LocalDateTime.now())
                    .setUser(userInterface.model().currentUser())
                    .setChange(0)
                    .setCancellationReason("Cancelled during card payment.")
                    .build();
            userInterface.model().currentUser().addTransaction(t);
            userInterface.logout("You Have Logged Out Due To Cancellation!");
        });
        return cancelButton;
    }

    public Button backButton() {
        Button backButton = new Button("Back");

        backButton.setOnAction(e -> userInterface.setLeft(new PaymentMethodView(
                userInterface, productContainer, quantity)));
        return backButton;
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
                .setCancellationReason("Timeout during card payment.")
                .build();
        userInterface.model().currentUser().addTransaction(t);
        userInterface.logout("You Have Logged Out Due To Timeout!");
    }

    public Button payButton() {
        Button payButton = new Button("Pay");
        payButton.setOnAction(e -> {
                Card c = userInterface.model()
                    .currentUser()
                    .paymentManager()
                    .cardManager()
                    .lookupCard(cardHolderName.getText(), cardNumber.getText());
                if (c == null) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setHeaderText("Card with given credentials is not registered");
                    a.showAndWait();
                    return;
                }
                processPayment(c);
        });
        return payButton;
    }

    private void processPayment(Card c) {
            userInterface.model().pay(productContainer.product().price() * quantity, null, c);
            productContainer.setQuantityLeft(productContainer.quantityLeft() - quantity);

            Transaction t = new TransactionBuilder()
                    .setAmount(productContainer.product().price() * quantity)
                    .setQuantity(quantity)
                    .setProduct(productContainer.product())
                    .setPaymentMethod(PaymentMethod.CARD)
                    .setDate(LocalDateTime.now())
                    .setUser(userInterface.model().currentUser())
                    .setChange(0)
                    .setCancellationReason(null)
                    .build();

            userInterface.model().currentUser().addTransaction(t);
            userInterface.updateRightView(new ProductTableView(userInterface));
            userInterface.logout("THANK YOU FOR YOUR PURCHASE! \n" +
                "You have purchased " + quantity + " " + productContainer.product().name()
                + "!\nYou paid a total of $" +
                productContainer.product().price() * quantity +
                " using saved card ending with XX" +
                 c.getNumber().substring(c.getNumber().length() - 2) +
                ".\nYou are logged out!");
    }

    public HBox numberBox() {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        Label label = new Label("Card Number");
        label.setMinWidth(100);
        cardNumber = new PasswordField();

        box.getChildren().addAll(label, cardNumber);
        return box;
    }

    public HBox nameBox() {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        Label label = new Label("Cardholder Name");
        label.setMinWidth(100);
        cardHolderName = new TextField();

        box.getChildren().addAll(label, cardHolderName);
        return box;
    }
}

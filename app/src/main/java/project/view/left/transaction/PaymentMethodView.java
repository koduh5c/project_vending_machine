package project.view.left.transaction;

import javafx.scene.control.Button;
import project.model.payment.PaymentMethod;
import project.model.product.ProductContainer;
import project.model.user.container.Transaction;
import project.view.UserInterface;
import project.view.template.LeftViewTemplate;

import java.time.LocalDateTime;

public class PaymentMethodView extends LeftViewTemplate {
    private ProductContainer productContainer;
    private int quantity;

    public PaymentMethodView(UserInterface userInterface, ProductContainer container, int qty) {
        super(userInterface);
        this.productContainer = container;
        this.quantity = qty;

        header.setText("Payment Method");

        getChildren().addAll(
            buildCashButton(),
            buildCardButton(),
            cancelButton());
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
                    .setCancellationReason("Cancelled during payment method selection.")
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
                .setCancellationReason("Timeout during payment method selection.")
                .build();
        userInterface.model().currentUser().addTransaction(t);
        userInterface.logout("You Have Logged Out Due To Timeout!");
    }

    public Button buildCashButton() {
        Button cashButton = new Button("Cash Payment");
        cashButton.setOnAction(e -> {
                userInterface.updateLeftView(new CashView(userInterface,
                        productContainer, quantity));
                userInterface.printToConsole("Paying by cash . . . \n " +
                        "You have selected: \n" +
                        productContainer.product().name() + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Total Amount: $" + productContainer.product().price() * quantity);
        });
        return cashButton;
    }

    public Button buildCardButton() {
        Button cardButton = new Button("Card Payment");
        cardButton.setOnAction(e -> {
                userInterface.updateLeftView(new CardView(userInterface, productContainer, quantity));
                userInterface.printToConsole("Paying by card . . . \n " +
                        "You have selected: \n" +
                        productContainer.product().name() + "\n" +
                        "Quantity: " + quantity + "\n" +
                        "Total Amount: $" + productContainer.product().price() * quantity);
        });
        return cardButton;
    }
}

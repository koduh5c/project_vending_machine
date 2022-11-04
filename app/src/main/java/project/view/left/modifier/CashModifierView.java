package project.view.left.modifier;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import project.model.payment.CashManager;
import project.view.UserInterface;
import project.view.template.LeftViewTemplate;

public class CashModifierView extends LeftViewTemplate {
    private ComboBox<Double> cashField;
    private TextField quantityField;

    public CashModifierView(UserInterface userInterface) {
        super(userInterface);
        header.setText("Notes/coins to modify");
        getChildren().addAll(
                cashBox(),
                backButton());
    }

    public HBox cashBox() {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        CashManager cashManager = userInterface.model().currentUser().paymentManager().cashManager();

        cashField = new ComboBox<>(FXCollections
                .observableArrayList(cashManager.getDenominations()));
        cashField.setPromptText("Notes/Coins");
        cashField.setMinWidth(100);

        quantityField = new TextField();
        quantityField.setPromptText("Qty");
        quantityField.setMaxWidth(50);

        box.getChildren().addAll(cashField, quantityField, modifyButton());
        return box;

    }

    public Button modifyButton() {
        Button button = new Button("Modify");
        button.setOnAction(e -> modify());
        return button;
    }

    public Button backButton() {
        Button button = new Button("Back");
        button.setOnAction(e -> userInterface.returnToMenu());
        return button;
    }

    public void modify() {
        CashManager cashManager = userInterface.model().paymentManager().cashManager();

        if (cashField.getValue() == null) {
            userInterface.printToConsole("Please select a note/coin to modify.");
            return;
        }

        if (quantityField.getText().equals("")) {
            userInterface.printToConsole("Please specify the quantity of the notes/coins.");
            return;
        }

        try {
            if (Integer.parseInt(quantityField.getText()) < 0) {
                userInterface.printToConsole("Please enter a positive integer!");
                return;
            }
        } catch (NumberFormatException e) {
            userInterface.printToConsole("Please enter a number!");
            return;
        }


        cashManager.setNotes(cashField.getValue(),
                Integer.parseInt(quantityField.getText()));
        userInterface.printToConsole(String.format("""
                You have modified %s's quantity to %s!
                """, cashField.getValue(), quantityField.getText()));
    }
}

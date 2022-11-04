package project.view.left;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import project.view.UserInterface;
import project.view.template.LeftViewTemplate;

public class SaveCardView extends LeftViewTemplate {
    private PasswordField cardNumber;
    private TextField cardHolderName;

    public SaveCardView(UserInterface userInterface) {
        super(userInterface);
        header.setText("Credit Card Details");
        buildView();
    }

    public void buildView() {
        getChildren().addAll(
                numberBox(),
                nameBox(),
                buttons()
        );
    }

    public HBox buttons() {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(
            saveButton(),
            backButton()
        );
        return box;
    }

    public Button backButton() {
        Button backButton = new Button("Back");

        backButton.setOnAction(e -> userInterface.returnToMenu());
        return backButton;
    }

    public Button saveButton() {
        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
                if (userInterface.model().currentUser().assignCardToUser(
                        cardHolderName.getText(), cardNumber.getText())) {
                    subheader.setText("Card Saved!");
                } else {
                    subheader.setText("Card does not exist! Please try again.");
                }
        });
        return saveButton;
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

package project.view.left.modifier;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.view.UserInterface;
import project.view.template.LeftViewTemplate;

public class UserModifierView extends LeftViewTemplate {
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField usertypeField;

    public UserModifierView(UserInterface userInterface) {
        super(userInterface);
        usernameField = new TextField();
        passwordField = new PasswordField();
        usertypeField = new TextField();
    }

    public VBox initCreateUserBox() {
        header.setText("Creating new user");
        Button confirmButton = new Button("Create User");
        confirmButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                errorPrompt("One or more empty field(s)");
                return;
            }
            if (userInterface.model().createAccount(usernameField.getText(), passwordField.getText(), "CUSTOMER")) {
                confirmationPrompt(String.format("New customer with:\nusername: %s",
                                usernameField.getText()));
                                userInterface.login(usernameField.getText(), passwordField.getText());
            } else {
                errorPrompt("An error has occured");
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> userInterface.returnToLogin());
        getChildren().addAll(
                new HBox(new Text("Username"), usernameField),
                new HBox(new Text("Password"), passwordField),
                new HBox(confirmButton, cancelButton));
        styleAllChildrenBox();
        return this;
    }

    public VBox initAddUserBox() {
        header.setText("Adding new user");
        Button confirmButton = new Button("Add User");
        confirmButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || usertypeField.getText().isEmpty()) {
                errorPrompt("One or more empty field(s)");
                return;
            }
            if (userInterface.model().createAccount(usernameField.getText(), passwordField.getText(), usertypeField.getText())) {
                confirmationPrompt(
                        String.format("New user with:\nusername: %s\npassword: %s\nstatus: %s\nhas been created",
                                usernameField.getText(),
                                passwordField.getText(),
                                usertypeField.getText()));
                userInterface.returnToMenu();
            } else {
                errorPrompt("An error has occured");
            }
        });
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(e -> userInterface.returnToMenu());
        getChildren().addAll(
                new HBox(new Text("Username"), usernameField),
                new HBox(new Text("Password"), passwordField),
                new HBox(new Text("User Type"), usertypeField),
                new HBox(confirmButton, cancelButton));
        styleAllChildrenBox();
        return this;
    }

    public VBox initRemoveUserBox() {
        header.setText("Removing a user");
        Button confirmButton = new Button("REMOVE USER");
        confirmButton.setOnAction(e -> {
            if (usernameField.getText().isEmpty()) {
                errorPrompt("Enter a username");
                return;
            }
            if (userInterface.model().removeAccount(usernameField.getText())) {
                confirmationPrompt(
                        String.format("User with:\nusername: %s\nhas been removed",
                                usernameField.getText()));
                userInterface.returnToMenu();
            } else {
                errorPrompt("An error has occured");
            }
        });
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(e -> userInterface.returnToMenu());
        getChildren().addAll(
                new HBox(new Text("Username"), usernameField),
                new HBox(confirmButton, cancelButton));
        styleAllChildrenBox();
        return this;
    }

    private void styleAllChildrenBox() {
        getChildren().stream().forEach(e -> {
            if (e.getClass().equals(HBox.class)) {
                ((HBox) e).setSpacing(10);
                ((HBox) e).setAlignment(Pos.CENTER);
            }
        });
    }

    private void confirmationPrompt(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void errorPrompt(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}

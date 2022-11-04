package project.view.left.login;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.view.UserInterface;
import project.view.left.modifier.UserModifierView;
import project.view.template.LeftViewTemplate;

public class LoginView extends LeftViewTemplate {

    TextField usernameField;
    PasswordField passwordField;

    public LoginView(UserInterface userInterface) {
        super(userInterface);

        init();
    }

    public void init() {
        Label usernameLabel = new Label("Username ");
        usernameLabel.setPrefWidth(100);

        usernameField = new TextField();

        HBox usernameBox = new HBox();
        usernameBox.getChildren().addAll(usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password ");
        passwordLabel.setPrefWidth(100);

        passwordField = new PasswordField();

        HBox passwordBox = new HBox();
        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        header.setText("SIGN IN");

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(
            buildLoginButton(),
            buildAnonymousButton(),
            buildCreateUserButton()
        );

        getChildren().addAll(
            usernameBox,
            passwordBox,
            buttonBox,
            new LoginTester(userInterface)
        );

    }

    public Button buildLoginButton() {
        Button button = new Button("LOGIN");
        button.setPrefWidth(200);
        button.setOnAction(e -> {
                if (userInterface.login(usernameField.getText(), passwordField.getText())) {

                } else {
                    subheader.setText("Invalid credentials");
                }
                usernameField.clear();
                passwordField.clear();
        });
        return button;
    }

    public Button buildAnonymousButton() {
        Button button = new Button("ANONYMOUS LOGIN");
        button.setPrefWidth(200);
        button.setOnAction(e -> {
                if (userInterface.loginAnonymously()) {

                } else {
                    subheader.setText("Something went wrong");
                }
                usernameField.clear();
                passwordField.clear();
        });
        return button;
    }

    public Button buildCreateUserButton() {
        Button button = new Button("CREATE USER");
        button.setPrefWidth(200);
        button.setOnAction(e -> {
                userInterface.updateLeftView(new UserModifierView(userInterface).initCreateUserBox());
        });
        return button;
    }
}

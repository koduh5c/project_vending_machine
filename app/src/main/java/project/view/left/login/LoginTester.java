package project.view.left.login;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.view.UserInterface;

public class LoginTester extends VBox {
    private UserInterface userInterface;

    public LoginTester(UserInterface userInterface) {
        this.userInterface = userInterface;
        setStyle(
                "-fx-border-color: black; -fx-alignment: center; -fx-spacing: 10; -fx-padding: 10;");
        getChildren().addAll(
                new Text("TESTER: QUICK LOGIN"),
                cashier(),
                customer(),
                owner(),
                seller());
    }

    private Button cashier() {
        Button res = new Button("LOGIN CASHIER");
        res.setOnAction(e -> userInterface.login("vincent123", "abc59141"));
        return res;
    }

    private Button customer() {
        Button res = new Button("LOGIN CUSTOMER");
        res.setOnAction(e -> {
            userInterface.login("charles123", "abc40691");
            userInterface.model().currentUser().assignCardToUser("Charles", "40691");
         });
        return res;
    }

    private Button owner() {
        Button res = new Button("LOGIN OWNER");
        res.setOnAction(e -> userInterface.login("ruth123", "abc55134"));
        return res;
    }

    private Button seller() {
        Button res = new Button("LOGIN SELLER");
        res.setOnAction(e -> userInterface.login("kasey123", "abc60146"));
        return res;
    }
}

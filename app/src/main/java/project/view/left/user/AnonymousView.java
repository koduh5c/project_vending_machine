package project.view.left.user;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.Button;
import project.view.UserInterface;

public class AnonymousView extends UserViewImpl {
    public AnonymousView(UserInterface userInterface) {
        super(userInterface);
    }

    @Override
    public void init() {
        header.setText("Anonymous");
        subheader.setText("Customer");
        getChildren().addAll(
            buildPurchaseBox(),
            buildRecentProductsButton()
        );
    }

    @Override
    public Button buildRecentProductsButton() {
        Button b = new Button("Last " + recentTransactionsLimit + " Product(s)");
        b.setOnAction(e -> {
            List<String> res = userInterface.model().recentTransactionsFromAllUsersSorted(0, recentTransactionsLimit).stream()
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
}

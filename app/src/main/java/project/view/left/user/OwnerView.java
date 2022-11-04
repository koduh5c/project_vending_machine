package project.view.left.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import project.view.UserInterface;
import project.view.left.modifier.CashModifierView;
import project.view.left.modifier.ProductModifierView;
import project.view.left.modifier.UserModifierView;

public class OwnerView extends UserViewImpl {

    public OwnerView(UserInterface userInterface) {
        super(userInterface);
    }

    @Override
    public void init() {
        subheader.setText("Owner");
        getChildren().addAll(
            buildPurchaseBox(),
            buildSaveCardButton(),
            buildRecentProductsButton(),
            buildAllTransactionButton(),
            buildListButton(),
            buildListCashButton(),
            buildSummariseButton(),
            buildAddUserButton(),
            buildRemoveUserButton(),
            buildModifyProductButton(),
            buildModifyChangeButton()
        );
    }

    public Button buildListButton() {
        Button button = new Button("List All Users ");
        button.setOnAction(e -> {
            List<String> ls = userInterface.model().currentUser().uniqueList();
            if (!ls.isEmpty()) {
                userInterface.printToConsole("USERNAMES SUMMARY\n\n" + listToConsoleFormat(ls));
            } else {
                userInterface.printToConsole("Nothing to print");
            }
        });
        return button;
    }

    public Button buildSummariseButton() {
        Button button = new Button("Cancelled Transactions");
        button.setOnAction(e -> {
            List<String> ls = userInterface.model().currentUser().summarise();
            if (!ls.isEmpty()) {
                userInterface.printToConsole("CANCELLED TRANSACTIONS SUMMARY\n\n" + listToConsoleFormat(ls));
            } else {
                userInterface.printToConsole("Nothing to print");
            }
        });
        return button;
    }

    public Button buildAddUserButton() {
        Button button = new Button("Create User");
        button.setOnAction(e -> userInterface.updateLeftView(new UserModifierView(userInterface).initAddUserBox()));
        return button;
    }

    public Button buildRemoveUserButton() {
        Button button = new Button("Remove User");
        button.setOnAction(e -> userInterface.updateLeftView(new UserModifierView(userInterface).initRemoveUserBox()));
        return button;
    }

    public Button buildModifyProductButton() {
        Button b = new Button("Modify Product");
        b.setOnAction(e -> new ProductModifierView(userInterface));
        return b;
    }

    public Button buildModifyChangeButton() {
        Button b = new Button("Modify Change");
         b.setOnAction(e -> userInterface.setLeft(new CashModifierView(userInterface)));
        return b;
    }

    public Button buildListCashButton() {
        Button button = new Button("List Available Coins/Notes");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String change = stringifyChangeMap(userInterface.model().currentUser().
                        paymentManager().cashManager().register());
                userInterface.printToConsole(String.format("""
                        Notes/Coins Available in Vending Machine:
                        %s
                        """,
                        change));

            }
        });
        return button;
    }

    private String stringifyChangeMap(Map<Double, Integer> changeMap) {
        return changeMap.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                .map(e -> String.format("\t%.2f : %d\n", e.getKey(), e.getValue()))
                .collect(Collectors.joining());
    }

}

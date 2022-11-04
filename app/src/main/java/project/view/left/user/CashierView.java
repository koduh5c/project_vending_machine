package project.view.left.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import project.view.UserInterface;
import project.view.left.modifier.CashModifierView;

public class CashierView extends UserViewImpl {

    public CashierView(UserInterface userInterface) {
        super(userInterface);
    }

    @Override
    public void init() {
        subheader.setText("Cashier");
        getChildren().addAll(
            buildPurchaseBox(),
            buildSaveCardButton(),
            buildRecentProductsButton(),
            buildAllTransactionButton(),
            buildListButton(),
            buildSummariseButton(),
            modifyCash()
        );
    }

    public Button buildListButton() {
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

    public Button modifyCash() {
        Button button = new Button("Modify Cash Amount");
        button.setOnAction(e -> userInterface.setLeft(new CashModifierView(userInterface)));
        return button;
    }

    public Button buildSummariseButton() {
        Button button = new Button("Summarise all transactions");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> list = userInterface.model().currentUser().summarise();
                userInterface.printToConsole(listToConsoleFormat(list));
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

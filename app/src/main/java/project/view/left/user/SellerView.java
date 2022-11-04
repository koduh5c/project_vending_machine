package project.view.left.user;

import java.util.List;

import javafx.scene.control.Button;
import project.view.UserInterface;
import project.view.left.modifier.ProductModifierView;

public class SellerView extends UserViewImpl {

    public SellerView(UserInterface userInterface) {
        super(userInterface);
    }

    @Override
    public void init() {
        subheader.setText("Seller");
        getChildren().addAll(
            buildPurchaseBox(),
            buildSaveCardButton(),
            buildRecentProductsButton(),
            buildAllTransactionButton(),
            buildSummariseButton(),
            buildListButton(),
            buildModifyProductButton()
        );
    }

    public Button buildListButton() {
        Button button = new Button("List");
        button.setOnAction(e -> {
                 List<String> ls = userInterface.model().currentUser().uniqueList();
                 if (!ls.isEmpty()) {
                     userInterface.printToConsole(listToConsoleFormat(ls));
                 } else {
                     userInterface.printToConsole("Nothing to print");
                 }
        });
        return button;
    }

    public Button buildSummariseButton() {
        Button button = new Button("Summarise All Transactions");
        button.setOnAction(e -> {
                 List<String> ls = userInterface.model().currentUser().summarise();
                 if (!ls.isEmpty()) {
                     userInterface.printToConsole(listToConsoleFormat(ls));
                 } else {
                     userInterface.printToConsole("Nothing to print");
                 }
        });
        return button;
    }

    public Button buildModifyProductButton() {
        Button button = new Button("Modify Product");
        button.setOnAction(e -> {
            new ProductModifierView(userInterface);
        });
        return button;
    }
    
}

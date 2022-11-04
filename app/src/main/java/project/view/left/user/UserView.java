package project.view.left.user;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public interface UserView {
    public void init();

    public Button buildRecentProductsButton();

    public Button buildAllTransactionButton();

    public HBox buildPurchaseBox();

    // public Button buildListButton();

    // public Button buildSummariseButton();

    public Button buildSaveCardButton();
}

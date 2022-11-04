package project.view.left.user;

import project.view.UserInterface;

public class CustomerView extends UserViewImpl {
    public CustomerView(UserInterface userInterface) {
        super(userInterface);
    }

    @Override
    public void init() {
        subheader.setText("Customer");
        getChildren().addAll(
            buildPurchaseBox(),
            buildSaveCardButton(),
            buildRecentProductsButton(),
            buildAllTransactionButton()
        );
    }


}

package project.view.left;

import javafx.scene.Node;
import project.model.user.container.User;
import project.view.UserInterface;
import project.view.left.user.AnonymousView;
import project.view.left.user.CashierView;
import project.view.left.user.CustomerView;
import project.view.left.user.OwnerView;
import project.view.left.user.SellerView;

public class UserViewFactory {
    /**
     * @param user
     * @param userInterface
     * @return UserView object equivalent to the User argument
     */
    public static Node createUserView(User user, UserInterface userInterface) {
        Node res = null;
        switch (user.userType()) {
            case OWNER:
                res = new OwnerView(userInterface);
                break;
            case SELLER:
                res = new SellerView(userInterface);
                break;
            case CASHIER:
                res = new CashierView(userInterface);
                break;
            case CUSTOMER:
                res = new CustomerView(userInterface);
                break;
            case ANONYMOUS:
                res = new AnonymousView(userInterface);
                break;
            default:
                break;
        }
        return res;
    }   
}

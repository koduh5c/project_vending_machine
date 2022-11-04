package project.model.user;

import project.model.user.container.Anonymous;
import project.model.user.container.Cashier;
import project.model.user.container.Customer;
import project.model.user.container.Owner;
import project.model.user.container.Seller;
import project.model.user.container.User;

public class UserFactory {
    public static User createUser(String username, String password, UserType userType) {
        User u = null;
        switch (userType) {
            case OWNER:
                u = new Owner();
                break;
            case SELLER:
                u = new Seller();
                break;
            case CASHIER:
                u = new Cashier();
                break;
            case CUSTOMER:
                u = new Customer();
                break;
            case ANONYMOUS:
                u = Anonymous.getInstance();
                break;
            default:
                break;
        }
        
        if (u != null) {
            u.setUsername(username);
            u.setPassword(password);
            u.setUserType(userType);
        }
        return u;
    }   
}

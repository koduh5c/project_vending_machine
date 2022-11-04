package project.model.user.container;

import java.util.List;

import project.model.payment.Card;
import project.model.payment.PaymentManager;
import project.model.product.ProductManager;
import project.model.user.UserManager;
import project.model.user.UserType;

public interface User {
    public String username();

    public boolean setUsername(String username);

    public String password();

    public boolean setPassword(String password);

    public void injectPaymentManager(PaymentManager paymentManager);

    public void injectUserManager(UserManager userManager);

    public void injectProductManager(ProductManager productManager);

    /**
     * @return a list of unique string relevant to the user type
     */
    public List<String> uniqueList();

    public List<String> summarise();

    public boolean isAnonymous();

    public UserType userType();

    public boolean setUserType(UserType userType);

    public boolean addTransaction(Transaction t);

    public List<Transaction> transactions();

    public PaymentManager paymentManager();

    public UserManager userManager();

    public ProductManager productManager();

    public boolean assignCardToUser(String name, String number);

    public Card registeredCard();
}

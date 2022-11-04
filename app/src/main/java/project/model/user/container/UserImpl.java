package project.model.user.container;

import java.util.ArrayList;
import java.util.List;

import project.model.payment.Card;
import project.model.payment.PaymentManager;
import project.model.product.ProductManager;
import project.model.user.UserManager;
import project.model.user.UserType;

abstract class UserImpl implements User {
    protected List<Transaction> transactions;
    protected Card registeredCard;
    protected String username;
    protected String password;
    protected UserType userType;
    protected PaymentManager paymentManager;
    protected UserManager userManager;
    protected ProductManager productManager;

    public UserImpl() {
        transactions = new ArrayList<>();

        // REMOVE THIS BLOCK TO REMOVE TRANSACTION MOCKING
        // -----------------------------------------------------------
        // Injecting with mock Transaction's
        // transactions = IntStream.range(0, 15)
        //         .mapToObj(i -> new TransactionBuilder()
        //                 .setDate(LocalDateTime.of(
        //                         ThreadLocalRandom.current().nextInt(2000, 2022),
        //                         ThreadLocalRandom.current().nextInt(1, 13),
        //                         ThreadLocalRandom.current().nextInt(1, 29),
        //                         ThreadLocalRandom.current().nextInt(0, 24),
        //                         ThreadLocalRandom.current().nextInt(0, 60),
        //                         ThreadLocalRandom.current().nextInt(0, 60)
        //                     ))
        //                 .setQuantity(ThreadLocalRandom.current().nextInt(1, 20))
        //                 .build())
        //         .collect(Collectors.toList());
        // // Setting mock Transaction's from index 4 to 9 as cancelled
        // IntStream.range(4, 9).forEach(i -> transactions.get(i).setCancellationReason("Some cancellation reason"));
        // -----------------------------------------------------------
    }

    @Override
    public PaymentManager paymentManager() {
        return paymentManager;
    }

    @Override
    public UserManager userManager() {
        return userManager;
    }

    @Override
    public ProductManager productManager() {
        return productManager;
    }

    @Override
    public void injectPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }

    @Override
    public void injectUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void injectProductManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public boolean setUsername(String username) {
        this.username = username;
        return true;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public boolean setPassword(String password) {
        this.password = password;
        return true;
    }

    @Override
    public UserType userType() {
        return userType;
    }

    @Override
    public boolean setUserType(UserType userType) {
        this.userType = userType;
        return true;
    }

    @Override
    public boolean isAnonymous() {
        return username == null;
    }

    @Override
    public boolean addTransaction(Transaction t) {
        if (t == null) {
            return false;
        }
        transactions.add(t);
        return true;
    }

    @Override
    public List<Transaction> transactions() {
        return transactions;
    }

    @Override
    public List<String> uniqueList() {
        return new ArrayList<>();
    }

    @Override
    public List<String> summarise() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User Name: " + username
                + "\nUser Type:" + userType + "\n\n";
    }

    @Override
    public boolean assignCardToUser(String name, String number) {
        Card c = paymentManager.getCard(name, number);
        if (c == null) {
            return false;
        }
        registeredCard = c;
        return true;
    }

    @Override
    public Card registeredCard() {
        return registeredCard;
    }
}

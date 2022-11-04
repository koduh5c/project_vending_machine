package project.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import project.model.payment.Card;
import project.model.payment.PaymentManager;
import project.model.product.ProductContainer;
import project.model.product.ProductManager;
import project.model.user.UserManager;
import project.model.user.UserType;
import project.model.user.container.Transaction;
import project.model.user.container.User;

public class VendingMachine {
    private User currentUser;
    private UserManager userManager;
    private PaymentManager paymentManager;
    private ProductManager productManager;

    public VendingMachine() {
        init();
    }

    private void init() {
        this.userManager = new UserManager();
        this.paymentManager = new PaymentManager();
        this.productManager = new ProductManager();
    }

    private void injectUserWithManagers() {
        currentUser.injectPaymentManager(paymentManager);
        currentUser.injectUserManager(userManager);
        currentUser.injectProductManager(productManager);
    }

    public UserManager userManager() {
        return userManager;
    }

    public List<Transaction> recentTransactionsFromAllUsersSorted(int fromIndex, int toIndex) {
        List<Transaction> allTransactions = userManager.db().stream()
                .map(User::transactions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Collections.sort(allTransactions, Comparator.comparing(Transaction::date).reversed());
        if (toIndex > allTransactions.size()) {
            toIndex = allTransactions.size();
        }
        return allTransactions.subList(fromIndex, toIndex);
    }

    public List<Transaction> recentTransactionsFromCurrentUserSorted(int fromIndex, int toIndex) {
        List<Transaction> tList = currentUser.transactions().stream().collect(Collectors.toList());
        Collections.sort(tList, Comparator.comparing(Transaction::date).reversed());
        return tList.subList(fromIndex, toIndex);
    }

    public PaymentManager paymentManager() {
        return paymentManager;
    }

    public ProductManager productManager() {
        return productManager;
    }

    public User currentUser() {
        return currentUser;
    }

    public List<ProductContainer> products() {
        return productManager.db();
    }

    public Map<String, List<ProductContainer>> categorisedProducts() {
        return productManager.categoriseDB();
    }

    public boolean loginAsAnonymous() {
        currentUser = userManager.getAnonymousUser();
        injectUserWithManagers();
        return true;
    }

    public boolean loginAsUser(String username, String password) {
        User user = userManager.getUser(username, password);
        if (user == null) {
            return false;
        }
        currentUser = user;
        injectUserWithManagers();
        return true;
    }

    public boolean createAccount(String username, String password, String userType) {
        if (username == null || password == null || userType == null) {
            return false;
        }

        UserType res = null;
        try {
            res = UserType.valueOf(userType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return userManager.createAccount(username, password, res) != null;
    }

    public boolean removeAccount(String username) {
        return userManager.removeUser(username) != null;
    }

    // returns change
    public Map<Double, Integer> pay(double cost, Map<Double, Integer> paidCash, Card card) {
        if (cost <= 0.0) {
            return null;
        }
        // Trying to input both cash and card
        if ((paidCash == null && card == null) || (paidCash != null && card != null)) {
            return null;
        }
        if (paidCash != null) {
            Map<Double, Integer> change = paymentManager.payCash(paidCash, cost);
            if (change == null) {
                return null;
            } else {
                return change;
            }
        } else if (card != null) {
            if (paymentManager.payCard(card)) {
                return new HashMap<>();
            } else {
                return null;
            }
        }
        return null;
    }

    public double calculateChange(Map<Double, Integer> change) {
        if (change == null) {
            return 0.0;
        }

        double total = 0;
        for (Map.Entry<Double, Integer> note : change.entrySet()) {
            if (note.getValue() < 0 || note.getKey() < 0) {
                continue;
            }
            total += note.getKey() * note.getValue();
        }
        return total;
    }

}

package project.model.payment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import project.model.Manager;

public class PaymentManager implements Manager {
    private CardManager cardManager;
    private CashManager cashManager;

    public PaymentManager() {
        injectCardManager(new CardManager());
        injectCashManager(new CashManager());
    }

    public void injectCardManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }

    public void injectCashManager(CashManager cashManager) {
        this.cashManager = cashManager;
    }

    public CardManager cardManager() {
        return cardManager;
    }

    public CashManager cashManager() {
        return cashManager;
    }

    public Card addCard(String cardName, String cardNumber) {
        Card res = new Card(cardName, cardNumber);
        cardManager.db().add(res);
        return res;
    }

    public Card getCard(String cardName, String cardNumber) {
        List<Card> res = cardManager.db().stream()
                .filter(e -> e.getName().equals(cardName) && e.getNumber().equals(cardNumber))
                .collect(Collectors.toList());
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    public Map<Double, Integer> payCash(Map<Double, Integer> paidNotes, double total) {
        if (!cashManager.paidEnough(paidNotes, total) || !cashManager.validNotesAmount(paidNotes)) {
            return null;
        }
        double change = cashManager.calculateChange(paidNotes, total);
        if (!cashManager.checkSufficientChange(paidNotes, change)) {
            return null;
        }
        Map<Double, Integer> returnChange = cashManager.returnChange(paidNotes, change);
        return returnChange;
    }

    public boolean payCard(Card card) {
        if (card == null) {
            return false;
        }
        if (cardManager.cardExists(card)) {
            return true;
        }
        return false;
    }

    // private double total(Map<Product, Integer> products) {
    // double total = 0;

    // for (Map.Entry<Product, Integer> product : products.entrySet()) {
    // total += product.getKey().price() * product.getValue();
    // }

    // return total;
    // }

}

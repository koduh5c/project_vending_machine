package project.model.payment;

import java.util.ArrayList;

public class CardManager {
    private final String dbDir = getClass().getResource("/creditcards_db.json").getPath();
    private ArrayList<Card> cardDB;

    public CardManager() {
        this.cardDB = CreditDBParser.loadDB(dbDir);
    }

    public ArrayList<Card> db() {
        return this.cardDB;
    }

    public Card lookupCard(String name, String number) {
        if (name == null || number == null) {
            return null;
        }
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return null;
        }
        String input = name.toLowerCase();
        String inputNum = number;
        for (int i = 0; i < cardDB.size(); i++) {
            Card current = cardDB.get(i);
            String nameDB = current.getName().toLowerCase();
            String numberDB = current.getNumber();
            if (input.equals(nameDB) && inputNum.equals(numberDB)) {
                return current;
            }
        }
        return null;
    }

    public boolean cardExists(Card card) {
        if (card == null || card.getNumber() == null || card.getName() == null) {
            return false;
        }
        try {
            Integer.parseInt(card.getNumber());
        } catch (NumberFormatException e) {
            return false;
        }
        String input = card.getName().toLowerCase();
        String inputNum = card.getNumber();
        for (int i = 0; i < cardDB.size(); i++) {
            Card current = cardDB.get(i);
            String nameDB = current.getName().toLowerCase();
            String numberDB = current.getNumber();
            if (input.equals(nameDB) && inputNum.equals(numberDB)) {
                return true;
            }
        }
        return false;
    }

}

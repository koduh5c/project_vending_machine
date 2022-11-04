package project.model.payment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.lang.Math;

public class CashManager {
    private final int initialQuantity = 5;
    private final List<Double> denominations = Arrays.asList(100.0, 50.0, 20.0, 10.0, 5.0, 2.0, 1.0, 0.5, 0.2, 0.1,
            0.05);
    private Map<Double, Integer> register;

    public CashManager() {
        initRegister(initialQuantity);
    }

    public Map<Double, Integer> register() {
        return register;
    }

    public Map<Double, Integer> getChangeCounter() {
        return denominations.stream()
                .collect(Collectors.toMap(Function.identity(), e -> 0));
    }

    public boolean initRegister(int initialAmount) {
        if (initialAmount < 0 || denominations.isEmpty()) {
            return false;
        }
        register = denominations.stream()
                .collect(Collectors.toMap(Function.identity(), e -> initialAmount));
        return true;
    }

    // checking user has input enough notes
    // input = The amount someone paid Map -> e.g. {100.0: 5, 50.0: 2, etc}
    // true = user has paid enough
    // false = user still needs to put more money in
    public boolean paidEnough(Map<Double, Integer> notes, double cost) {
        if (notes == null) {
            return false;
        }
        if (cost <= 0.0) {
            return true;
        }
        double paid = 0;
        for (Map.Entry<Double, Integer> note : notes.entrySet()) {
            paid += note.getValue() * note.getKey();
        }
        if (paid < cost) {
            return false;
        }
        return true;
    }

    // returns double of change required to be returned
    public double calculateChange(Map<Double, Integer> notes, double cost) {
        if (notes == null) {
            return -1.0;
        }

        double paid = 0;
        for (Map.Entry<Double, Integer> note : notes.entrySet()) {
            paid += note.getValue() * note.getKey();
        }
        // paid nothing and the product cost something
        if (paid <= 0.0) {
            return -1.0;
        }

        if (paid <= 0.0 || cost <= 0.0) {
            return 0.0;
        }
        double calc = paid - cost;
        calc = calc*100;
        calc = Math.round(calc);
        calc = calc /100;
        return calc;
    }

    // checks whether the user inputs positive int notes
    public boolean validNotesAmount(Map<Double, Integer> paidNotes) {
        if (paidNotes == null) {
            return false;
        }

        for (Map.Entry<Double, Integer> note : paidNotes.entrySet()) {
            if (note.getValue() < 0) {
                return false;
            }
        }
        return true;
    }

    // takes in notes because you can return the notes received as change
    // checks whether you have enough change in the machine
    // true = enough change
    // false = not enough change to return
    public boolean checkSufficientChange(Map<Double, Integer> counterMap, double requiredChange) {
        if (counterMap == null) {
            return false;
        }
        if (requiredChange <= 0.0) {
            return true;
        }
        double process = requiredChange;
        // boolean finishProcess = false;
        // add to atm, then remove from atm
        addNotes(counterMap);
        for (int i = 0; i < denominations.size(); i++) {
            process = requiredChange;
            for (int x = 0 + i; x < denominations.size(); x++) {
                if (denominations.get(x) > process) {
                    continue;
                }
                if (register.get(denominations.get(x)) == 0) {
                    continue;
                }
                int calc = (int) (process / denominations.get(x));
                if (calc > register.get(denominations.get(x))) {
                    process -= register.get(denominations.get(x)) * denominations.get(x);
                } else {
                    process -= calc * denominations.get(x);
                }
                if (process == 0) {
                    // finishProcess = true;
                    subtractNotes(counterMap);
                    return true;
                }
            }
        }
        subtractNotes(counterMap);
        return false;
    }

    // returns denominations and amount of each as Map and subtracts from the
    // atm
    public Map<Double, Integer> returnChange(Map<Double, Integer> paid, double change) {
        if (change < 0.0 || paid == null) {
            return null;
        }
        if (change == 0.0) {
            Map<Double, Integer> noChange = new HashMap<>();
            noChange.put(0.0, 0);
            return noChange;
        }
        double process = change;
        boolean finishProcess = false;
        Map<Double, Integer> returnNotes = null;
        addNotes(paid);
        for (int i = 0; i < denominations.size(); i++) {
            process = change;
            Map<Double, Integer> temp = new HashMap<>();
            for (int x = 0 + i; x < denominations.size(); x++) {
                if (denominations.get(x) > process) {
                    continue;
                }
                if (register.get(denominations.get(x)).equals(0)) {
                    continue;
                }
                int calc = (int) (process / denominations.get(x));
                if (calc > register.get(denominations.get(x))) {
                    temp.put(denominations.get(x), register.get(denominations.get(x)));
                    process -= register.get(denominations.get(x)) * denominations.get(x);
                } else {
                    process -= calc * denominations.get(x);
                    temp.put(denominations.get(x), calc);
                }
                if (process == 0) {
                    finishProcess = true;
                    break;
                }
            }
            if (finishProcess) {
                returnNotes = temp;
                subtractNotes(returnNotes);
                return returnNotes;
            }
        }
        return returnNotes;
    }

    // removes the returned notes from the atm
    public void subtractNotes(Map<Double, Integer> subtract) {
        if (subtract == null) {
            return;
        }
        for (Map.Entry<Double, Integer> note : subtract.entrySet()) {
            int subtractAmount = register.get(note.getKey()) - note.getValue();
            if (subtractAmount <= 0) {
                register.put(note.getKey(), 0);
            } else {
                register.put(note.getKey(), subtractAmount);
            }
        }
    }

    public void addNotes(Map<Double, Integer> add) {
        if (add == null) {
            return;
        }
        for (Map.Entry<Double, Integer> note : add.entrySet()) {
            int addAmount = register.get(note.getKey()) + note.getValue();
            if (addAmount <= 0) {
                register.put(note.getKey(), 0);
            } else {
                register.put(note.getKey(), addAmount);
            }
        }
    }

    public void setNotes(Double denomination, Integer amount) {
        if (denomination <= 0.0) {
            return;
        }

        //if denomination doesnt exist in vending machine
        if (register.get(denomination) == null) {
            return;
        }

        if (amount < 0) {
            register.put(denomination, 0);
        } else {
            register.put(denomination, amount);
        }
    }

    public void setNotes(Map<Double, Integer> set) {
        if (set == null) {
            return;
        }

        for(Map.Entry<Double, Integer> note : set.entrySet()) {
            register.put(note.getKey(), note.getValue());
        }
    }

    public int getDenomAmount(double denomination) {
        for (Map.Entry<Double, Integer> note : register.entrySet()) {
            if (note.getKey() == denomination) {
                return note.getValue();
            }
        }
        return 0;
    }

    public List<Double> getDenominations() {
        return this.denominations;
    }

}

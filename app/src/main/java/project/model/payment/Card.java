package project.model.payment;

public class Card {
    private String name;
    private String number;
    
    public Card(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}

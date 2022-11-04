package project.model.payment;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CreditDBParser {
    public static ArrayList<Card> loadDB(String dir) {
        JSONParser parser = new JSONParser();
        ArrayList<Card> cardList = new ArrayList<Card>();
        try {
            Object object = parser.parse(new FileReader(dir));
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonCard = (JSONObject) jsonArray.get(i);
                String name = (String) jsonCard.get("name");
                String number = (String) jsonCard.get("number");
                Card card = new Card(name, number);
                cardList.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardList;
    }
}

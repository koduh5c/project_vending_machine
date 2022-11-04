package project;

 import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import project.model.payment.Card;

public class CreditDBParserTest {
    private static String dbDir = CreditDBParserTest.class.getResource("/creditcards_db.json").getPath();
    private static JSONParser parser = new JSONParser();
    private static ArrayList<Card> cardList = new ArrayList<Card>();

     @BeforeAll
     public static void setUp() {
         try {
             Object object = parser.parse(new FileReader(dbDir));
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
     }

    @Test
    public void notEmpty(){
        assertEquals(dbDir.isEmpty(), false);
    }

    @Test
    public void nameExists(){
        try { Object object = parser.parse(new FileReader(dbDir));
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonCard = (JSONObject) jsonArray.get(i);
                assertEquals(jsonCard.containsKey("name"), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void numberExists(){
        try { Object object = parser.parse(new FileReader(dbDir));
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonCard = (JSONObject) jsonArray.get(i);
                assertEquals(jsonCard.containsKey("number"), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void numberIsLength5(){
        try { Object object = parser.parse(new FileReader(dbDir));
            JSONArray jsonArray = (JSONArray) object;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonCard = (JSONObject) jsonArray.get(i);
                assertEquals(jsonCard.get("number").toString().length(), 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}

package project.model.product;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ProductDBParser {
    public static List<Product> loadDB(String dir) {
        JSONParser parser = new JSONParser();
        List<Product> res = new ArrayList<>();
        try {
            JSONObject jo = (JSONObject) parser.parse(new FileReader(dir));
            for (Object category : jo.keySet()) {
                for (Object product : (JSONArray) jo.get(category)) {
                    JSONObject entry = (JSONObject) product;
                    res.add(new Product(
                        category.toString(),
                        entry.get("name").toString(),
                        entry.get("code").toString(),
                        Double.parseDouble(entry.get("price").toString())
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}

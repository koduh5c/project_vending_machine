package project.model.user;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import project.model.user.container.User;

public class UserDBParser {
    public static List<User> loadDB(String dir) {
        JSONParser parser = new JSONParser();
        List<User> res = new ArrayList<>();
        try {
            JSONArray jo = (JSONArray) parser.parse(new FileReader(dir));
            for (Object o : jo) {
                JSONObject userObj = (JSONObject) o;
                String userType = userObj.get("type").toString().toUpperCase();
                String username = userObj.get("username").toString();
                String password = userObj.get("password").toString();
                User newUser = UserFactory.createUser(username, password, UserType.valueOf(userType));
                res.add(newUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}

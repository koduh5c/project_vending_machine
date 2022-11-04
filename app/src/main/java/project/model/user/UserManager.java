package project.model.user;

import java.util.List;
import java.util.stream.Collectors;

import project.model.Manager;
import project.model.user.container.User;

public class UserManager implements Manager {
    private final String dbDir = getClass().getResource("/user_db.json").getPath();
    private final User anonymousUser;
    private List<User> db;

    public UserManager() {
        loadDB();
        anonymousUser = UserFactory.createUser("", "", UserType.ANONYMOUS);
        db.add(anonymousUser);
    }

    public List<User> db() {
        return db;
    }

    private boolean loadDB() {
        List<User> db = UserDBParser.loadDB(dbDir);
        if (db == null) {
            return false;
        }
        this.db = db;
        return true;
    }

    public User getAnonymousUser() {
        return anonymousUser;
    }

    public User getUser(String username, String password) {
        return db.stream()
                .filter(e -> e.username().equals(username) && e.password().equals(password))
                .findAny().orElse(null);
    }

    public User createAccount(String username, String password, UserType userType) {
        if (db.stream().anyMatch(e -> e.username().equals(username))) {
            return null;
        }
        User user = UserFactory.createUser(username, password, userType);
        db.add(user);
        return user;
    }

    public User removeUser(String username) {
        List<User> res = db.stream().filter(e -> e.username().equals(username)).collect(Collectors.toList());
        if (res.isEmpty()) {
            return null;
        }
        db.remove(res.get(0));
        return res.get(0);
    }

}

package project.model.user.container;

import java.util.List;
import java.util.stream.Collectors;

public class Owner extends UserImpl {

    // Report of all users
    @Override
    public List<String> uniqueList() {
        return userManager.db().stream()
                .map(User::toString)
                .collect(Collectors.toList());
    }

    // Report of cancelled transactions
    @Override
    public List<String> summarise() {
        return userManager.db().stream()
                .flatMap(e -> e.transactions().stream())
                .filter(Transaction::isCancelled)
                .map(Transaction::toString)
                .collect(Collectors.toList());
    }
}

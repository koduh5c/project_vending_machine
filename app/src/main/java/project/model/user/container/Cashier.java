package project.model.user.container;

import java.util.List;
import java.util.stream.Collectors;

public class Cashier extends UserImpl {

    @Override
    public List<String> uniqueList() {
        return paymentManager.cashManager().register().entrySet().stream()
                .map(e -> String.format("Denomination: %d, Quantity: %d", e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> summarise() {
        return userManager.db().stream()
                .flatMap(e -> e.transactions().stream())
                .map(Transaction::toString)
                .collect(Collectors.toList());
    }

}

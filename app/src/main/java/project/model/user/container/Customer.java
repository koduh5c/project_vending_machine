package project.model.user.container;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Customer extends UserImpl {
    private final int maxList = 10;

    @Override
    public List<String> uniqueList() {
        List<Transaction> res = transactions.stream().collect(Collectors.toList());
        Collections.reverse(res);
        return res.stream()
                .limit(maxList)
                .map(Transaction::toString)
                .collect(Collectors.toList());
    }
}

package project.model.user.container;

import java.util.List;
import java.util.stream.Collectors;

import project.model.product.ProductContainer;

public class Seller extends UserImpl {

    // A report of AVAILABLE products (clarification from ed post:
    // https://edstem.org/au/courses/9767/discussion/1073148)
    @Override
    public List<String> uniqueList() {
        return productManager.db().stream()
                .filter(e -> e.quantityLeft() > 0)
                .map(ProductContainer::toString)
                .collect(Collectors.toList());
    }

    // A report of ALL products
    @Override
    public List<String> summarise() {
        return userManager.db().stream()
                .flatMap(e -> e.transactions().stream())
                .map(Transaction::toString)
                .collect(Collectors.toList());
    }

}

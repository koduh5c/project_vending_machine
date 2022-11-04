package project.model.user.container;

import java.time.LocalDateTime;

import project.model.payment.PaymentMethod;
import project.model.product.Product;

public class Transaction {
    private LocalDateTime date;
    private Product product;
    private double amount;
    private double change;
    private int quantity;
    private PaymentMethod paymentMethod;
    private User user;
    private String cancellationReason;

    public Transaction(
            LocalDateTime date,
            Product product,
            double amount,
            double change,
            int quantity,
            PaymentMethod paymentMethod,
            User user,
            String cancellationReason) {
        this.date = date;
        this.product = product;
        this.amount = amount;
        this.change = change;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.user = user;
        this.cancellationReason = cancellationReason;
    }

    public LocalDateTime date() {
        return date;
    }

    public Product product() {
        return product;
    }

    public double amount() {
        return amount;
    }

    public double change() {
        return change;
    }

    public int quantity() {
        return quantity;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }

    public User user() {
        return user;
    }

    public String cancellationReason() {
        return cancellationReason;
    }

    public boolean isCancelled() {
        return cancellationReason != null;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public static class TransactionBuilder {
        private LocalDateTime date = null;
        private Product product = null;
        private double amount = 0.0;
        private double change = 0.0;
        private int quantity = 0;
        private PaymentMethod paymentMethod = null;
        private User user = null;
        private String cancellationReason = null;

        public TransactionBuilder setDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public TransactionBuilder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public TransactionBuilder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder setChange(double change) {
            this.change = change;
            return this;
        }

        public TransactionBuilder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public TransactionBuilder setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public TransactionBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public TransactionBuilder setCancellationReason(String cancellationReason) {
            this.cancellationReason = cancellationReason;
            return this;
        }

        public Transaction build() {
            return new Transaction(
                    date,
                    product,
                    amount,
                    change,
                    quantity,
                    paymentMethod,
                    user,
                    cancellationReason);
        }
    }

    @Override
    public String toString() {
        return "Date: " + date + "\nProduct: " + product + " \nAmount: " + amount + " \nChange: " + change
                + "\nQuantity: " + quantity + "\nPayment Method: " + paymentMethod + "\nUser: " + user
                + "\nCancellation Reason: " + cancellationReason + "\n\n";
    }
}

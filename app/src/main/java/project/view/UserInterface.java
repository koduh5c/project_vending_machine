package project.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import project.model.VendingMachine;
import project.view.center.ConsoleView;
import project.view.left.UserViewFactory;
import project.view.left.login.LoginView;
import project.view.left.transaction.CardView;
import project.view.left.transaction.CashView;
import project.view.left.transaction.PaymentMethodView;
import project.view.right.productTable.ProductTableView;

public class UserInterface extends BorderPane {
    public final int WIDTH = 1400;
    public final int HEIGHT = 720;

    private VendingMachine model;
    private Scene scene;
    private Node currentUserView;
    private Node currentView;
    private Node leftView;
    private Node centreView;
    private Node rightView;

    public UserInterface(VendingMachine model) {
        this.model = model;

        scene = new Scene(this, WIDTH, HEIGHT);

        leftView = new LoginView(this);
        centreView = new ConsoleView(this);
        rightView = new ProductTableView(this);

        updateLeftView(leftView);
        updateCentreView(centreView);
        updateRightView(rightView);
    }

    public Scene scene() {
        return scene;
    }

    public VendingMachine model() {
        return model;
    }

    public int width() {
        return WIDTH;
    }

    public int height() {
        return HEIGHT;
    }

    public boolean login(String username, String password) {
        boolean loginSuccessful = model.loginAsUser(username, password);
        if (!loginSuccessful) {
            return false;
        }
        Node loginView = UserViewFactory.createUserView(model.currentUser(), this);
        updateLeftView(loginView);
        currentUserView = loginView;
//        printToConsole("Login successful");
        return true;
    }

    public boolean loginAnonymously() {
        boolean loginSuccessful = model.loginAsAnonymous();
        if (!loginSuccessful) {
            return false;
        }
        Node loginView = UserViewFactory.createUserView(model.currentUser(), this);
        updateLeftView(loginView);
        currentUserView = loginView;
        return true;
    }

    public void logout(String message) {
        if (isLoggedin()) {
            updateLeftView(new LoginView(this));
            currentUserView = null;
            printToConsole(message);
        }
    }

    public void printToConsole(String s) {
        ((ConsoleView) centreView).setConsoleText(s);
    }

    public void updateLeftView(Node node) {
        currentView = node;
        setLeft(node);
    }

    public void updateRightView(Node node) {
        setRight(node);
    }

    public void updateCentreView(Node node) {
        setCenter(node);
    }

    private boolean isLoggedin() {
        return currentUserView != null;
    }

    /**
     * Returns to the main menu IF LOGGED IN
     */
    public void returnToMenu() {
        if (isLoggedin()) {
            updateLeftView(currentUserView);
        }
    }

    /**
     * Returns to the login menu IF NOT LOGGED IN
     */
    public void returnToLogin() {
        if (!isLoggedin()) {
            updateLeftView(new LoginView(this));
        }
    }

    public boolean timeoutTransaction() {
        if (currentView instanceof PaymentMethodView) {
            ((PaymentMethodView) currentView).timeOut();
            return true;
        } else if (currentView instanceof CashView) {
            ((CashView) currentView).timeOut();
            return true;
        } else if (currentView instanceof CardView) {
            ((CardView) currentView).timeOut();
            return true;
        }
        return false;
    }


}
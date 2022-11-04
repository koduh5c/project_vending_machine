package project;
import javafx.application.Application;
import javafx.stage.Stage;
import project.model.VendingMachine;
import project.view.UserInterface;

public class App extends Application{
    private final VendingMachine model = new VendingMachine();
    private final UserInterface view = new UserInterface(model); 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(view.scene());
        primaryStage.setTitle("Vending Machine Interface");

        primaryStage.show();

    }

}

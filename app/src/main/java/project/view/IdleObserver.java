package project.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class IdleObserver {
    public static void start(UserInterface userInterface, int countdown) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(countdown), ev -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("You have been idle for more than " + countdown + " seconds.\nYou have been logged out.");
            alert.setOnHidden(evt -> {
                if (!userInterface.timeoutTransaction()) {
                    userInterface.logout("Logged out due to time out!");
                }
                // Platform.exit();
                // System.exit(0);
            });
            alert.show();
        }));
        timeline.play();
        userInterface.scene().addEventFilter(KeyEvent.KEY_PRESSED, ev -> {
            timeline.playFromStart();
        });
        userInterface.scene().addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
            timeline.playFromStart();
        });
    }
}

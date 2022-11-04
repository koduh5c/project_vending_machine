package project.view.template;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import project.view.UserInterface;

public class CenterViewTemplate extends VBox {
    protected final UserInterface userInterface;

    public CenterViewTemplate(UserInterface userInterface) {
        this.userInterface = userInterface;
        
        setAlignment(Pos.CENTER);
        setPrefWidth(300);
        setSpacing(10);
        setPadding(new Insets(30));
    }
}

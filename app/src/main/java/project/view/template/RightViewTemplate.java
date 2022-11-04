package project.view.template;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import project.view.UserInterface;

public class RightViewTemplate extends VBox {
    protected final UserInterface userInterface;
    protected Text header;

    public RightViewTemplate(UserInterface userInterface) {
        this.userInterface = userInterface;
        
        header = new Text();
        header.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        
        getChildren().addAll(
            header
        );

        setPrefWidth(300);
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
    }
}

package project.view.center;

import java.util.List;

import javafx.scene.control.TextArea;
import project.view.UserInterface;
import project.view.template.CenterViewTemplate;

public class ConsoleView extends CenterViewTemplate {
    private TextArea consoleField;

    public ConsoleView(UserInterface userInterface) {
        super(userInterface);

        init();
    }

    public void init() {
        consoleField = new TextArea();
        consoleField.setPrefHeight(userInterface.height() * .8);
        consoleField.setPrefWidth(userInterface.width() * .45);

        getChildren().addAll(consoleField);
    }

    public void setConsoleText(String text) {
        consoleField.setText(text);
    }

    public String formatToConsoleOutput(List<String> ls) {
        return ls.stream().reduce("\n", String::concat);
    }


}

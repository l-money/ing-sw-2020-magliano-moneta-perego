package santorini.view;

import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PlayTable {

    private Stage stage;


    public void initialize() {
        System.out.println("Table");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

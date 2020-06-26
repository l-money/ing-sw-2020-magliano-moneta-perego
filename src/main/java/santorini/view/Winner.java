package santorini.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class Winner {
    private Stage stage;

    @FXML
    public void initialize() {
        System.out.println("Hai vinto");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

package santorini.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Class  Wait
 */

public class Wait {

    private Stage stage;

    @FXML
    public void initialize() {
        System.out.println("Wait Windows");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

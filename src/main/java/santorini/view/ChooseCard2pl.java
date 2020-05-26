package santorini.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ChooseCard2pl {

    @FXML
    private ImageView firstGod;

    DropShadow shadow = new DropShadow();
    private Stage stage;

    @FXML
    void clickFirstGod(MouseEvent event) {

    }

    @FXML
    void firstGodDescription(MouseEvent event) {
        firstGod.setEffect(shadow);
        Label description = new Label("DESCRZIONE DEL DIO");
        Scene scene = new Scene(description);
    }

    @FXML
    void firstGodOut(MouseEvent event) {

    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

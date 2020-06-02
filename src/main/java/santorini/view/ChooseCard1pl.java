package santorini.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import santorini.model.godCards.God;

import java.util.ArrayList;

public class ChooseCard1pl implements CardChoice {


    @FXML
    private ImageView firstGod, powerGod;

    @FXML
    private Label label1, descriptionGod;

    private God choosed = null;
    private ArrayList<God> cards;
    private Stage stage;
    private DropShadow shadow;

    public God getChoosed() {
        return this.choosed;
    }

    public ChooseCard1pl(ArrayList<God> cards) {
        this.cards = cards;
    }

    public void initialize() {
        firstGod.setImage(new Image("images/GodCards/" + cards.get(0).getName() + ".png", 165, 247, true, false));
        label1.setText(cards.get(0).getName());
        descriptionGod.setText(cards.get(0).getDescription());
        powerGod.setImage(new Image("images/GodsPower/" + cards.get(0).getName() + "Power.png"));
        powerGod.setEffect(shadow);
    }

    @FXML
    void clickFirstGod(MouseEvent event) {
        this.choosed = cards.get(0);
        stage.close();
    }

    @FXML
    void firstGodIn(MouseEvent event) {
        firstGod.setEffect(shadow);
        label1.setEffect(shadow);
    }

    @FXML
    void firstGodOut(MouseEvent event) {
        firstGod.setEffect(null);
        label1.setEffect(null);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

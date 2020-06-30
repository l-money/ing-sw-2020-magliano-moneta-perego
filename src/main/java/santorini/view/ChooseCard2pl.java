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

/**
 * Class ChooseCard2pl
 */

public class ChooseCard2pl implements CardChoice {

    @FXML
    private ImageView firstGod, secondGod, powerGod;
    @FXML
    private Label label1, label2, description;
    private ArrayList<God> cards;


    @FXML
    public void initialize() {
        System.out.println("Choose 2 cards");
        System.out.println(getClass().getClassLoader().getResource("images/GodCards/" + cards.get(0).getName() + ".png"));
        //File f = new File(String.valueOf(getClass().getClassLoader().getResource("images/GodCards/" + cards.get(0).getName() + ".png")));
        Image i = new Image("images/GodCards/" + cards.get(0).getName() + ".png", 163, 247, true, false);
        firstGod.setImage(i);
        label1.setText(cards.get(0).getName());
        secondGod.setImage(new Image("images/GodCards/" + cards.get(1).getName() + ".png"));
        label2.setText(cards.get(1).getName());
    }

    /**
     * method getChoosed
     *
     * @return god card choose
     */
    public God getChoosed() {
        return choosed;
    }

    private God choosed = null;

    DropShadow shadow = new DropShadow();
    private Stage stage;

    @FXML
    public void clickFirstGod(MouseEvent event) {
        this.choosed = cards.get(0);
        stage.close();
    }


    @FXML
    public void clickSecondGod(MouseEvent event) {
        this.choosed = cards.get(1);
        stage.close();

    }

    /**
     * method ChooseCard2pl
     *
     * @param cards .
     */
    public ChooseCard2pl(ArrayList<God> cards) {
        this.cards = cards;
    }

    @FXML
    public void firstGodDescription(MouseEvent event) {
        firstGod.setEffect(shadow);
        description.setText(cards.get(0).getDescription());
        powerGod.setImage(new Image("images/GodsPower/" + cards.get(0).getName() + "Power.png"));
        powerGod.setEffect(shadow);
        label1.setEffect(shadow);
    }

    @FXML
    public void firstGodOut(MouseEvent event) {
        firstGod.setEffect(null);
        description.setText(null);
        powerGod.setImage(null);
        powerGod.setEffect(null);
        label1.setEffect(null);
    }

    @FXML
    public void secondGodDescription(MouseEvent event) {
        secondGod.setEffect(shadow);
        description.setText(cards.get(1).getDescription());
        powerGod.setImage(new Image("images/GodsPower/" + cards.get(1).getName() + "Power.png"));
        powerGod.setEffect(shadow);
        label2.setEffect(shadow);
    }

    @FXML
    public void secondGodOut(MouseEvent event) {
        secondGod.setEffect(null);
        description.setText(null);
        powerGod.setImage(null);
        powerGod.setEffect(null);
        label2.setEffect(null);
    }

    /**
     * method setStage
     * @param stage .
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

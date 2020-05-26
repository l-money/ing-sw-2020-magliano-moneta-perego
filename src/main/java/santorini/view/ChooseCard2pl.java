package santorini.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import santorini.model.godCards.God;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Objects;

public class ChooseCard2pl implements CardChoice {

    @FXML
    private ImageView firstGod, secondGod;
    @FXML
    private TextArea description;
    @FXML
    private Label label1, label2;
    private ArrayList<God> cards;

    @FXML
    public void initialize() {
        System.out.println(getClass().getClassLoader().getResource("images/GodCards/" + cards.get(0).getName() + ".png"));
        //File f = new File(String.valueOf(getClass().getClassLoader().getResource("images/GodCards/" + cards.get(0).getName() + ".png")));
        Image i = new Image("images/GodCards/" + cards.get(0).getName() + ".png", 163, 247, true, false);
        firstGod.setImage(i);
        label1.setText(cards.get(0).getName());
        secondGod.setImage(new Image("images/GodCards/" + cards.get(1).getName() + ".png"));
        label2.setText(cards.get(1).getName());

    }

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

    public ChooseCard2pl(ArrayList<God> cards) {
        this.cards = cards;
    }

    @FXML
    public void firstGodDescription(MouseEvent event) {
        firstGod.setEffect(shadow);
        description.setText(cards.get(0).getDescription());

    }

    @FXML
    public void firstGodOut(MouseEvent event) {
        firstGod.setEffect(null);
    }

    @FXML
    public void secondGodDescription(MouseEvent event) {
        secondGod.setEffect(shadow);
        description.setText(cards.get(1).getDescription());
    }

    @FXML
    public void secondGodOut(MouseEvent event) {
        secondGod.setEffect(null);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

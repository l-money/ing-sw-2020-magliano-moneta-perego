package santorini.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import santorini.model.godCards.God;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChooseCard3pl implements CardChoice {
    DropShadow shadow = new DropShadow();
    private Stage stage;

    @FXML
    private ImageView firstGod, secondGod, thirdGod, godPower;

    @FXML
    private Label descriptionGod;

    @FXML
    private Label label1, label2, label3;

    private God choosed = null;
    private ArrayList<God> cards;

    public God getChoosed() {
        return this.choosed;
    }


    public ChooseCard3pl(ArrayList<God> cards) {
        this.cards = cards;
    }


    @FXML
    public void initialize() {
        System.out.println("Choose 3 cards");
        firstGod.setImage(new Image("images/GodCards/" + cards.get(0).getName() + ".png", 163, 247, true, false));
        label1.setText(cards.get(0).getName());
        secondGod.setImage(new Image("images/GodCards/" + cards.get(1).getName() + ".png", 163, 247, true, false));
        label2.setText(cards.get(1).getName());
        thirdGod.setImage(new Image("images/GodCards/" + cards.get(2).getName() + ".png", 163, 247, true, false));
        label3.setText(cards.get(2).getName());
        descriptionGod.setStyle("-fx-background-color: RESET;" +
                "-fx-background-radius: 30;" +
                "-fx-border-radius: 30;" +
                "-fx-text-alignment: CENTER;");
    }

    @FXML
    void clickFirstGod(MouseEvent event) {
        this.choosed = cards.get(0);
        stage.close();

//        ColorAdjust colorAdjust = new ColorAdjust();
//        colorAdjust.setContrast(0.4);
//        colorAdjust.setHue(-0.05);
//        colorAdjust.setBrightness(0.9);
//        colorAdjust.setSaturation(0.8);
//        firstGod.setEffect(colorAdjust);

    }

    @FXML
    void clickSecondGod(MouseEvent event) {
        this.choosed = cards.get(1);
        stage.close();
    }

    @FXML
    void clickThirdGod(MouseEvent event) {
        this.choosed = cards.get(2);
        stage.close();
    }

    @FXML
    void firstGodIn(MouseEvent event) {
        firstGod.setEffect(shadow);
        descriptionGod.setText(cards.get(0).getDescription());
        godPower.setImage(new Image("images/GodsPower/" + cards.get(0).getName() + "Power.png"));
        godPower.setEffect(shadow);
        label1.setEffect(shadow);
//        Tooltip.install(god1, new Tooltip("\nQuesta è la descrizione del tuo dio\n" +
//                "qui puoi inserirci tutto quello che vuoi\n" +
//                "e associarlo ad un'icona laterale\n"));
    }

    @FXML
    void firstGodOut(MouseEvent event) {
        firstGod.setEffect(null);
        descriptionGod.setText(null);
        godPower.setImage(null);
        godPower.setEffect(null);
        label1.setEffect(null);
    }

    @FXML
    void secondGodIn(MouseEvent event) {
        secondGod.setEffect(shadow);
        descriptionGod.setText(cards.get(1).getDescription());
        godPower.setImage(new Image("images/GodsPower/" + cards.get(1).getName() + "Power.png"));
        godPower.setEffect(shadow);
        label2.setEffect(shadow);
    }

    @FXML
    void secondGodOut(MouseEvent event) {
        secondGod.setEffect(null);
        descriptionGod.setText(null);
        godPower.setImage(null);
        godPower.setEffect(null);
        label2.setEffect(null);
    }

    @FXML
    void thirdGodIn(MouseEvent event) {
        thirdGod.setEffect(shadow);
        descriptionGod.setText(cards.get(2).getDescription());
        godPower.setImage(new Image("images/GodsPower/" + cards.get(2).getName() + "Power.png"));
        godPower.setEffect(shadow);
        label3.setEffect(shadow);
    }

    @FXML
    void thirdGodOut(MouseEvent event) {
        thirdGod.setEffect(null);
        descriptionGod.setText(null);
        godPower.setImage(null);
        godPower.setEffect(null);
        label3.setEffect(null);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

package santorini.view;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import santorini.model.Mossa;
import santorini.model.godCards.God;

import java.util.ArrayList;

public class ViewController extends View {

    Stage stage;

    public ViewController() {

    }

    @FXML
    public void initialize() {

    }


    @Override
    public void chooseCards(ArrayList<God> gods) {
    }

    @Override
    public void setNewAction(Mossa.Action action) {

    }

    @Override
    public void setNumeroGiocatori() {
    }

    @Override
    public void setFailed(String msg) {

    }

    @Override
    public void printMessage(String msg) {

    }

    @Override
    public void setInitializePawn() {

    }

    @Override
    public void vittoria() {

    }

    @Override
    public void sconfitta(String winner) {

    }

    @Override
    public void networkError(String player) {

    }

    @Override
    public void printTable() {

    }
}

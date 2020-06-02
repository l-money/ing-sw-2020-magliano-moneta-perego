package santorini.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import santorini.model.Mossa;
import santorini.model.godCards.God;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ViewController extends View {

    private Stage thisStage;
    private Stage overlayedStage;

    public ViewController() {

    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    @FXML
    public void initialize() {

    }


    @Override
    public void chooseCards(ArrayList<God> gods) {
        Platform.runLater(() -> {
            overlayedStage.close();
            Stage dialog = new Stage();
            dialog.setTitle("Scelta Carte Divinità");
            dialog.getIcons().add(new Image("images/cm_boardgame.png"));
            Parent root;
            FXMLLoader loader = null;
            CardChoice cc = null;
            try {
                switch (gods.size()) {
                    case 1:
                        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard1pl.fxml")));
                        cc = new ChooseCard1pl(gods);
                        cc.setStage(dialog);
                        loader.setController(cc);
                        break;
                    case 2:
                        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard2pl.fxml")));
                        cc = new ChooseCard2pl(gods);
                        cc.setStage(dialog);
                        loader.setController(cc);
                        break;
                    case 3:
                        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard3pl.fxml")));
                        cc = new ChooseCard3pl(gods);
                        cc.setStage(dialog);
                        loader.setController(cc);
                        break;
                }
                root = loader.load();
                Scene s = new Scene(root);
                dialog.setResizable(false);
                dialog.setScene(s);
                dialog.initOwner(thisStage);
                dialog.initModality(Modality.APPLICATION_MODAL);
                //dialog.setOnCloseRequest(event -> returnNumber(numberPlayersController));
                dialog.showAndWait();
                handlerClient.setCard(cc.getChoosed());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void setNewAction(Mossa.Action action) {

    }

    @Override
    public void setNumeroGiocatori() {
        Platform.runLater(() -> {
            Stage dialog = new Stage();
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("numberPlayers.fxml")));
                NumberPlayers numberPlayersController = new NumberPlayers(dialog);
                loader.setController(numberPlayersController);
                root = loader.load();
                Scene s = new Scene(root);
                dialog.setTitle("Numero Giocatori");
                dialog.getIcons().add(new Image("images/cm_boardgame.png"));
                dialog.setResizable(false);
                dialog.setScene(s);
                dialog.initOwner(thisStage);
                dialog.initModality(Modality.APPLICATION_MODAL);
                //dialog.setOnCloseRequest(event -> returnNumber(numberPlayersController));
                dialog.showAndWait();
                handlerClient.setPartecipanti(numberPlayersController.getPlayers());
                waitDialog("Attendi...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void waitDialog(String title) {
        Stage dialog = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("wait.fxml")));
            Scene s1 = new Scene(root);
            dialog.setScene(s1);
            overlayedStage = dialog;
            overlayedStage.setTitle(title);
            overlayedStage.setResizable(false);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

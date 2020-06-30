package santorini.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import santorini.network.NetworkHandlerClient;

import java.io.IOException;
import java.util.Objects;

/**
 * Class StartGame
 */

public class StartGame {

    @FXML
    private Button playButton;
    private NetworkHandlerClient handlerClient;

    @FXML
    private Button exitButton;

    private Stage stage;
    private DropShadow shadow;


    @FXML
    void clickPlayButton(MouseEvent event) {
        stage.close();
        try {
            stage.setTitle("Login User");
            stage.getIcons().add(new Image("images/cm_boardgame.png"));
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("loginuser2.fxml")));
//            ViewController controller = new ViewController();
//
//            controller.setHandlerClient(handlerClient);
//            handlerClient.setView(controller);
//
//            loader.setController(controller);
            LoginUser2 controller = new LoginUser2();
            controller.setStage(stage);
            loader.setController(controller);
            Parent root = loader.load();
            Scene s = new Scene(root);
            stage.setScene(s);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickExitButton(MouseEvent event) {
        stage.close();
    }

    @FXML
    void exitButtonIn(MouseEvent event) {
        shadow = new DropShadow();
        exitButton.setEffect(shadow);
//        exitButton.setStyle("-fx-background-color: #B22222;"+
//                "-fx-background-radius: 200em; " +
//                "-fx-min-width: 100px; " +
//                "-fx-min-height: 100px; " +
//                "-fx-max-width: 100px; " +
//                "-fx-max-height: 100px;"+
//                "-fx-opacity: 0.4");
    }

    @FXML
    void exitButtonOut(MouseEvent event) {
        exitButton.setEffect(null);
        exitButton.setStyle("-fx-background-color: RESET");
    }

    @FXML
    void playButtonIn(MouseEvent event) {
        shadow = new DropShadow();
        playButton.setEffect(shadow);
//        playButton.setStyle("-fx-background-color: #228B22;"+
//        "-fx-background-radius: 200em; " +
//                "-fx-min-width: 100px; " +
//                "-fx-min-height: 100px; " +
//                "-fx-max-width: 100px; " +
//                "-fx-max-height: 100px;"+
//                "-fx-opacity: 0.5");
    }

    @FXML
    void playButtonOut(MouseEvent event) {
        playButton.setEffect(null);
        playButton.setStyle("-fx-background-color: null");
    }


    @FXML
    public void initialize() {
        System.out.println("Start Game");
    }

    /**
     * method setStage
     *
     * @param stage .
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

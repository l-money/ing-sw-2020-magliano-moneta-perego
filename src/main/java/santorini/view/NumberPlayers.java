package santorini.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Class NumberPlayers
 */

public class NumberPlayers {

    @FXML
    private Button twoPlayers;

    @FXML
    private Button threePlayers;
    private Stage stage;

    /**
     * method getPlayers
     *
     * @return players
     */
    public int getPlayers() {
        return players;
    }

    private int players = 0;

    DropShadow shadow = new DropShadow();

    @FXML
    public void initialize() {
        System.out.println("Numbers Players");
        //connect.setOnAction(event -> readParameters());
        twoPlayers.setOnAction(event -> chooseCard2());
        threePlayers.setOnAction(event -> chooseCard3());
    }

    /**
     * method chooseCard2
     */
    public void chooseCard2() {
        this.players = 2;
        stage.close();
    }

    /**
     * NumberPlayers
     * @param stage .
     */
    public NumberPlayers(Stage stage) {
        this.stage = stage;
    }

    /**
     * chooseCard3
     */
    public void chooseCard3() {
        this.players = 3;
        stage.close();

    }


    @FXML
    void threePlayersIn(MouseEvent event) {
        threePlayers.setEffect(shadow);
    }

    @FXML
    void threePlayersOut(MouseEvent event) {
        threePlayers.setEffect(null);
    }

    @FXML
    void twoPlayersIn(MouseEvent event) {
        twoPlayers.setEffect(shadow);
    }

    @FXML
    void twoPlayersOut(MouseEvent event) {
        twoPlayers.setEffect(null);
    }

    /**
     * method setStage
     * @param stage .
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /*private void chooseCard2() {
        stage.close();
        try {
            stage.setTitle("Carte Divinità");
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard2pl.fxml")));
//            ViewController controller = new ViewController();
//
//            controller.setHandlerClient(handlerClient);
//            handlerClient.setView(controller);
//
//            loader.setController(controller);
            ChooseCard2pl controller = new ChooseCard2pl();
            controller.setStage(stage);
            loader.setController(controller);
            Parent root = loader.load();
            Scene s2 = new Scene(root);
            stage.setScene(s2);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseCard3() {
        stage.close();
        try {
            stage.setTitle("Carte Divinità");
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard3pl.fxml")));
//            ViewController controller = new ViewController();
//
//            controller.setHandlerClient(handlerClient);
//            handlerClient.setView(controller);
//
//            loader.setController(controller);
            ChooseCard3pl controller2 = new ChooseCard3pl();
            controller2.setStage(stage);
            loader.setController(controller2);
            Parent root = loader.load();
            Scene s1 = new Scene(root);
            stage.setScene(s1);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

//    private void waitWindow() {
//        stage.close();
//        try {
//            stage.setTitle("Wait..");
//            //stage.getIcons().add(new Image("images/cm_boardgame.png"));
//            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("wait.fxml")));
////            ViewController controller = new ViewController();
////
////            controller.setHandlerClient(handlerClient);
////            handlerClient.setView(controller);
////
////            loader.setController(controller);
////            LoginUser2 controller = new LoginUser2();
////            controller.setStage(stage);
////            loader.setController(controller);
//            Wait controller = new Wait();
//            controller.setStage(stage);
//            loader.setController(controller);
//            Parent root = loader.load();
//            Scene s = new Scene(root);
//            stage.setScene(s);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

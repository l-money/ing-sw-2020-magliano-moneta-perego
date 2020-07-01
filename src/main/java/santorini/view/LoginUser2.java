package santorini.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import santorini.network.NetworkHandlerClient;

import java.io.IOException;
import java.util.Objects;

/**
 * Class LoginUser2
 */
/*
https://code.makery.ch/blog/javafx-dialogs-official/
*/

public class LoginUser2 {

    @FXML
    private TextField name, address;
    @FXML
    private Button connect;
    private NetworkHandlerClient handlerClient;

    private Stage stage;

    /**
     * initialization of user
     */
    @FXML
    public void initialize() {
        System.out.println("LoginUser");
        connect.setOnAction(event -> readParameters());
    }

//    private void windowPlayers() {
//        stage.close();
//
//        try {
//            System.out.println("Number Players");
//            stage.setTitle("Numero Giocatori");
//            Label label = new Label("Quanti giocatori partecipano alla partita?");
//            label.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
//            label.setAlignment(Pos.TOP_CENTER);
//            AnchorPane.setLeftAnchor(label, 60d);
//            AnchorPane layout = new AnchorPane();
//            //Bottone 2 Giocatori
//            Button buttonTwo = new Button("2");
//            buttonTwo.setStyle("-fx-font-size: 25");
//            buttonTwo.setPrefSize(50,30);
//            AnchorPane.setLeftAnchor(buttonTwo, 280d); // distance 0 from right side of
//            AnchorPane.setTopAnchor(buttonTwo, 70d);
//            buttonTwo.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        buttonTwo.setOnAction(event -> chooseCard2());
//                    }
//                });
//            //bottone 3 giocatori
//            Button buttonThree = new Button("3");
//            buttonThree.setStyle("-fx-font-size: 25");
//            buttonThree.setPrefSize(50,30);
//            AnchorPane.setRightAnchor(buttonThree, 280d);
//            AnchorPane.setTopAnchor(buttonThree, 70d);
//            buttonThree.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                    new EventHandler<MouseEvent>() {
//                        @Override
//                        public void handle(MouseEvent e) {
//                            buttonThree.setOnAction(event -> chooseCard3());
//                        }
//                    });
//            layout.getChildren().addAll(label, buttonTwo, buttonThree);
//            Scene scene2 = new Scene(layout, 730, 180);
//            stage.setScene(scene2);
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * method windowPlayers
     */
    private void windowPlayers() {
        try {
            /*Add here the file fxml of the stage to open*/
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("playTable.fxml")));
            ViewController vc = (ViewController) handlerClient.getView();
            vc.setThisStage(stage);
            loader.setController(vc);
            Parent root = loader.load();
            Scene s = new Scene(root);
            stage.setTitle("Tavolo Da Gioco");
            stage.setScene(s);
            stage.show();
            vc.waitDialog("Attendi...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method setStage
     *
     * @param stage .
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * method readParameters
     */
    public void readParameters() {
        System.out.println("BUTTON EVENT");
        String n = name.getText();
        String a = address.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di input");
        if (n.equals("") || a.equals("") || n.length() < 3) {
            alert.setHeaderText("Input non corretto");
            alert.showAndWait();
            return;
        }
        try {
            ViewController vc = new ViewController();
            handlerClient = new NetworkHandlerClient(a, n, vc);
            vc.setHandlerClient(handlerClient);
        } catch (IOException ex) {
            alert.setHeaderText("Connessione non riuscita!");
            alert.showAndWait();
            return;
        }
        /*alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifica di rete");
        alert.setHeaderText("Connessione stabilita con successo");
        alert.setContentText(null);
        alert.showAndWait();*/
        //stage.close();
        windowPlayers();
    }


//    public void avviaFinestra() {
//
//        stage.close();
//        try {
//            /*Aggiungi qua il file fxml della finestra da aprire*/
//            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("startGame.fxml")));
//            ViewController controller = new ViewController();
//
//            controller.setHandlerClient(handlerClient);
//            handlerClient.setView(controller);
//
//            loader.setController(controller);
//            Parent root = loader.load();
//            Scene s = new Scene(root);
//            stage.setScene(s);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


}
package santorini.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import santorini.NetworkHandlerClient;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
/*
ROBA BELLA
https://code.makery.ch/blog/javafx-dialogs-official/
*/

public class LoginUser2 {

    @FXML
    private TextField name, address;
    @FXML
    private Button connect;
    private NetworkHandlerClient handlerClient;

    private Stage stage;

    @FXML
    public void initialize() {
        System.out.println("LoginUser");
        connect.setOnAction(event -> readParameters());
        connect.setOnAction(event -> windowPlayers());
    }

    //prova finestra da aprire: FUNZIONA
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

    private void windowPlayers() {
        stage.close();
        try {
            stage.setTitle("Numero Giocatori");
            //stage.getIcons().add(new Image("images/cm_boardgame.png"));
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("numberPlayers.fxml")));
//            ViewController controller = new ViewController();
//
//            controller.setHandlerClient(handlerClient);
//            handlerClient.setView(controller);
//
//            loader.setController(controller);
//            LoginUser2 controller = new LoginUser2();
//            controller.setStage(stage);
//            loader.setController(controller);
            NumberPlayers controller = new NumberPlayers();
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


    public void setStage(Stage stage) {
        this.stage = stage;
    }

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
            handlerClient = new NetworkHandlerClient(a, n, null);
        } catch (IOException ex) {
            alert.setHeaderText("Connessione non riuscita!");
            alert.showAndWait();
            return;
        }
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifica di rete");
        alert.setHeaderText("Connessione stabilita con successo");
        alert.setContentText(null);

        alert.showAndWait();
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
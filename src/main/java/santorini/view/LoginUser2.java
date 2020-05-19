package santorini.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import santorini.NetworkHandlerClient;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    public void initialize() {
        System.out.println("INITIALIZE");
        connect.setOnAction(event -> readParameters());
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

}
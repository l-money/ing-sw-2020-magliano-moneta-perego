package santorini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import santorini.view.CLIView;
import santorini.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] a) {
        //System.out.println(a[0]);
        if (a.length > 0 && a[0].equals("--cli")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String address = null, name = null;
            NetworkHandlerClient handlerClient = null;
            try {
                System.out.print("Inserisci indirizzo server: ");
                address = br.readLine();
                System.out.print("Inserisci il tuo nome: ");
                name = br.readLine();
                View v = new CLIView();
                handlerClient = new NetworkHandlerClient(address, name, v);
                v.setHandlerClient(handlerClient);
            } catch (IOException ex) {
                System.out.println("Connessione fallita");
                System.exit(1);
            }

        } else if (a.length > 0 && a[0].equals("--server")) {
            try {
                System.out.println("Starting server...");
                NetworkHandlerServer handlerServer = new NetworkHandlerServer();
                handlerServer.initGameConnections();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            launch(a);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("####\t" + getClass().getClassLoader().getResource("loginuser2.fxml"));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("loginuser2.fxml")));
        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.show();
    }
}
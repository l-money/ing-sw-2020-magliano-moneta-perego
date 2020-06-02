package santorini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import santorini.view.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main extends Application {
    public static boolean test = false;
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
                while (true) {
                    System.out.println("New Match created");
                    NetworkHandlerServer handlerServer = new NetworkHandlerServer();
                    handlerServer.initGameConnections();
                    System.out.println("New Match started");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (a.length > 0 && a[0].equals("--test")) {
            test = true;
            launch(a);
        } else {
            launch(a);
        }
    }
/*
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("####\t" + getClass().getClassLoader().getResource("loginuser2.fxml"));
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("loginuser2.fxml")));
        LoginUser2 controller = new LoginUser2();
        controller.setStage(primaryStage);
        loader.setController(controller);
        Parent root = loader.load();
        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.show();
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (test) {
            FXMLLoader lo = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("playTable.fxml")));
            /*Metti come controller un controller di test*/
            TestTable controller = new TestTable();
            /*TestController tc = new TestController(/**EVENTUALI ARGS)*/
            controller.setStage(primaryStage);
            /*lo.setController(tc);*/
            lo.setController(controller);
            Parent root = lo.load();
            Scene s = new Scene(root);
            primaryStage.setScene(s);
            primaryStage.show();
        } else {
            primaryStage.setTitle("Start Game");
            primaryStage.getIcons().add(new Image("images/cm_boardgame.png"));
            System.out.println("####\t" + getClass().getClassLoader().getResource("startGame.fxml"));
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("startGame.fxml")));
            StartGame controller = new StartGame();
            controller.setStage(primaryStage);
            loader.setController(controller);
            Parent root = loader.load();
            Scene s = new Scene(root);
            primaryStage.setScene(s);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }
}
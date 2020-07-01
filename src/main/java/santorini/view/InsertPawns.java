package santorini.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class InsertPawns
 */

public class InsertPawns {

    @FXML
    private TextField coordPawn0;

    @FXML
    private TextField coordPawn1;

    @FXML
    private Button playPawns;
    private Stage stage;

    /**
     * initialization of pawns
     */
    public void initialize() {
        System.out.println("Inserisci Pedine");
        playPawns.setOnAction(event -> avviaTable());
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
     * method avviaTable
     * starts the table of the game
     */
    private void avviaTable() {
        stage.close();
        try {
            stage.setTitle("Tavolo da gioco");
            //stage.getIcons().add(new Image("images/cm_boardgame.png"));
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("playTable.fxml")));
//            ViewController controller = new ViewController();
//
//            controller.setHandlerClient(handlerClient);
//            handlerClient.setView(controller);
//
//            loader.setController(controller);
//            LoginUser2 controller = new LoginUser2();
//            controller.setStage(stage);
//            loader.setController(controller);
            PlayTable controller = new PlayTable();
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
}

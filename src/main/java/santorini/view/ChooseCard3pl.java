package santorini.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import santorini.model.godCards.God;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChooseCard3pl implements CardChoice {

    @FXML
    private Button changeBut;

    @FXML
    private ImageView godPower;

    @FXML
    private Button inserisciPedine;

    @FXML
    private ImageView god1;

    @FXML
    private ImageView god2;

    private Stage stage;
    private List<String> images;
    private God choosed;

    DropShadow shadow = new DropShadow();


    @FXML
    public void initialize() {
        System.out.println("Choose 3 cards");
//        loadImages(new File("Project_Images/Project_Images/GodCards"));
//        changeBut.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent ae) {
//                god2.setImage(loadRandomImages());
//            }
//        });
//    }
//
//    File file = new File("Project_Images/Project_Images/GodCards");
//    Image image = new Image(file.toURI().toString());
//    ImageView iv = new ImageView(image);
//
//    private Image loadRandomImages() {
//        int countImages = images.size();
//        int imageNumber = (int) (Math.random() * countImages);
//
//        String image = images.get(imageNumber);
//        return new Image(image);
//    }
//
//    private void loadImages(final file directory) {
//        if(images == null) {
//            images = new ArrayList<String>();
//        } else {
//            images.clear();
//        }
//
//        File[] files = directory.listFiles();
//        for(File f : files) {
//            if(f.isDirectory()) {
//                loadImages(f);
//            } else {
//                images.add(f.getName());
//            }
//        }
//    }
//
//}
    }

    @FXML
    void clickGod1(MouseEvent event) {

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(0.4);
        colorAdjust.setHue(-0.05);
        colorAdjust.setBrightness(0.9);
        colorAdjust.setSaturation(0.8);
        god1.setEffect(colorAdjust);

    }

    @FXML
    void imageGod1In(MouseEvent event) {
        god1.setEffect(shadow);
        Tooltip.install(god1, new Tooltip("\nQuesta è la descrizione del tuo dio\n" +
                "qui puoi inserirci tutto quello che vuoi\n" +
                "e associarlo ad un'icona laterale\n"));

//        File file = new File("ApolloPower.png");
//        Image image = new Image(file.toURI().toString());
//        godPower.setImage(image);
    }

    @FXML
    void imageGod1Out(MouseEvent event) {
        god1.setEffect(null);
    }

    @FXML
    void inserisciPedine(MouseEvent event) {

        stage.close();
        try {
            stage.setTitle("Inserisci pedine");
            //stage.getIcons().add(new Image("images/cm_boardgame.png"));
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("insertPawns.fxml")));
//            ViewController controller = new ViewController();
//
//            controller.setHandlerClient(handlerClient);
//            handlerClient.setView(controller);
//
//            loader.setController(controller);
//            LoginUser2 controller = new LoginUser2();
//            controller.setStage(stage);
//            loader.setController(controller);
            InsertPawns controller = new InsertPawns();
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

    @Override
    public God getChoosed() {
        return this.choosed;
    }
}

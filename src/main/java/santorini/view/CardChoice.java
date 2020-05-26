package santorini.view;

import javafx.stage.Stage;
import santorini.model.godCards.God;

public interface CardChoice {
    public God getChoosed();

    public void setStage(Stage stage);
}

package santorini.view;

import javafx.stage.Stage;
import santorini.model.godCards.God;

/**
 * Class CarsChoice
 */

public interface CardChoice {
    /**
     * method getChoosed
     *
     * @return God card choose
     */
    public God getChoosed();

    /**
     * method setStage
     *
     * @param stage
     */
    public void setStage(Stage stage);
}

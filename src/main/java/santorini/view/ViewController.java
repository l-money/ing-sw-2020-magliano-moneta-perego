package santorini.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import santorini.model.Cell;
import santorini.model.Mossa;
import santorini.model.Table;
import santorini.model.godCards.God;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class ViewController
 */

public class ViewController extends View {

    private Stage thisStage;
    private Stage overlayedStage;
    private boolean firstAction = false;
    private boolean initialized = false;
    @FXML
    private GridPane gridPane;

    @FXML
    private Button jumpMove;

    @FXML
    private TextArea textArea;

    private Cell c = null;

    @FXML
    private Label userName;

    @FXML
    private Button submitAction;

    @FXML
    private Button buttonPl1;

    @FXML
    private Button buttonPl2;

    @FXML
    private Button buttonPl3;

    @FXML
    private Button movePawn;

    @FXML
    private Button buildPawn;

    @FXML
    private Button stopPawn;

    @FXML
    private ImageView firstPl;

    @FXML
    private ImageView secondPl;

    @FXML
    private ImageView thirdPl;

    private Button[][] bt = new Button[5][5];

    private Mossa currentMove = null;

    private int pawnPlacedCounter = 0;
    private String initCoords = "";
    private DropShadow shadow = new DropShadow();

    /**
     * constructor of ViewController
     */
    public ViewController() {

    }

    /**
     * method setThisStage
     *
     * @param thisStage
     */
    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    /**
     * Initializes all components of this stage
     */
    @FXML
    public void initialize() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt[i][j] = new Button();
                bt[i][j].setAlignment(Pos.BOTTOM_CENTER);
                bt[i][j].setBackground(Background.EMPTY);
                bt[i][j].setMaxSize(65, 65);
                bt[i][j].setMinSize(65, 65);
                gridPane.add(bt[i][j], j, i);
                GridPane.setHalignment(bt[i][j], HPos.CENTER);
                GridPane.setValignment(bt[i][j], VPos.CENTER);
            }
        }
        submitAction.setDisable(true);
        jumpMove.setDisable(true);
        submitAction.setOnAction(event -> {
            try {
                System.out.println("Dettagli mossa: ");
                System.out.println("Coords target:   " + currentMove.getTargetX() + ", " + currentMove.getTargetY());
                System.out.println("Pawn:  " + currentMove.getIdPawn());
                System.out.println("Action: " + currentMove.getAction());
                disableButtons(true);
                handlerClient.sendAction(this.currentMove);
                submitAction.setDisable(true);
                lightPause();

            } catch (IOException e) {
                setFailed("Errore di rete");
                System.exit(1);
            } finally {
                jumpMove.setDisable(true);
                disableButtons(true);
            }
        });
        jumpMove.setOnAction(ev -> {
            try {
                handlerClient.sendAction(new Mossa(currentMove.getAction(), -1, -1, -1));
            } catch (IOException e) {
                setFailed("Errore di rete");
                System.exit(1);
            } finally {
                jumpMove.setDisable(true);
                effetto = false;
                disableButtons(true);
                lightPause();
            }
        });

        firstPl.setEffect(shadow);
        textArea.setEffect(shadow);
        movePawn.setEffect(shadow);
        buildPawn.setEffect(shadow);
        stopPawn.setEffect(shadow);
        submitAction.setEffect(shadow);
        jumpMove.setEffect(shadow);
        lightPause();

    }

    /**
     * method lightMove
     * lights the button Move
     */
    public void lightMove() {
        movePawn.setStyle("-fx-background-color: YELLOW;"
                + "-fx-background-radius: 30;"
                + "-fx-border-radius: 30;"
                + "-fx-border-color: 363507;");
        buildPawn.setStyle("-fx-background-color: null");
        stopPawn.setStyle("-fx-background-color: null");
    }

    /**
     * method lightBuild
     * lights the button Build
     */
    public void lightBuild() {
        buildPawn.setStyle("-fx-background-color: YELLOW;"
                + "-fx-background-radius: 30;"
                + "-fx-border-radius: 30;"
                + "-fx-border-color: 363507;");
        movePawn.setStyle("-fx-background-color: null");
        stopPawn.setStyle("-fx-background-color: null");
    }

    /**
     * method lightPause
     * lights the button Pause
     */
    public void lightPause() {
        stopPawn.setStyle("-fx-background-color: RED;"
                + "-fx-background-radius: 30;"
                + "-fx-border-radius: 30;"
                + "-fx-border-color: 363507;");
        movePawn.setStyle("-fx-background-color: null");
        buildPawn.setStyle("-fx-background-color: null");
    }

    /**
     * method startTable
     * sets up listener to set initial pawn positions
     *
     * @param t  table
     * @param bt buttons array referred to table
     */
    public void startTable(Table t, Button[][] bt) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (t.getTableCell(i, j).getPawn() == null) {
                    if (!table.getTableCell(i, j).isFree()) {
                        continue;
                    }
                    bt[i][j].setOnMousePressed(e -> {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setOnMouseEntered(null);
                        bt[x][y].setOnMouseExited(null);
                        bt[x][y].setStyle("-fx-border-color:blue");
                        System.out.println("x:" + x + "\ty:" + y);
                    });
                    bt[i][j].setOnMouseEntered(e -> {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setStyle("-fx-border-color:yellow");
                    });
                    bt[i][j].setOnMouseExited(e -> {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setStyle("-fx-border-color:trasparent");
                    });
                    bt[i][j].setOnAction(e -> {
                        Button button;
                        System.out.println("Cliccato");
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setOnMousePressed(null);
                        bt[x][y].setStyle("-fx-border-color:red");
                        bt[x][y].setDisable(true);
                        pawnPlacedCounter++;
                        initCoords = initCoords + x + "," + y + ",";
                        if (pawnPlacedCounter == 2) {
                            try {
                                handlerClient.initializePawns(initCoords);
                                System.out.println("Invio coordinate al server:\n" + initCoords);
                                initButtons();
                                disableButtons(true);
                                lightPause();
                            } catch (IOException ioException) {
                                setFailed("Errore di rete");
                                System.exit(1);
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * method disableButtons
     * set all buttons in the grid pane disabled or not
     *
     * @param b disable set
     */
    private void disableButtons(boolean b) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt[i][j].setDisable(b);
                bt[i][j].setStyle("-fx-border-color:transparent");
            }
        }
    }


    /**
     * method chooseCards
     * creates a new dialog window that asks to user which god cards
     * want to use
     *
     * @param gods available god cards
     */
    @Override
    public void chooseCards(ArrayList<God> gods) {
        System.out.println("Scelta della carta size: " + gods.size());
        Platform.runLater(() -> {
            userName.setText(getName());
            overlayedStage.close();
            System.out.println("Scelta della carta");
            Stage dialog = new Stage();
            dialog.setTitle("Scelta Carte Divinità");
            dialog.getIcons().add(new Image("images/cm_boardgame.png"));
            Parent root;
            FXMLLoader loader = null;
            CardChoice cc = null;
            try {
                switch (gods.size()) {
                    case 1:
                        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard1pl.fxml")));
                        cc = new ChooseCard1pl(gods);
                        cc.setStage(dialog);
                        loader.setController(cc);
                        break;
                    case 2:
                        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard2pl.fxml")));
                        cc = new ChooseCard2pl(gods);
                        cc.setStage(dialog);
                        loader.setController(cc);
                        break;
                    case 3:
                        loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("chooseCard3pl.fxml")));
                        cc = new ChooseCard3pl(gods);
                        cc.setStage(dialog);
                        loader.setController(cc);
                        break;
                    default:
                        return;
                }
                root = loader.load();
                Scene s = new Scene(root);
                dialog.setResizable(false);
                dialog.setScene(s);
                dialog.initOwner(thisStage);
                dialog.initModality(Modality.APPLICATION_MODAL);
                //dialog.setOnCloseRequest(event -> returnNumber(numberPlayersController));
                dialog.showAndWait();
                handlerClient.setCard(cc.getChoosed());
                setGod(cc.getChoosed());
            } catch (IOException e) {
                e.printStackTrace();
            }
            God g = getGod();
            System.out.println(g.getDescription());
            buttonPl1.setTooltip(new Tooltip(
                    g.getName() + "\n" +
                            g.getDescription()));
            Image i = new Image("images/GodCards/" + g.getName() + ".png", 172, 109, true, false);
            firstPl.setImage(i);
        });
    }

    /**
     * method initButtons
     * initializes buttons with default actions to do during the match
     */
    public void initButtons() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt[i][j].setOnMouseClicked(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    for (int a = 0; a < 5; a++) {
                        for (int b = 0; b < 5; b++) {
                            if (a == x && b == y) {
                                bt[a][b].setStyle("-fx-border-color:red");
                            } else {
                                bt[a][b].setStyle("-fx-border-color:trasparent");
                            }
                        }
                    }
                    bt[x][y].setStyle("-fx-border-color:red");
                    System.out.println("x:" + x + "\ty:" + y);
                });
                bt[i][j].setOnMouseEntered(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    bt[x][y].setStyle("-fx-border-color:yellow");
                });
                bt[i][j].setOnMouseExited(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    bt[x][y].setStyle("-fx-border-color:trasparent");
                });
                bt[i][j].setOnAction(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    //bt[x][y].setStyle("-fx-border-color:red");
                    aggiornaMossa(table.getTableCell(x, y));
                    Platform.runLater(() -> {
                        submitAction.setDisable(false);
                    });
                });
            }
        }
    }

    /**
     * method aggiornaMossa
     * updates current action with parameters given by user
     *
     * @param cella Target cell of the action
     */
    public void aggiornaMossa(Cell cella) {
        currentMove.setIdPawn(currentPawn);
        currentMove.setTargetX(cella.getX());
        currentMove.setTargetY(cella.getY());
        Platform.runLater(() -> {
//            jumpMove.setDisable(true);
            submitAction.setDisable(true);
        });

    }

    /**
     * method setNewAction
     * Enables action request from window to user
     *
     * @param action type of action (MOVE or BUILD)
     */
    @Override
    public void setNewAction(Mossa.Action action) {
        jumpMove.setDisable(false);
        Platform.runLater(() -> {
            submitAction.setDisable(true);

        });
        switch (action) {
            case BUILD:
                lightBuild();
                if (!inTurno) {
                    lightMyPawns();
                    inTurno = true;
                    firstAction = true;
                } else {
                    c = table.getTableCell(table.getXYPawn(getID(), currentPawn, true),
                            table.getXYPawn(getID(), currentPawn, false));
                    firstAction = false;
                    lightAvailable(c, bt[table.getXYPawn(getID(), currentPawn, true)][table.getXYPawn(getID(), currentPawn, false)]);

                }
                currentMove = new Mossa();
                currentMove.setAction(action);

                break;
            case MOVE:
                lightMove();
                currentMove = new Mossa();
                currentMove.setAction(action);
                if (!inTurno) {
                    effetto = true;
                    lightMyPawns();
                    inTurno = true;
                    firstAction = true;
                } else {
                    firstAction = false;
                    c = table.getTableCell(table.getXYPawn(getID(), currentPawn, true),
                            table.getXYPawn(getID(), currentPawn, false));
                    lightAvailable(c, bt[table.getXYPawn(getID(), currentPawn, true)][table.getXYPawn(getID(), currentPawn, false)]);//prima era true

                }
        }
    }


    /**
     * method setNumeroGiocatori
     * creates a dialog window that make user to decide the
     * number of players in this match
     */
    @Override
    public void setNumeroGiocatori() {
        Platform.runLater(() -> {
            overlayedStage.close();
            Stage dialog = new Stage();
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("numberPlayers.fxml")));
                NumberPlayers numberPlayersController = new NumberPlayers(dialog);
                loader.setController(numberPlayersController);
                root = loader.load();
                Scene s = new Scene(root);
                dialog.setTitle("Numero Giocatori");
                dialog.getIcons().add(new Image("images/cm_boardgame.png"));
                dialog.setResizable(false);
                dialog.setScene(s);
                dialog.initOwner(thisStage);
                dialog.initModality(Modality.APPLICATION_MODAL);
                //dialog.setOnCloseRequest(event -> returnNumber(numberPlayersController));
                dialog.showAndWait();
                handlerClient.setPartecipanti(numberPlayersController.getPlayers());
                waitDialog("Attendi...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * method waitDialog
     * creates a wait dialog
     *
     * @param title .
     */
    public void waitDialog(String title) {
        Stage dialog = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("wait.fxml")));
            Scene s1 = new Scene(root);
            dialog.setScene(s1);
            overlayedStage = dialog;
            overlayedStage.initOwner(thisStage);
            overlayedStage.initModality(Modality.APPLICATION_MODAL);
            overlayedStage.setTitle(title);
            overlayedStage.setResizable(false);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * method setFailed
     * creates a dialog to notify an error to user
     *
     * @param msg message contained in the dialog
     */
    @Override
    public void setFailed(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di input");
            alert.setHeaderText(msg);
            alert.showAndWait();
        });
        if (firstAction) {
            inTurno = false;
        }

    }

    /**
     * method  editString
     * removes ANSI color tag from a string
     *
     * @param s input string
     * @return string without ANSI
     */
    private String editString(String s) {
        s = s.replace("[31m", "");
        s = s.replace("[0m", "");
        s = s.replace("[33m", "");
        s = s.replace("[36m", "");
        s = s.replace("[34m", "");
        s = s.replace("*", "");
        s = s.replace("|", "");
        //s = s.replace("]", "]\n");
        s = s.replace("\t", "\n");


        return s;
    }

    /**
     * method printMessage
     * show messages provided from server to player
     * message are shown in a text area in main window
     *
     * @param msg message to send to user
     */
    @Override
    public void printMessage(String msg) {
        if (msg.contains("Turno di :")) {
            inTurno = false;
            for (boolean b : pawnEnabled) {
                b = true;
            }
        }
        msg = editString(msg);
        if (textArea != null) {
            textArea.appendText(msg + "\n");
        }
    }

    /**
     * method setInitializePawn
     * handle the server request to place the initial position of user pawns
     */
    @Override
    public void setInitializePawn() {
        //overlayedStage.close();
        System.out.println("-Welcome-");
        lightMove();
        startTable(this.getTable(), bt);
    }

    /**
     * method vittoria
     * notify to user that have won this match
     */
    @Override
    public void vittoria() {
        Platform.runLater(() -> {
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hai vinto");
            alert.setHeaderText("Fine della partita");
            alert.showAndWait();*/
            Parent root = null;
            Stage dialog = new Stage();
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("winner.fxml")));
                Scene s = new Scene(root);
                dialog.setScene(s);
                overlayedStage = dialog;
                overlayedStage.initOwner(thisStage);
                overlayedStage.initModality(Modality.APPLICATION_MODAL);
                overlayedStage.setResizable(false);
                overlayedStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        try {
            handlerClient.getServer().close();
        } catch (IOException e) {
            System.out.println();
        }
    }

    /**
     * method sconfitta
     * notify to user that has lost the match and who is the winner
     *
     * @param winner winner's name
     */
    @Override
    public void sconfitta(String winner) {
        Platform.runLater(() -> {
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hai perso");
            alert.setHeaderText("Ha vinto " + winner + "\nFine della partita");
            alert.showAndWait();*/
            Parent root = null;
            Stage dialog = new Stage();
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("loser.fxml")));
                Scene s = new Scene(root);
                dialog.setScene(s);
                overlayedStage = dialog;
                overlayedStage.initOwner(thisStage);
                overlayedStage.initModality(Modality.APPLICATION_MODAL);
                overlayedStage.setResizable(false);
                overlayedStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        try {
            handlerClient.getServer().close();
        } catch (IOException e) {
            System.out.println();
        }
    }

    /**
     * method networkError
     * notify to user that a player has just disconnected
     *
     * @param player disconnected player's name
     */
    @Override
    public void networkError(String player) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di rete");
            alert.setHeaderText("Partita terminata\n" + player + " si è disconnesso");
            alert.showAndWait();
            System.exit(1);
        });

    }

    /**
     * method printTable
     * update table status on main stage
     */
    @Override
    public void printTable() {
        Platform.runLater(() -> {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (table.getTableCell(i, j).isComplete()) {
                        BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Levels/L" + table.getTableCell(i, j).getLevel() + "+Dome.png")), 75, 75, true, false),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT);
                        bt[i][j].setBackground(new Background(myBI));
                    } else if (table.getTableCell(i, j).getPawn() != null) {
                        BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/LevelAndPawns/L" + table.getTableCell(i, j).getLevel() + "+" + table.getTableCell(i, j).getPawn().getIdGamer() + "+" + table.getTableCell(i, j).getPawn().getIdPawn() + ".png")), 75, 75, true, false),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT);
                        bt[i][j].setBackground(new Background(myBI));
                    } else if (table.getTableCell(i, j).getLevel() != 0) {
                        BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Levels/L" + table.getTableCell(i, j).getLevel() + ".png")), 75, 75, true, false),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT);
                        bt[i][j].setBackground(new Background(myBI));
                    } else {
                        bt[i][j].setBackground(Background.EMPTY);
                    }
                }
            }
        });
    }

    /**
     * method lightMyPawns
     * highlights current user's pawns
     */
    public void lightMyPawns() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Cell cell = table.getTableCell(i, j);
                if (cell.getPawn() != null && cell.getPawn().getIdGamer() == getID() && cell.getPawn().getICanPlay()) {
                    bt[i][j].setStyle("-fx-border-color:red");
                    bt[i][j].setOnMouseEntered(e -> {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setStyle("-fx-border-color:yellow");
                    });
                    bt[i][j].setOnMouseExited(e -> {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setStyle("-fx-border-color:red");
                    });
                    bt[i][j].setDisable(false);
                    bt[i][j].setOnAction(e -> {
                        Button bOne;
                        bOne = (Button) e.getSource();
                        int x = GridPane.getRowIndex(bOne);
                        int y = GridPane.getColumnIndex(bOne);
                        for (int i1 = 0; i1 < 5; i1++) {
                            for (int j1 = 0; j1 < 5; j1++) {
                                Cell cell1 = table.getTableCell(i1, j1);
                                if (cell1.getPawn() != null && cell1.getPawn().getIdGamer() == getID()) {
                                    if (i1 == x && j1 == y) {
                                        bt[i1][j1].setStyle("-fx-border-color:red");
                                        bt[i1][j1].setOnMouseClicked(null);
                                        bt[i1][j1].setOnAction(null);
                                        bt[i1][j1].setOnMouseEntered(null);
                                        bt[i1][j1].setOnMouseExited(null);

                                    } else {
                                        bt[i1][j1].setStyle("-fx-border-color:transparent");
                                        bt[i1][j1].setDisable(true);
                                    }
                                }
                            }
                        }

                        System.out.print("***Id pedina: " + table.getTableCell(x, y).getPawn().getIdPawn());
                        System.out.println("\tId Giocatore: " + table.getTableCell(x, y).getPawn().getIdGamer() + "***\n");
                        currentMove.setIdPawn(table.getTableCell(x, y).getPawn().getIdPawn());
                        currentPawn = currentMove.getIdPawn();
                        lightAvailable(table.getTableCell(x, y), bOne);
                    });
                }
            }
        }
    }

    /**
     * method lightAvailable
     * shows adjacent cells for movement or building
     * @param myCell my current position
     * @param myButton button of the current position
     */
    private void lightAvailable(Cell myCell, Button myButton) {
        Platform.runLater(() -> {
            ArrayList<Cell> cells = new ArrayList<>();
            int x = myCell.getX();
            int y = myCell.getY();
            myButton.setStyle("-fx-border-color:red");
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    //control if the cell exists
                    if (((i != x) || (j != y)) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                        //control there is not dome
                        if ((!getTable().getTableCell(i, j).isComplete())) {
                            //control there is not a pawn of my same team
                            if (!((getTable().getTableCell(i, j).getPawn() != null) &&
                                    (getTable().getTableCell(i, j).getPawn().getIdGamer() == myCell.getPawn().getIdGamer()))) {
                                cells.add(getTable().getTableCell(i, j));
                            }
                        }
                    }
                }
                if (getGod().getName().equalsIgnoreCase("zeus") && effetto && currentMove.getAction() == Mossa.Action.BUILD) {
                    myButton.setStyle("-fx-border-color:blue");
                    myButton.setDisable(false);
                    myButton.setOnMouseEntered(e -> {
                        Button button;
                        button = (Button) e.getSource();
                        button.setStyle("-fx-border-color:yellow");
                    });
                    myButton.setOnMouseExited(e -> {
                        Button button;
                        button = (Button) e.getSource();
                        button.setStyle("-fx-border-color:blue");
                    });
                } else {
                    for (Cell lightMe : cells) {
                        int a = lightMe.getX();
                        int b = lightMe.getY();
                        bt[lightMe.getX()][lightMe.getY()].setStyle("-fx-border-color:yellow");
                        bt[lightMe.getX()][lightMe.getY()].setDisable(false);
                        bt[a][b].setOnMouseEntered(e -> {
                            Button button;
                            button = (Button) e.getSource();
                            button.setStyle("-fx-border-color:yellow");
                        });
                        bt[a][b].setOnMouseExited(e -> {
                            Button button;
                            button = (Button) e.getSource();
                            button.setStyle("-fx-border-color:trasparent");
                        });
                        bt[a][b].setOnAction(e -> {
                            Button button;
                            button = (Button) e.getSource();
                            button.setStyle("-fx-border-color:red");
                            bt[x][y].setOnMouseClicked(null);
                            int x1 = GridPane.getRowIndex(button);
                            int y1 = GridPane.getColumnIndex(button);
                            aggiornaMossa(table.getTableCell(x1, y1));
                            Platform.runLater(() -> {
                                submitAction.setDisable(false);
                            });
                        });
                    }
                }
            }
        });
    }
}



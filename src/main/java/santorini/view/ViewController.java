package santorini.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    public ViewController() {

    }

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
            }
        });

        firstPl.setEffect(shadow);
        secondPl.setEffect(shadow);
        thirdPl.setEffect(shadow);
        textArea.setEffect(shadow);
        movePawn.setEffect(shadow);
        buildPawn.setEffect(shadow);
        stopPawn.setEffect(shadow);
        submitAction.setEffect(shadow);
        jumpMove.setEffect(shadow);

    }

    public void lightMove() {
        movePawn.setStyle("-fx-background-color: YELLOW");
    }

    public void lightBuild() {
        buildPawn.setStyle("-fx-background-color: YELLOW");
    }

    public void lightPause() {
        stopPawn.setStyle("-fx-background-color: RED");
    }

    /**
     * Sets up listener to set initial pawn positions
     *
     * @param t  table
     * @param bt buttons array referred to table
     */
    public void startTable(Table t, Button[][] bt) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
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
                    pawnPlacedCounter++;
                    initCoords = initCoords + x + "," + y + ",";
                    if (pawnPlacedCounter == 2) {
                        try {
                            handlerClient.initializePawns(initCoords);
                            System.out.println("Invio coordinate al server:\n" + initCoords);
                            initButtons();
                            disableButtons(true);
                        } catch (IOException ioException) {
                            setFailed("Errore di rete");
                            System.exit(1);
                        }
                    }
                });
            }
        }
    }

    /**
     * Set all buttons in the grid pane disabled or not
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
     * Creates a new dialog window that asks to user which god cards
     * want to use
     *
     * @param gods available god cards
     */
    @Override
    public void chooseCards(ArrayList<God> gods) {
        System.out.println("Scelta della carta size: " + gods.size());
        Platform.runLater(() -> {
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

        });

        God g = getGod();
        buttonPl1.setTooltip(new Tooltip(
                "g.getName()" +
                        "g.getDescription()"));
        buttonPl2.setTooltip(new Tooltip(
                "g.getName()" +
                        "g.getDescription()"));
        buttonPl3.setTooltip(new Tooltip(
                "g.getName()" +
                        "g.getDescription()"));


    }

    /**
     * Initializes buttons with default actions to do during the match
     */
    public void initButtons() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt[i][j].setOnMousePressed(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
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
     * Updates current action with parameters given by user
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
                    lightAvailable(c, bt[table.getXYPawn(getID(), currentPawn, true)][table.getXYPawn(getID(), currentPawn, true)]);

                }
                currentMove = new Mossa();
                currentMove.setAction(action);

                break;
            case MOVE:
                lightMove();
                effetto = true;
                currentMove = new Mossa();
                currentMove.setAction(action);
                if (!inTurno) {
                    lightMyPawns();
                    inTurno = true;
                    firstAction = true;
                } else {
                    firstAction = false;
                    c = table.getTableCell(table.getXYPawn(getID(), currentPawn, true),
                            table.getXYPawn(getID(), currentPawn, false));
                    lightAvailable(c, bt[table.getXYPawn(getID(), currentPawn, true)][table.getXYPawn(getID(), currentPawn, true)]);

                }
        }
    }


    /**
     * Creates a dialog window that make user to decide the
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
     * Creates a wait dialog
     *
     * @param title
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
     * Creates a dialog to notify an error to user
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
     * Removes ANSI color tag from a string
     * DA SISTEMARE
     *
     * @param s input string
     * @return string without ANSI
     */
    private String editString(String s) {
        String[] arr = s.split("\\[");
        StringBuilder result = new StringBuilder();
        for (String f : arr) {
            String g = "";
            try {
                g = f.substring(3);
            } catch (StringIndexOutOfBoundsException ex) {
                continue;
            }
            result.append(g + "\n");
        }
        return result.toString();
    }

    /**
     * Show messages provided from server to player
     * Message are shown in a text area in main window
     *
     * @param msg message to send to user
     */
    @Override
    public void printMessage(String msg) {
        if (msg.contains("Turno di :")) {
            inTurno = false;
        }
        msg = editString(msg);
        if (textArea != null) {
            textArea.appendText(msg);
        }
    }

    /**
     * Handle the server request to place the initial position of user pawns
     */
    @Override
    public void setInitializePawn() {
        //overlayedStage.close();
        System.out.println("ciaociao");
        startTable(this.getTable(), bt);
    }

    /**
     * Notify to user that have won this match
     */
    @Override
    public void vittoria() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hai vinto");
            alert.setHeaderText("Fine della partita");
            alert.showAndWait();
            System.exit(0);
        });

    }

    /**
     * Notify to user that has lost the match and who is the winnner
     *
     * @param winner winner's name
     */
    @Override
    public void sconfitta(String winner) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hai perso");
            alert.setHeaderText("Ha vinto " + winner + "\nFine della partita");
            alert.showAndWait();
            System.exit(0);
        });
    }

    /**
     * Notify to user that a player has just disconnected
     *
     * @param player disconnected player's name
     */
    @Override
    public void networkError(String player) {
        setFailed("Errore di rete\n" + player + "si è disconnesso");
        System.exit(1);
    }

    /**
     * Update table status on main stage
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
                        //mettere immagine con livello + cupola con la seguente sintassi
                        //"images/.../I_" + t.getTableCell(i,j).getLevel() + "_" + t.getTableCell(i,j).getPawn().getIdGamer()" + "_" + t.getTableCell(i,j).getPawn().getIdPawn() + ".png"
                        BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/LevelAndPawns/L" + table.getTableCell(i, j).getLevel() + "+" + table.getTableCell(i, j).getPawn().getIdGamer() + "+" + table.getTableCell(i, j).getPawn().getIdPawn() + ".png")), 75, 75, true, false),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT);
                        bt[i][j].setBackground(new Background(myBI));
                    } else if (table.getTableCell(i, j).getLevel() != 0) {
                        //mettere immagine con solo livello con la seguente sintassi
                        //"images/.../I_" + t.getTableCell(i,j).getLevel() + ".png"
                        BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Levels/L" + table.getTableCell(i, j).getLevel() + ".png")), 75, 75, true, false),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                                BackgroundSize.DEFAULT);
                        bt[i][j].setBackground(new Background(myBI));
                    } else {
                        //mettere immagine vuota se il livello è 0
                        bt[i][j].setBackground(Background.EMPTY);
                    }
                }
            }
        });
    }

    /**
     * Highlights current user's pawns
     */
    public void lightMyPawns() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Cell cell = table.getTableCell(i, j);
                if (cell.getPawn() != null && cell.getPawn().getIdGamer() == getID() && cell.getPawn().getICanPlay()) {
                    //se: esiste la pedina nella cella && la pedina è della stessa squadra del gamer && la pedina può giocare
                    //rendere cliccabile e illuminata la cella
                    bt[i][j].setStyle("-fx-border-color:yellow");
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
                                    } else {
                                        bt[i1][j1].setStyle("-fx-border-color:blue");
                                    }
                                } else {
                                    bt[i1][j1].setStyle("-fx-border-color:transparent");
                                    bt[i1][j1].setDisable(true);
                                }
                            }
                        }

                        System.out.print("***Id pedina: " + table.getTableCell(x, y).getPawn().getIdPawn());
                        System.out.println("\tId Giocatore: " + table.getTableCell(x, y).getPawn().getIdGamer() + "***\n");
                        currentMove.setIdPawn(table.getTableCell(x, y).getPawn().getIdPawn());
                        currentPawn = currentMove.getIdPawn();
                        initButtons();
                        lightAvailable(table.getTableCell(x, y), bOne);
                    });
                }
            }
        }
    }

    /**
     * @param myCell
     * @param myButton
     */
    private void lightAvailable(Cell myCell, Button myButton) {
        ArrayList<Cell> cells = new ArrayList<>();
        int x = myCell.getX();
        int y = myCell.getY();
        myButton.setStyle("-fx-border-color:red");
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                //controllo se la casella esiste
                if (((i != x) || (j != y)) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                    //controllo se non c'è la cupola
                    if ((!getTable().getTableCell(i, j).isComplete())) {
                        cells.add(getTable().getTableCell(i, j));
                    }
                }
            }
        }
        for (Cell lightMe : cells) {
            bt[lightMe.getX()][lightMe.getY()].setStyle("-fx-border-color:yellow");
            bt[lightMe.getX()][lightMe.getY()].setDisable(false);
        }
    }

    /**
     * NON PIÙ USATO
     * Metodo che illumina le caselle adiacenti possibili al movimento
     *
     * @param t      tavolo da gioco
     * @param myCell posizione della pedina scelta
     * @param bt     tabella di immagini
     */
    //Chiamo il metodo in lightMyPawns
    public void accessibleCells(Table t, Cell myCell, Button[][] bt, Button myButton) {
        ArrayList<Cell> cells = new ArrayList<>();
        int x = myCell.getX();
        int y = myCell.getY();
        myButton.setStyle("-fx-border-color:red");
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                //controllo se la casella esiste
                if (((i != x) || (j != y)) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                    //controllo se non c'è la cupola
                    if ((!t.getTableCell(i, j).isComplete())) {
                        cells.add(t.getTableCell(i, j));
                    }
                }
            }
        }
        for (Cell lightMe : cells) {
            bt[lightMe.getX()][lightMe.getY()].setStyle("-fx-border-color:yellow");
            bt[lightMe.getX()][lightMe.getY()].setDisable(false);
            bt[lightMe.getX()][lightMe.getY()].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Button bTwo;
                    bTwo = (Button) e.getSource();
                    int x = GridPane.getRowIndex(bTwo);
                    int y = GridPane.getColumnIndex(bTwo);
                    bt[myCell.getX()][myCell.getY()].setStyle("-fx-border-color:red");
                    int l = cells.size();
                    for (int k = 0; k < l; k++) {
                        if (cells.get(k).getX() == x && cells.get(k).getY() == y) {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:red");
                        } else {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:yellow");
                        }
                    }
                    mySelection(bt, t.getTableCell(x, y), t);
                    currentMove.setTargetX(x);
                    currentMove.setTargetY(y);
                }
            });

        }
    }

    /**
     * NON PIÙ USATO
     * Metodo che printa la casella cliccata
     *
     * @param bt       tavola di bottoni
     * @param moveCell cella cliccata
     * @param t        table
     */
    public void mySelection(Button[][] bt, Cell moveCell, Table t) {
        Button buttonMovement;
        buttonMovement = bt[moveCell.getX()][moveCell.getY()];
        System.out.println("Row: " + GridPane.getRowIndex(buttonMovement));
        System.out.println("Column: " + GridPane.getColumnIndex(buttonMovement));
    }

    /**
     * Metodo Building
     * Dopo il movimento prende la casella in cui si è spostata la pedina (start) e cerca le caselle adaicenti per la costruzione
     *
     * @param t     tavolo
     * @param bt    tabella di bottoni
     * @param start nuova posizione della pedina
     */
    public void Building(Table t, Button[][] bt, Cell start) {
        System.out.println("**Costruzione**\n");
        int x = table.getXYPawn(getID(), currentPawn, true);
        int y = table.getXYPawn(getID(), currentPawn, false);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == x && j == y) {
                    bt[i][j].setStyle("-fx-border-color:white");
                } else {
                    bt[i][j].setStyle("-fx-border-color:trasparent");
                }
            }
        }
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                //controllo se la casella esiste
                if (((i != x) || (j != y)) && (i >= 0) && (i <= 4) && (j >= 0) && (j <= 4)) {
                    //controllo se non c'è la cupola
                    if ((!t.getTableCell(i, j).isComplete())) {
                        cells.add(t.getTableCell(i, j));
                    }
                }
            }
        }
        if (getGod().getName().equalsIgnoreCase("zeus") && effetto) {
            bt[x][y].setStyle("-fx-border-color:blue");
            bt[x][y].setDisable(false);
            bt[x][y].setOnAction(ev -> {
                Button b = (Button) ev.getSource();
                currentMove.setTargetX(GridPane.getRowIndex(b));
                currentMove.setTargetY(GridPane.getColumnIndex(b));
            });
        } else {
            for (Cell lightMe : cells) {
                bt[lightMe.getX()][lightMe.getY()].setStyle("-fx-border-color:blue");
                bt[lightMe.getX()][lightMe.getY()].setDisable(false);
                bt[lightMe.getX()][lightMe.getY()].setOnMouseClicked(e -> {
                    Button bTwo;
                    bTwo = (Button) e.getSource();
                    int xs = GridPane.getRowIndex(bTwo);
                    int ys = GridPane.getColumnIndex(bTwo);
                    bt[start.getX()][start.getY()].setStyle("-fx-border-color:white");
                    //bt[start.getX()][start.getY()].setOnMouseClicked(null);
                    int l = cells.size();
                    for (int k = 0; k < l; k++) {
                        if (cells.get(k).getX() == xs && cells.get(k).getY() == ys) {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:red");
                        } else {
                            bt[cells.get(k).getX()][cells.get(k).getY()].setStyle("-fx-border-color:blue");
                        }
                    }
                    mySelection(bt, t.getTableCell(xs, ys), t);
                    currentMove.setTargetX(xs);
                    currentMove.setTargetY(ys);
                });
            }
        }

    }
}

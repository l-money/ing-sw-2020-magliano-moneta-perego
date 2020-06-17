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
    @FXML
    private GridPane gridPane;

    @FXML
    private Button jumpMove;

    @FXML
    private TextArea textArea;

    @FXML
    private Button submitAction;

    @FXML
    private Button buttonPl1;

    @FXML
    private Button buttonPl2;

    @FXML
    private Button buttonPl3;

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
                handlerClient.sendAction(this.currentMove);

            } catch (IOException e) {
                setFailed("Errore di rete");
            } finally {
                jumpMove.setDisable(true);
                submitAction.setDisable(true);
                disableButtons(true);
            }
        });
        jumpMove.setOnAction(ev -> {
            try {
                handlerClient.sendAction(new Mossa(currentMove.getAction(), -1, -1, -1));
            } catch (IOException e) {
                setFailed("Errore di rete");
            } finally {
                jumpMove.setDisable(true);
            }
        });

        firstPl.setEffect(shadow);
        secondPl.setEffect(shadow);
        thirdPl.setEffect(shadow);
        textArea.setEffect(shadow);

    }

    public void lightMove() {
        buttonPl1.setStyle("-fx-background-color: YELLOW");
    }

    public void lightBuild() {
        buttonPl2.setStyle("-fx-background-color: YELLOW");
    }

    public void lightPause() {
        buttonPl3.setStyle("-fx-background-color: RED");
    }

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
                        }
                    }
                });
            }
        }
    }

    private void disableButtons(boolean b) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt[i][j].setDisable(b);
            }
        }
    }


    @Override
    public void chooseCards(ArrayList<God> gods) {
        Platform.runLater(() -> {
            overlayedStage.close();
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

    public void initButtons() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                bt[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setOnMouseEntered(null);
                        bt[x][y].setOnMouseExited(null);
                        bt[x][y].setStyle("-fx-border-color:blue");
                        System.out.println("x:" + x + "\ty:" + y);
                    }
                });
                bt[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setStyle("-fx-border-color:yellow");
                    }
                });
                bt[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Button button;
                        button = (Button) e.getSource();
                        int x = GridPane.getRowIndex(button);
                        int y = GridPane.getColumnIndex(button);
                        bt[x][y].setStyle("-fx-border-color:trasparent");
                    }
                });
                bt[i][j].setOnAction(e -> {
                    Button button;
                    button = (Button) e.getSource();
                    int x = GridPane.getRowIndex(button);
                    int y = GridPane.getColumnIndex(button);
                    bt[x][y].setOnMousePressed(null);
                    //bt[x][y].setStyle("-fx-border-color:red");
                    aggiornaMossa(table.getTableCell(x, y));
                });
            }
        }
    }

    public void aggiornaMossa(Cell cella) {
        currentMove.setTargetX(cella.getX());
        currentMove.setTargetY(cella.getY());
        submitAction.setDisable(false);
        jumpMove.setDisable(true);
        submitAction.setDisable(true);
    }

    @Override
    public void setNewAction(Mossa.Action action) {
        jumpMove.setDisable(false);

        switch (action) {
            case BUILD:
                Cell c = table.getTableCell(currentMove.getTargetX(), currentMove.getTargetY());
                currentMove = new Mossa();
                currentMove.setAction(action);
                currentMove.setIdPawn(currentPawn);
                Building(table, bt, c);
                break;
            case MOVE:
                currentMove = new Mossa();
                currentMove.setAction(action);
                lightMyPawns();
        }
    }

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


    @Override
    public void setFailed(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di input");
            alert.setHeaderText(msg);
            alert.showAndWait();
        });

    }

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

    @Override
    public void printMessage(String msg) {
        msg = editString(msg);
        textArea.appendText(msg);
    }

    @Override
    public void setInitializePawn() {
        //overlayedStage.close();
        System.out.println("ciaociao");
        startTable(this.getTable(), bt);
    }

    @Override
    public void vittoria() {

    }

    @Override
    public void sconfitta(String winner) {

    }

    @Override
    public void networkError(String player) {
        setFailed("Errore di rete\n" + player + "si è disconnesso");
    }

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

    public void lightMyPawns() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Cell cell = table.getTableCell(i, j);
                if (cell.getPawn() != null && cell.getPawn().getIdGamer() == getID() && cell.getPawn().getICanPlay()) {
                    //se: esiste la pedina nella cella && la pedina è della stessa squadra del gamer && la pedina può giocare
                    //rendere cliccabile e illuminata la cella
                    bt[i][j].setStyle("-fx-border-color:yellow");
                    bt[i][j].setDisable(false);
                    bt[i][j].setOnMouseClicked(e -> {

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
                        accessibleCells(table, cell, bt, bOne);
                    });
                }
            }
        }
    }

    /**
     * //TODO: DA SISTEMARE
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
                    //Building per ora commentata
                    //Building(t,bt,t.getTableCell(x,y));
                    currentMove.setTargetX(x);
                    currentMove.setTargetY(y);
                    submitAction.setDisable(false);
                }
            });

        }
    }

    /**
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
        int x = start.getX();
        int y = start.getY();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == x && j == y) {
                    bt[i][j].setStyle("-fx-border-color:white");
                } else {
                    bt[i][j].setStyle("-fx-border-color:trasparent");
                    bt[i][j].setOnMouseClicked(null);
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
        for (Cell lightMe : cells) {
            bt[lightMe.getX()][lightMe.getY()].setStyle("-fx-border-color:blue");
            bt[lightMe.getX()][lightMe.getY()].setDisable(false);
            bt[lightMe.getX()][lightMe.getY()].setOnMouseClicked(e -> {
                Button bTwo;
                bTwo = (Button) e.getSource();
                int xs = GridPane.getRowIndex(bTwo);
                int ys = GridPane.getColumnIndex(bTwo);
                bt[start.getX()][start.getY()].setStyle("-fx-border-color:white");
                bt[start.getX()][start.getY()].setOnMouseClicked(null);
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
                submitAction.setDisable(false);
            });
        }

    }
}

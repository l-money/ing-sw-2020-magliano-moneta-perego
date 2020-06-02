package santorini.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import santorini.model.Gamer;
import santorini.model.Pawn;
import santorini.model.Table;

public class TestTable {

    @FXML
    private GridPane gridPane;
    private ImageView[][] imageView;
    private Stage stage;
    private int c1, c2;
    private Lighting lighting = new Lighting();
    private DropShadow shadow = new DropShadow();

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
    //TODO :
    // Popolare la table iniziale di imageView vuote
    // Illuminare le imageView adiacenti
    // Spostamento pedina con secondo click

    public void initialize() {
        imageView = new ImageView[5][5];
        c1 = 0;
        c2 = 0;
        Table table = new Table();
        Gamer gamer = new Gamer(null, "Pippo", 0, null, null);
        Gamer gamer2 = new Gamer(null, "Pno", 1, null, null);
        Gamer gamer3 = new Gamer(null, "Pano", 2, null, null);
        Pawn p0 = new Pawn();
        Pawn p1 = new Pawn();
        Pawn p2 = new Pawn();
        Pawn p3 = new Pawn();
        Pawn p4 = new Pawn();
        Pawn p5 = new Pawn();

        p0.setIdPawn(0);
        p0.setIdGamer(0);
        p1.setIdPawn(1);
        p1.setIdGamer(0);

        p2.setIdPawn(0);
        p3.setIdGamer(1);
        p2.setIdPawn(1);
        p3.setIdGamer(1);

        p4.setIdPawn(0);
        p5.setIdGamer(2);
        p4.setIdPawn(1);
        p5.setIdGamer(2);

        table.setACell(1, 1, 0, false, false, p0);
        table.setACell(2, 2, 1, false, false, p1);
        table.setACell(1, 2, 0, false, false, p2);
        table.setACell(2, 3, 1, false, false, p3);

        initializeCells(table, imageView);
        myPawns(table, gamer, imageView);
    }

    //Prototipo: Se trova celle con pedine mette immagine di apollo
    public void initializeCells(Table t, ImageView[][] imageView) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (t.getTableCell(x, y).getPawn() != null) {
                    Image i = new Image(String.valueOf(getClass().getClassLoader().getResource("images/GodCards/Apollo.png")), 58, 53, true, false);
                    imageView[x][y] = new ImageView();
                    imageView[x][y].setImage(i);
                    imageView[x][y].setFitHeight(58);
                    imageView[x][y].setFitWidth(53);
                    gridPane.add(imageView[x][y], y, x);
                }
            }
        }
    }

    //Prototipo: le pedine di gamer0 sono cliccabili, le pedine gamer1 non cliccabili
    public void myPawns(Table t, Gamer g, ImageView[][] imageView) {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (t.getTableCell(x, y).getPawn() != null && t.getTableCell(x, y).getPawn().getIdGamer() == g.getIdGamer()) {
                    imageView[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            ImageView im;
                            im = (ImageView) e.getSource();
                            im.setEffect(shadow);
                            System.out.println("Row: " + GridPane.getRowIndex(im));
                            System.out.println("Column: " + GridPane.getColumnIndex(im));
                            int a = GridPane.getRowIndex(im);
                            int b = GridPane.getColumnIndex(im);
                            System.out.print("Id pedina: " + t.getTableCell(a, b).getPawn().getIdPawn());
                            System.out.println("\tId Giocatore: " + t.getTableCell(a, b).getPawn().getIdGamer());
                            imageView[a][b + 1].setEffect(lighting);

                        }
                    });

                }
            }
        }
    }

}

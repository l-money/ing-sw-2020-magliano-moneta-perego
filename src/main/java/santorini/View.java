package santorini;

import santorini.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class View {

    private Table table;
    private BufferedReader br;
    private NetworkHandlerClient handlerClient;
    private Gamer gamer;
    private Turno turno;
    private Mossa move;
    private Mossa build;
    private String movePawn;
    private ArrayList<God> gods;

    public View() {
        table = new Table();
        gamer = new Gamer(null, "Prova", 0, null, null);
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void setHandlerClient(NetworkHandlerClient handlerClient) {
        this.handlerClient = handlerClient;
    }


    public void chooseCards(ArrayList<God> gods) {
        new Thread(() -> {
            String card = "1";
            God chooseCard;
            int number;
            for (God g : gods) {
                System.out.println(g.getName());
                System.out.println(g.getDescription());
            }
            System.out.println("Il numero di carte che puoi scegliere sono " + gods.size());
            try {
                do {
                    System.out.println("Che carta vuoi scegliere?");
                    card = br.readLine();
                    number = Integer.parseInt(card);
                    if (number < 0 || number > 2) {
                        System.out.println("Errore carta scelta!");
                    }
                } while (number < 0 || number > 2);
                chooseCard = gods.get(number);
                handlerClient.setCard(chooseCard);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }


    //chiedo solamente la cella in cui voglio costruire e incremento di uno il livello
    public void setNewBuild() {
        new Thread(() -> {
            String moveBuild;
            int[] positionBuild = new int[2];
            System.out.println("In che cella vuoi costruire [x,y]? ");
            try {
                moveBuild = br.readLine();
                positionBuild = giveMeString(moveBuild, 2);
                int b = Integer.parseInt(movePawn);
                build = new Mossa(Mossa.Action.BUILD, b, positionBuild[0], positionBuild[1]);

                handlerClient.setBuildPawn(positionBuild);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    /*
        metodo che dopo aver scelto la pedina da muovere,
        chiede l'inserimento nella cella in cui vuole spostarsi
     */
    public void setNewMove() {
        new Thread(() -> {
            String stringMove;
            int[] coordinateMove = new int[2];
            System.out.println("Che pedina vuoi muovere? ");
            try {
                movePawn = br.readLine();
                switch (movePawn.charAt(0)) {
                    case '0':
                        System.out.println("Inserisci movimento per la pedina 1 [x,y]: ");
                        stringMove = br.readLine();
                        coordinateMove = giveMeString(stringMove, 2);
                        move = new Mossa(Mossa.Action.MOVE, 0, coordinateMove[0], coordinateMove[1]);

                        break;
                    case '1':
                        System.out.println("Inserisci movimento per la pedina 2 [x,y]: ");
                        stringMove = br.readLine();
                        coordinateMove = giveMeString(stringMove, 2);
                        move = new Mossa(Mossa.Action.MOVE, 1, coordinateMove[0], coordinateMove[1]);

                        break;
                    default:
                        System.out.println("Numero pedina errore");
                }
                handlerClient.setMovementPawn(coordinateMove);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }


    /*
        metodo che mi fa immettere il numero dei partecipanti alla partita
        che possono essere o 2 o 3
     */
    public void setNumeroGiocatori() {
        new Thread(() -> {
            String partecipanti = "2";
            int[] players = new int[1];
            System.out.print("Scegli numero partecipanti [Default 2]: ");
            try {
//                partecipanti = br.readLine();
//                if (partecipanti.charAt(0) == '3') {
//                    partecipanti = "3";
//                } else {
//                    partecipanti = "2";
//                }
                players = giveMeString(partecipanti, 1);//Se scelgo di usare il metodo devo mettere int al posto di string?
                handlerClient.setPartecipanti(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /*
        metodo che mi fa inserire le pedine all'interno della tabella
        nelle coordinate x e y
     */
    public void setInitializePawn() {
        new Thread(() -> {
            String coordPawn0, coordPawn1;
            int[] coordinate = new int[2];
            System.out.println("Inserisci posizioni delle pedine: ");
            try {
                System.out.println("Inserisci cordinata pedina 1 [x,y]: ");
                coordPawn0 = br.readLine();
                coordinate = giveMeString(coordPawn0, 2);
                gamer.setAPawn(0, coordinate[0], coordinate[1], 0, 0);
                table.setACell(coordinate[0], coordinate[1], 0, false, false, gamer.getPawn(0));

                System.out.println("Inserisci cordinata pedina 2 [x,y]: ");
                coordPawn1 = br.readLine();
                coordinate = giveMeString(coordPawn1, 2);
                gamer.setAPawn(1, coordinate[0], coordinate[1], 0, 0);
                table.setACell(coordinate[0], coordinate[1], 0, false, false, gamer.getPawn(1));

                handlerClient.setCordinata(coordinate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setFailed() {
        new Thread(() -> {

            System.out.println("Errore generale");
            System.out.println(("Nuova istruzione in arrivo: "));

        }).start();
    }

    public int[] giveMeCard(String c, int nu) {
        int[] numberCard = new int[nu];
        int m = c.length();
        char[] stringCard = new char[m];
        for (int i = 0; i < m; i++) {
            stringCard[i] = c.charAt(i);
        }
        if (m == 1) {
            int x = Integer.parseInt(String.valueOf(stringCard[nu - 1]));
            numberCard[nu - 1] = x;
        } else {
            System.err.println("Errore");
        }
        return numberCard;
    }

    public int[] giveMeString(String s, int n) {
        //s è la stringa in ingresso
        //n numero di caratteri richiesti: 1 per giocatori; 2 per coordinate x,y
        int[] numbers = new int[n];
        int l = s.length();
        char[] chars = new char[l];
        for (int i = 0; i < l; i++) {
            chars[i] = s.charAt(i);
        }
        switch (l) {
            case 1:
                int x = Integer.parseInt(String.valueOf(chars[n - 1]));
                if ((x == 2) || (x == 3)) {
                    numbers[n - 1] = x;
                } else {
                    System.err.println("Errore\n");
                }
                break;
            case 3:
                if (chars[1] != ',') {
                    System.err.println("Errore\n");
                } else {
                    int x1 = Integer.parseInt(String.valueOf(chars[0]));
                    int y1 = Integer.parseInt(String.valueOf(chars[2]));
                    if ((x1 < 0) || (x1 > 4) || (y1 < 0) || (y1 > 4)) {
                        System.err.println("Errore\n");
                    } else {
                        numbers[0] = x1;
                        numbers[1] = y1;
                    }
                }
                break;
            default:
                System.err.println("Errore\n");
                break;
        }
        return numbers;
    }

//    public class ColorPrint{
//        public static final String ANSI_RESET = "\u001B[0m";
//        public static final String ANSI_BLELLOW = "\u001B[33m";
//        public static final String ANSI_BLUE = "\u001B[34m";
//        public static final String ANSI_PURPLE = "\u001B[35m";
//        public static final String ANSI_CYAN = "\u001B[36m";
//        public static final String ANSI_WHITE = "\u001B[37m";
//        public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
//        public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
//        public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
//        public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
//        public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
//        public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
//        public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
//        public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
//
//        public static void main(String[] args) {
//            System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
//            System.out.println(ANSI_GREEN_BACKGROUND + "Green background" + ANSI_RESET);
//
//        }
//    }
}


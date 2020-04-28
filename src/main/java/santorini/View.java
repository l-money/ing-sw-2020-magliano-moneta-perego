package santorini;

import santorini.model.Pawn;
import santorini.model.Table;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class View {

    private Table table;
    private BufferedReader br;
    private NetworkHandlerClient handlerClient;

    public View() {
        table = new Table();
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void setHandlerClient(NetworkHandlerClient handlerClient) {
        this.handlerClient = handlerClient;
    }

    //metodo che stampa la table indicante livello cella e posizione pedina
    public void printTable(Table table) {
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                System.out.print(" " + table.getTableCell(i, j).getLevel());
                if (table.getTableCell(i, j).getPawn() != null) {
                    System.out.print("\u001B[42m");
                } else {
                    System.out.print("\u001B[0m");
                }
            }
            System.out.println();
        }
    }

    //metodo che ricerca in quali celle hanno la cupola
    public void searchLevelMax(Table table) {
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {

            }
        }
    }

    public void levelMax(Table table) {

    }

    public void setNumeroGiocatori() {
        new Thread(() -> {
            String partecipanti = "2";
            System.out.print("Scegli numero partecipanti [Default 2]: ");
            try {
                partecipanti = br.readLine();
                if (partecipanti.charAt(0) == '3') {
                    partecipanti = "3";
                } else {
                    partecipanti = "2";
                }
                handlerClient.setPartecipanti(partecipanti);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
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


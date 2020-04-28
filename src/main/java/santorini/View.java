package santorini;

import santorini.model.Pawn;
import santorini.model.Table;

public class View {

    private Table table;

    public View() {
        table = new Table();
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

class ViewStampa {
    public static void main(String[] args) {
        View v = new View();
        Table t = new Table();
        Pawn p = new Pawn();
        v.printTable(t);
        System.out.println("\n");
        t.getTableCell(2, 2).setLevel(2);
        t.getTableCell(1, 0).setPawn(p);
        t.getTableCell(0, 2).setPawn(p);
        v.printTable(t);
    }

}

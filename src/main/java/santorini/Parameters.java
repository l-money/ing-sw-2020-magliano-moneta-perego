package santorini;

public class Parameters {
    public static final int PORT = 3467;

    public static enum command {
        SET_PLAYERS_NUMBER,
        INITIALIZE_PAWNS,
        MOVE,
        BUILD,
        FAILED,
        NO,
        WINNER,
        LOSER,
        NETWORK_ERROR,
        MESSAGE,
        LOCKED_PAWN,
        SWITCH_PAWN,
    }


}

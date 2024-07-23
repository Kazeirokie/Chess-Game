import java.util.ArrayList;
import java.util.List;

// MVC DESIGN PATTERN : MODEL
// Board class used to create Board in the game.
public class Board {

    public final static int WIDTH = 7;
    public final static int HEIGHT = 6;

    private static List<GamePiece> pieces = new ArrayList<>();

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public List<GamePiece> getPieces() {
        return pieces;
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Return Piece at Specific Location
    public static GamePiece getPieceAt(int rowId, int columnId) {
        for (GamePiece piece : pieces) {
            if (piece.getCurrentRow() == rowId && piece.getCurrentCol() == columnId) {
                return piece;
            }
        }
        return null;
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Initialize Chess Piece Position Into The Board
    public void addPiece(List<GamePiece> piece) {
        pieces.addAll(piece);
    }
}

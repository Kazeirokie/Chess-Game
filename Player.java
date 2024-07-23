import java.util.ArrayList;
import java.util.List;

// MVC DESIGN PATTERN : MODEL
// Player class used to create Players in the game.
public class Player {

    private List<GamePiece> playerPiece;
    private List<GamePiece> capturedPiece;
    private PieceColor playerColor;
    private PieceSet playerSet;
    private Integer playerID;
    private static int genID = 1;

    public Player(PieceColor playerColor) {
        this.playerColor = playerColor;
        playerPiece = new ArrayList<>();
        capturedPiece = new ArrayList<>();
        playerSet = new PieceSet();
        playerID = genID;
        genID++;
    }

    public void addPlayerPieces(PieceColor playerColor) {
        playerPiece.addAll(playerSet.getColorSet(playerColor));
    }

    public void addPlayerPiece(GamePiece piece) {
        playerPiece.add(piece);
    }

    public void addCapturedPiece(GamePiece piece) {
        capturedPiece.add(piece);
    }

    public void removePlayerPiece(GamePiece piece) {
        playerPiece.remove(piece);
    }

    public PieceColor getColor() {
        return playerColor;
    }

    public List<GamePiece> getPlayerPiece() {
        return playerPiece;
    }

    public List<GamePiece> getCapturedPiece() {
        return capturedPiece;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String toString() {
        return "Player " + playerID.toString();
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Reset Player Pieces
    public void resetPlayer() {

        // CLEAR PLAYER PIECES DATA
        playerPiece.clear();
        capturedPiece.clear();

        // RESET PIECE SET AND ADD INTO PLAYER
        playerSet.initPieces(playerColor);
        addPlayerPieces(playerColor);
    }
}
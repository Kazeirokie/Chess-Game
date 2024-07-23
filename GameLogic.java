import java.util.ArrayList;
import java.util.List;

// MVC DESIGN PATTERN : MODEL
// GameLogic class sets the logics that must be followed in the game.
public class GameLogic {

    private static int flipCounter = 0;
    private static int moveCount = 0;
    private List<Player> players;
    private Player currentPlayer;
    private boolean isPlayerMove;
    private boolean winFlag;
    private int winnerPlayer;
    private Board board;

    public GameLogic(Board board) {
        this.board = board;
        players = new ArrayList<>();
        players.add(new Player(PieceColor.YELLOW));
        players.add(new Player(PieceColor.BLUE));
    }

    public static int getFlipCounter() {
        return flipCounter;
    }

    public int getWinnerPlayer() {
        return winnerPlayer;
    }

    public boolean getWinFlag() {
        return winFlag;
    }

    public void setFlipCounter(int counter) {
        flipCounter = counter;
    }

    public void increaseFlipCounter() {
        flipCounter += 1;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int newCount) {
        moveCount = newCount;
    }

    public static void resetMoveCount() {
        moveCount = 0;
    }

    public boolean isPlayerMove() {
        return isPlayerMove;
    }

    public void isPlayerMove(boolean bool) {
        isPlayerMove = bool;
    }

    // Author : Aminul
    // Description : Initialize Game Data
    public void initGame() {
        for (Player player : players) {
            player.resetPlayer();
            board.addPiece(player.getPlayerPiece());
        }
        setCurrentPlayer(0);
    }

    // Author : Aminul
    // Description : Reset Game Data
    public void resetData() {
        for (Player player : players) {
            player.getPlayerPiece().clear();
            player.getCapturedPiece().clear();
        }
        board.getPieces().clear();
        resetMoveCount();
        flipCounter = 0;
        isPlayerMove = false;
        winFlag = false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int curPlayer) {
        currentPlayer = players.get(curPlayer);
    }

    // Author : Aminul
    // Description : Change Turn to Next Player
    private int getNextPlayer() {
        return (getCurrentPlayer().getPlayerID() == 1) ? 1 : 0;
    }

    public List<Player> getPlayers() {
        return players;
    }

    // Author : Luqman
    // Description : Validate Piece Possible Future Moves
    public boolean canMoveTo(int oldRow, int oldCol, int newRow, int newCol) {
        GamePiece selectedPiece = Board.getPieceAt(oldRow, oldCol);
        GamePiece capturedPiece = Board.getPieceAt(newRow, newCol);

        if (selectedPiece.equals(capturedPiece) || !selectedPiece.isMoveValid(newRow, newCol)
                || !selectedPiece.isPathClear(newRow, newCol)) {
            return false;
        }

        if (capturedPiece != null) {
            if (selectedPiece.getColor().equals(capturedPiece.getColor())) {
                return false;
            }

        }
        return true;
    }

    // Author : Luqman & Aminul
    // Description : Validate Piece Movement & Relocate Piece Position
    public void movePiece(GamePiece chessPiece, int newRow, int newCol) {

        if (chessPiece.getColor().equals(PieceColor.BLUE) && currentPlayer.getPlayerID() == 1) {
            System.out.println("Player 1 can only move yellow pieces");
            isPlayerMove = false;
            return;
        } else if (chessPiece.getColor().equals(PieceColor.YELLOW) && currentPlayer.getPlayerID() == 2) {
            System.out.println("Player 2 can only move blue pieces");
            isPlayerMove = false;
            return;
        }

        GamePieceView chessPieceView = new GamePieceView(chessPiece);
        if (chessPiece.isMoveValid(newRow, newCol)) {

            isPlayerMove = true;

            // Move the piece to new tile
            chessPieceView.moveTo(newRow, newCol);
            moveCount++;

            // Check if piece meet another piece
            GamePiece capturedPiece = Board.getPieceAt(newRow, newCol);
            if (capturedPiece != null) {
                GamePieceView capturedPieceView = new GamePieceView(capturedPiece);
                if (chessPiece.getColor().equals(capturedPiece.getColor())) {

                    // Stay at same tile
                    chessPieceView.moveTo(chessPiece.getCurrentRow(), chessPiece.getCurrentCol());
                    isPlayerMove = false;
                    moveCount--;
                    System.out.println("Cannot capture your own piece");
                    return;
                } else {
                    capturePiece(capturedPiece, currentPlayer);
                    observerWinner(capturedPiece);
                    capturedPieceView.capture();
                }
            }

            setCurrentPlayer(getNextPlayer());

            // Set new row and column as current
            chessPiece.setCurrentRow(newRow);
            chessPiece.setCurrentCol(newCol);
            chessPiece.checkAndTransform(board, moveCount);

        } else {
            // Stay at same tile
            chessPieceView.moveTo(chessPiece.getCurrentRow(), chessPiece.getCurrentCol());
            isPlayerMove = false;
            System.out.println("Invalid move for the piece");
        }
    }

    // Author : Aminul
    // Description : Observe Win Condition (Sun Piece Captured)
    private void observerWinner(GamePiece piece) {
        if (currentPlayer.getCapturedPiece().contains(piece) && piece.getType() == PieceType.SUN) {
            winFlag = true;
            winnerPlayer = currentPlayer.getPlayerID();
        } else
            winFlag = false;
    }

    // Author : Luqman & Aminul
    // Description : Capture Piece in Chessboard
    private void capturePiece(GamePiece capturedPiece, Player currentPlayer) {
        board.getPieces().remove(capturedPiece);
        currentPlayer.addCapturedPiece(capturedPiece);
        // Find the player who owns the captured piece
        Player otherPlayer = findPlayerByPiece(capturedPiece);

        // If the owner is found, remove the piece from their player pieces
        if (otherPlayer != null) {
            otherPlayer.removePlayerPiece(capturedPiece);
        }
        System.out.println(currentPlayer.getCapturedPiece());
    }

    // Author : Luqman & Aminul
    // Description : Find Player Based on The Piece
    private Player findPlayerByPiece(GamePiece piece) {
        for (Player player : getPlayers()) {
            if (player.getPlayerPiece().contains(piece)) {
                return player;
            }
        }
        return null; // Piece not found in any player's pieces
    }

    // Author : Aminul
    // Description : Invert Point Piece After Reach End of Chessboard
    public void invertObject() {
        for (GamePiece piece : board.getPieces()) {
            GamePieceView pieceView = new GamePieceView(piece);
            if (piece.getType() == PieceType.POINT && piece.getCurrentRow() == 0) {
                piece.invertCounter++;
                pieceView.updateImage();
            }
        }
    }
}

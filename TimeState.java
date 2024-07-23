// STATE DESIGN PATTERN : CONCRETE STATE
// TimeState class provides the state for the Time Piece. Will be used in swapping between Time and Plus Pieces.
public class TimeState implements PieceState {

    // Author : Syed Danial
    // Description : Check Valid Move for Time Piece
    @Override
    public boolean isMoveValid(GamePiece piece, int newRow, int newCol) {
        // Check for diagonal movement
        return Math.abs(newRow - piece.currentRow) == Math.abs(newCol - piece.currentCol);
    }

    // Author : Syed Danial
    // Description : Check Available Path for Time Piece (Possible Future Move)
    @Override
    public boolean isPathClear(GamePiece piece, int newRow, int newCol) {
        int rowStep = (newRow > piece.getCurrentRow()) ? 1 : -1;
        int colStep = (newCol > piece.getCurrentCol()) ? 1 : -1;

        for (int row = piece.getCurrentRow() + rowStep,
                col = piece.getCurrentCol() + colStep; row != newRow && col != newCol; row += rowStep, col += colStep) {
            if (Board.getPieceAt(row, col) != null) {
                // There is a piece in the path, so the move is invalid
                return false;
            }
        }

        return true;
    }
}

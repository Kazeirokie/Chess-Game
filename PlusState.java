// STATE DESIGN PATTERN : CONCRETE STATE
// PlusState class provides the state for the Plus Piece. Will be used in swapping between Time and Plus Pieces.
public class PlusState implements PieceState {

    // Author : Syed Danial
    // Description : Check Valid Move for Plus Piece
    @Override
    public boolean isMoveValid(GamePiece piece, int newRow, int newCol) {
        // Check for horizontal or vertical movement
        return newRow == piece.currentRow || newCol == piece.currentCol;
    }

    // Author : Syed Danial
    // Description : Check Available Path for Plus Piece (Possible Future Move)
    @Override
    public boolean isPathClear(GamePiece piece, int newRow, int newCol) {
        if (newRow == piece.currentRow) { // The move is horizontal
            int colStep = (newCol > piece.currentCol) ? 1 : -1;
            for (int col = piece.currentCol + colStep; col != newCol; col += colStep) {
                if (Board.getPieceAt(piece.currentRow, col) != null) {
                    // There is a piece in the path, so the move is invalid
                    return false;
                }
            }
        } else if (newCol == piece.currentCol) { // The move is vertical
            int rowStep = (newRow > piece.currentRow) ? 1 : -1;
            for (int row = piece.currentRow + rowStep; row != newRow; row += rowStep) {
                if (Board.getPieceAt(row, piece.currentCol) != null) {
                    // There is a piece in the path, so the move is invalid
                    return false;
                }
            }
        } else {
            // The move is neither horizontal nor vertical, so it's invalid
            return false;
        }

        return true;
    }
}

// MVC DESIGN PATTERN : MODEL
// Point class provides information about the Point Piece
public class Point extends GamePiece {

    public Point(int currentRow, int currentCol, PieceColor color, PieceStateType state, int invertCounter) {
        super(currentRow, currentCol, color);
        TYPE = PieceType.POINT;
        this.STATE = state;
        this.invertCounter = invertCounter;
    }

    @Override
    public boolean isMoveValid(int newRow, int newCol) {
        return isPointMove(newRow, newCol) && isPathClear(newRow, newCol);
    }

    // Author : Luqman
    // Description : Check Valid Move for Point Piece
    public boolean isPointMove(int newRow, int newCol) {
        int rowDiff = newRow - this.currentRow;
        int colDiff = Math.abs(newCol - this.currentCol);

        if ((rowDiff == -1 || rowDiff == -2) && colDiff == 0 && (invertCounter % 2 == 0))
            return true;
        else if ((rowDiff == 1 || rowDiff == 2) && colDiff == 0 && (invertCounter % 2 != 0))
            return true;
        return false;
    }

    // Author : Luqman
    // Description : Check Available Path for Point Piece (Possible Future Move)
    @Override
    public boolean isPathClear(int newRow, int newCol) {
        // If target mvoe is 2 square, check first square after Point
        // If there is object, path is not clear
        if (this.currentRow > newRow) {
            if (this.currentRow - newRow == 2)
                if (Board.getPieceAt(newRow + 1, this.currentCol) != null) {
                    return false;
                }
        } else {
            if (this.currentRow - newRow == -2)
                if (Board.getPieceAt(newRow - 1, this.currentCol) != null) {
                    return false;
                }
        }
        return true;
    }
}
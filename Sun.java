// MVC DESIGN PATTERN : MODEL
// Sun class provides information about the Sun Piece
public class Sun extends GamePiece {

    public Sun(int currentRow, int currentCol, PieceColor color, PieceStateType state) {
        super(currentRow, currentCol, color);
        TYPE = PieceType.SUN;
        this.STATE = state;
    }

    // Author : Luqman
    // Description : Check Valid Move for Sun Piece
    @Override
    public boolean isMoveValid(int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - this.currentRow);
        int colDiff = Math.abs(newCol - this.currentCol);

        // King can move one square in any direction
        return (rowDiff == 1 || rowDiff == 0) && (colDiff == 1 || colDiff == 0);
    }

    // Author : Luqman
    // Description : Check Available Path for Sun Piece (Possible Future Move)
    @Override
    public boolean isPathClear(int newRow, int newCol) {
        return true;
    }
}

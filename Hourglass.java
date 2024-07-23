// MVC DESIGN PATTERN : MODEL
// Hourglass class provides information about the Hourglass Piece
public class Hourglass extends GamePiece {

    public Hourglass(int currentRow, int currentCol, PieceColor color, PieceStateType state) {
        super(currentRow, currentCol, color);
        TYPE = PieceType.HOURGLASS;
        this.STATE = state;

    }

    // Author : Luqman
    // Description : Check Valid Move for Hourglass Piece
    public boolean isMoveValid(int newRow, int newCol) {
        return Math.abs(newRow - this.currentRow) * Math.abs(newCol - this.currentCol) == 2;
    }

    // Author : Luqman
    // Description : Check Available Path for Hourglass Piece (Possible Future Move)
    public boolean isPathClear(int newRow, int newCol) {
        return true;
    }

}

// MVC DESIGN PATTERN : MODEL
// Plus class provides information about the Plus Piece
public class Plus extends GamePiece {

    public Plus(int currentRow, int currentCol, PieceColor color, PieceStateType state) {
        super(currentRow, currentCol, color);
        TYPE = PieceType.PLUS;
        this.STATE = state;
        setCurrentState(state);

    }

    // Author : Syed Danial
    // Description : Check Valid Move for Plus Piece
    @Override
    public boolean isMoveValid(int newRow, int newCol) {
        return isPathClear(newRow, newCol) &&
                currentState.isMoveValid(this, newRow, newCol);
    }

    // Author : Syed Danial
    // Description : Check Available Path for Plus Piece (Possible Future Move)
    @Override
    public boolean isPathClear(int newRow, int newCol) {
        return currentState.isPathClear(this, newRow, newCol);
    }
}

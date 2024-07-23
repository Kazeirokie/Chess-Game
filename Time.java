// MVC DESIGN PATTERN : MODEL
// Time class provides information about the Time Piece
public class Time extends GamePiece {

    public Time(int currentRow, int currentCol, PieceColor color, PieceStateType state) {
        super(currentRow, currentCol, color);
        TYPE = PieceType.TIME;
        this.STATE = state;
        setCurrentState(state);
    }

    // Author : Syed Danial
    // Description : Check Valid Move for Time Piece
    @Override
    public boolean isMoveValid(int newRow, int newCol) {
        return isPathClear(newRow, newCol) &&
                currentState.isMoveValid(this, newRow, newCol);
    }

    // Author : Syed Danial
    // Description : Check Available Path for Time Piece (Possible Future Move)
    @Override
    public boolean isPathClear(int newRow, int newCol) {
        return currentState.isPathClear(this, newRow, newCol);
    }
}

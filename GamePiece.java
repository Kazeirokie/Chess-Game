// MVC DESIGN PATTERN : MODEL
// GamePiece abstract class stores common attributes that shared across all Pieces.
public abstract class GamePiece {

    protected int currentRow;
    protected int currentCol;
    protected PieceType TYPE;
    protected PieceColor COLOR;
    protected PieceStateType STATE;
    protected int invertCounter = 0;
    protected PieceState currentState;
    protected boolean isInverted = false;

    public GamePiece(int row, int col, PieceColor color) {
        this.currentRow = row;
        this.currentCol = col;
        this.COLOR = color;
    }

    public abstract boolean isMoveValid(int newRow, int newCol);

    public abstract boolean isPathClear(int newRow, int newCol);

    public boolean isInverted() {
        return isInverted;
    }

    public PieceColor getColor() {
        return COLOR;
    }

    public PieceType getType() {
        return TYPE;
    }

    public PieceStateType getStateType() {
        return STATE;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void setInverted(boolean invert) {
        this.isInverted = invert;
    }

    public void setColor(PieceColor COLOR) {
        this.COLOR = COLOR;
    }

    public void setType(PieceType TYPE) {
        this.TYPE = TYPE;
    }

    public void setStateType(PieceStateType STATE) {
        this.STATE = STATE;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public void setCurrentState(PieceStateType state) {
        switch (state) {
            case PLUS_STATE:
                currentState = new PlusState();
                break;
            case TIME_STATE:
                currentState = new TimeState();
                break;
            default:
                break;

        }
    }

    public PieceState getCurrentState() {
        return currentState;
    }

    // Author : Syed
    // Description : Transform Plus and Time Pieces After 2 Turns
    public void checkAndTransform(Board board, int moveCount) {
        if (moveCount >= 4) {
            for (GamePiece piece : board.getPieces()) {
                if (piece.getCurrentState() instanceof TimeState) {
                    piece.setStateType(PieceStateType.PLUS_STATE);
                    piece.setCurrentState(piece.getStateType());
                } else if (piece.getCurrentState() instanceof PlusState) {
                    piece.setStateType(PieceStateType.TIME_STATE);
                    piece.setCurrentState(piece.getStateType());
                }
            }
            GameLogic.resetMoveCount();
        }
    }

}

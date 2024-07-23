// STATE DESIGN PATTERN : STATE 
public interface PieceState {
    public boolean isMoveValid(GamePiece piece, int newRow, int newCol);

    public boolean isPathClear(GamePiece piece, int newRow, int newCol);
}

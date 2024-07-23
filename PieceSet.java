import java.util.ArrayList;
import java.util.List;

// MVC DESIGN PATTERN : MODEL
// PieceSet class used to create sets of Pieces based on colours (YELLOW & BLUE)
public class PieceSet {
    private List<GamePiece> yellowSet;
    private List<GamePiece> blueSet;

    // provide set of pieces in color to the players
    public PieceSet() {
        yellowSet = new ArrayList<>();
        blueSet = new ArrayList<>();
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Initialize Set of Pieces
    public void initPieces(PieceColor color) {
        resetPieceSet();
        initPointPieces(color);
        initOtherPieces(color);
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Return Set of Pieces Based on Color
    public List<GamePiece> getColorSet(PieceColor color) {
        return color.equals(PieceColor.YELLOW) ? yellowSet : blueSet;
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Initialize Point Pieces in Set.
    private void initPointPieces(PieceColor color) {
        for (int i = 0; i < Board.getWIDTH(); i++) {
            if (color.equals(PieceColor.YELLOW))
                yellowSet.add(new Point(4, i, color, PieceStateType.DEFAULT_STATE, 0));
            else
                blueSet.add(new Point(1, i, color, PieceStateType.DEFAULT_STATE, 0));
        }
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Initialize Pieces Other than Point in Set.
    private void initOtherPieces(PieceColor color) {
        int i = -1;
        if (color.equals(PieceColor.YELLOW)) {
            yellowSet.add(new Plus(5, ++i, color, PieceStateType.PLUS_STATE));
            yellowSet.add(new Hourglass(5, ++i, color, PieceStateType.DEFAULT_STATE));
            yellowSet.add(new Time(5, ++i, color, PieceStateType.TIME_STATE));
            yellowSet.add(new Sun(5, ++i, color, PieceStateType.DEFAULT_STATE));
            yellowSet.add(new Time(5, ++i, color, PieceStateType.TIME_STATE));
            yellowSet.add(new Hourglass(5, ++i, color, PieceStateType.DEFAULT_STATE));
            yellowSet.add(new Plus(5, ++i, color, PieceStateType.PLUS_STATE));

        } else {
            blueSet.add(new Plus(0, ++i, color, PieceStateType.PLUS_STATE));
            blueSet.add(new Hourglass(0, ++i, color, PieceStateType.DEFAULT_STATE));
            blueSet.add(new Time(0, ++i, color, PieceStateType.TIME_STATE));
            blueSet.add(new Sun(0, ++i, color, PieceStateType.DEFAULT_STATE));
            blueSet.add(new Time(0, ++i, color, PieceStateType.TIME_STATE));
            blueSet.add(new Hourglass(0, ++i, color, PieceStateType.DEFAULT_STATE));
            blueSet.add(new Plus(0, ++i, color, PieceStateType.PLUS_STATE));
        }
    }

    // Author : Izz Hakim & Danish Izz
    // Description : Return Piece at Specific Location
    private void resetPieceSet() {
        yellowSet.clear();
        blueSet.clear();
    }
}

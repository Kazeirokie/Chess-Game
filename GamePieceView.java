import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// MVC DESIGN PATTERN : VIEW
// GamePieceView class responsibles in setting the images for every Pieces to be displayed in View class
public class GamePieceView extends ImageView {
    private GamePiece piece;

    public GamePieceView(GamePiece piece) {
        this.piece = piece;
        updateImage();
    }

    // Author : Aminul & Syed
    // Description : Get String Depending on The Piece Color
    private String getImageColor(PieceColor color) {
        return color.equals(PieceColor.BLUE) ? "Blue" : "Yellow";
    }

    // Author : Aminul
    // Description : Get String Based on the invertCounter var
    private String invertImage() {
        return (piece.invertCounter % 2 == 0) ? "" : "Invert";
    }

    // Author : Syed
    // Description : Get String Depending on The Piece Type
    private String getImagePath() {
        String pieceType;
        PieceColor color = piece.getColor();
        if (piece instanceof Point) {
            pieceType = "Point";
        } else if (piece instanceof Hourglass) {
            pieceType = "Hourglass";
        } else if (piece instanceof Sun) {
            pieceType = "Sun";
        } else {
            pieceType = (piece.getCurrentState() instanceof TimeState) ? "Time" : "Plus";
        }
        return String.format("/images/%s%s_%s.png", invertImage(), getImageColor(color), pieceType);
    }

    // Author : Syed
    // Description : Update Image in the GUI
    public void updateImage() {
        String imagePath = getImagePath();
        Image pieceImage = new Image(imagePath, View.getSQUARE_SIZE(), View.getSQUARE_SIZE(), false, false);
        this.setImage(pieceImage);
        this.setRotate(GameLogic.getFlipCounter() % 2 != 0 ? 180 : 360);
    }

    // Author : Syed
    // Description : Relocate Image in The Chessboard
    public void moveTo(int row, int col) {
        relocate(col * View.getSQUARE_SIZE(), row * View.getSQUARE_SIZE());
    }

    // Author : Syed
    // Description : Remove Image Upon Piece Captured
    public void capture() {
        setImage(null);
    }
}
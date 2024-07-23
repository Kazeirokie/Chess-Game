import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

// MVC DESIGN PATTERN : CONTROLLER
// Controller class receives user input from View and updates the Models.
public class Controller {

    private GameLogic game;

    public Controller(GameLogic game) {
        this.game = game;
    }

    private GamePiece selectedPiece = null;

    // Author : Aminul
    // Description : Receive Input using Mouse Action
    public EventHandler<MouseEvent> mouseClickedHandler(View view) {
        return event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();

            int targetColumn = (int) (mouseX / View.getSQUARE_SIZE());
            int targetRow = (int) (mouseY / View.getSQUARE_SIZE());

            GamePiece clickedPiece = Board.getPieceAt(targetRow, targetColumn);

            if (selectedPiece != null) {

                game.movePiece(selectedPiece, targetRow, targetColumn);
                game.invertObject();
                view.logUpdate();

                // Reset the selected piece state
                selectedPiece = null;

                // Player piece to move is chosen
            } else if (clickedPiece != null && game.getCurrentPlayer().getPlayerPiece().contains(clickedPiece)) {
                selectedPiece = clickedPiece;
                view.showPossibleMoves(targetRow, targetColumn);
            }
        };
    }
}

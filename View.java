import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// MVC DESIGN PATTERN : VIEW
// View class provides the display of Graphical User Interface for the application.
public class View extends Application {
    private static final int WINDOW_HEIGHT = 650;
    private static final int WINDOW_WIDTH = 700;
    private static final int SQUARE_SIZE = 100;
    private GridPane chessBoard = new GridPane();
    private Stage gameStage = new Stage();
    private Controller controller;
    private BorderPane mainRoot;
    private BorderPane gameRoot;
    private GameLogic game;
    private Database data;
    private Stage winStage;
    private Stage stage;
    private Board board;

    public static int getSQUARE_SIZE() {
        return SQUARE_SIZE;
    }

    public GridPane getChessboard() {
        return chessBoard;
    }

    public View() {
        board = new Board();
        game = new GameLogic(board);
        data = new Database(game, board);
        controller = new Controller(game);
    }

    public static void main(String[] args) {
        View.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;
        mainRoot = new BorderPane();
        Scene mainScene = new Scene(mainRoot, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN);

        mainRoot.setTop(gameTitle());
        mainRoot.setCenter(mainMenuButton(stage));

        stage.getIcons().add(new Image("images/Icon.png"));
        stage.setTitle("Talabia Chess");
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();
    }

    // Author : Aminul
    // Description : Initialize Chess Game
    private void startGame() {
        gameRoot = new BorderPane();
        Scene gameScene = new Scene(gameRoot, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLACK);

        logUpdate();

        gameStage.getIcons().add(new Image("images/Icon.png"));
        gameStage.setTitle("Talabia Chess");
        gameStage.setResizable(false);
        gameStage.setScene(gameScene);
        gameStage.show();
    }

    // Author : Aminul
    // Description : Update Chess Game (if a winner declared, don't update)
    public void logUpdate() {

        if (game.getWinFlag() == true)

            gameWinnerPopup(game.getWinnerPlayer());

        else {

            gameRoot.setBottom(chessBoard());
            gameRoot.setTop(optionButton());
            System.out.println("Current Player: " + game.getCurrentPlayer().getPlayerID());
            for (int i = 0; i < game.getPlayers().size(); i++) {
                System.out.println("");
                System.out.println("Player " + (i + 1));
                System.out.println("Available Pieces: " + game.getPlayers().get(i).getPlayerPiece().size());
                System.out.println("Captured Pieces: " + game.getPlayers().get(i).getCapturedPiece().size());
            }
            System.out.println("------------------------------------------");

        }
    }

    // Author : Aminul
    // Description : Shows Game Title
    private HBox gameTitle() {
        HBox gameTitle = new HBox();
        gameTitle.setPadding(new Insets(50));
        gameTitle.setAlignment(Pos.CENTER);

        Text title = new Text();

        title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        title.setFill(Color.BLACK);
        title.setLineSpacing(20);
        title.setText("Talabia Chess");

        gameTitle.getChildren().add(title);

        return gameTitle;
    }

    // Author : Aminul
    // Description : Shows Main Menu Buttons
    private VBox mainMenuButton(Stage stage) {
        VBox mainMenuButton = new VBox();
        mainMenuButton.setPadding(new Insets(5, 20, 5, 10));
        mainMenuButton.setAlignment(Pos.CENTER);
        mainMenuButton.setSpacing(10);
        mainMenuButton.setStyle("-fx-padding: 20px; -fx-background-color: silver");

        Button startGame = new Button("New Game");
        Button loadGame = new Button("Load Game");
        Button exitGame = new Button("Exit");

        startGame.setPrefSize(150, 50);
        loadGame.setPrefSize(150, 50);
        exitGame.setPrefSize(150, 50);

        startGame.setOnAction(e -> {
            stage.close();
            game.initGame();
            startGame();
        });

        loadGame.setOnAction(e -> {
            stage.close();
            data.loadGame();
            startGame();
        });

        exitGame.setOnAction(e -> Platform.exit());

        mainMenuButton.getChildren().add(startGame);
        mainMenuButton.getChildren().add(loadGame);
        mainMenuButton.getChildren().add(exitGame);

        return mainMenuButton;
    }

    // Author : Aminul
    // Description : Shows Options During Gameplay
    private BorderPane optionButton() {
        BorderPane optionButton = new BorderPane();
        optionButton.setStyle("-fx-background-color: silver");

        Text playerLabel = new Text();
        playerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        playerLabel.setFill(Color.BLACK);
        playerLabel.setLineSpacing(20);
        playerLabel.setText(game.getCurrentPlayer().toString() + "'s Turn");

        HBox playerBox = new HBox();
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setPadding(new Insets(0, 0, 0, 20));
        playerBox.getChildren().add(playerLabel);

        Button returnButton = new Button("Exit Game");
        returnButton.setPrefSize(100, 50);
        // Buttons actions
        returnButton.setOnAction(e -> {
            saveGamePopup();
            gameStage.close();
            game.resetData();
            try {
                start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(returnButton);

        optionButton.setLeft(playerBox);
        optionButton.setRight(buttonBox);

        return optionButton;
    }

    // Author : Aminul
    // Description : Shows Chess Board with Pieces
    private GridPane chessBoard() {

        // Ensure captured piece does not appear on chessBoard
        for (Player player : game.getPlayers()) {
            board.getPieces().removeAll(player.getCapturedPiece());
        }

        flipBoard();

        // Set the handler for the entire chessboard
        chessBoard.setOnMouseClicked(controller.mouseClickedHandler(this));

        return chessBoard;
    }

    // Author : Aminul
    // Description : Flip Board Every Successful Turn
    private void flipBoard() {

        // Clear the existing pieces from the GridPane
        chessBoard.getChildren().clear();

        // Determine whether both players have completed their turns
        boolean shouldFlip = game.isPlayerMove();

        // Add the entire chessboard to the GridPane based on the current player's turn
        for (int i = 0; i < Board.getHEIGHT(); i++) {
            for (int j = 0; j < Board.getWIDTH(); j++) {
                int newRow = shouldFlip ? Board.getHEIGHT() - 1 - i : i;
                int newCol = shouldFlip ? Board.getWIDTH() - 1 - j : j;

                ImageView square = createSquare(newRow, newCol, game.getCurrentPlayer().getPlayerID());

                chessBoard.add(square, newCol, newRow);
            }
        }

        if (shouldFlip)
            game.increaseFlipCounter();

        // Update the positions of the pieces and add them to the GridPane
        for (GamePiece piece : board.getPieces()) {
            GamePieceView pieceView = new GamePieceView(piece);

            int newRow, newCol;

            if (shouldFlip) {
                newRow = Board.getHEIGHT() - 1 - piece.getCurrentRow();
                newCol = Board.getWIDTH() - 1 - piece.getCurrentCol();
            } else {
                newRow = piece.getCurrentRow();
                newCol = piece.getCurrentCol();
            }

            // Update the piece's position
            piece.setCurrentRow(newRow);
            piece.setCurrentCol(newCol);
            // Add the piece to the GridPane
            chessBoard.add(pieceView, newCol, newRow);
        }
    }

    // Author : Aminul
    // Description : Create Squares in Chess Board
    private static ImageView createSquare(int i, int j, int currentPlayerID) {
        ImageView square = new ImageView();
        square.setFitWidth(SQUARE_SIZE);
        square.setFitHeight(SQUARE_SIZE);
        square.setX(j * SQUARE_SIZE);
        square.setY(i * SQUARE_SIZE);

        // Adjust background colors based on the player's turn for the initial board
        // creation
        boolean isDarkSquare = (i + j) % 2 == 0;
        if (currentPlayerID == 2) {
            // If it's player 2's turn, invert the colors
            isDarkSquare = !isDarkSquare;
        }

        square.setImage(isDarkSquare ? new Image("/images/darkgreen.png") : new Image("/images/white.jpg"));

        return square;
    }

    // Author : Aminul & Luqman
    // Description : Shows Dot Representing Possible Moves for Pieces.
    public void showPossibleMoves(int selectedRow, int selectedCol) {
        System.out.println("Possible moves for the selected piece:");

        for (int newRow = 0; newRow < Board.HEIGHT; newRow++) {
            for (int newCol = 0; newCol < Board.WIDTH; newCol++) {
                try {
                    if (game.canMoveTo(selectedRow, selectedCol, newRow, newCol)) {
                        ImageView imageHolder = new ImageView();
                        Image image = new Image("/images/dot.png", SQUARE_SIZE, SQUARE_SIZE, false, false);
                        imageHolder.setImage(image);
                        chessBoard.add(imageHolder, newCol, newRow);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Author : Aminul
    // Description : Shows Popup to Save Game
    private void saveGamePopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.setTitle("Save Game");

        Label label = new Label("Do you want to save the game?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            // Handle 'Yes' button action
            data.saveGame();
            popupStage.close();
        });

        noButton.setOnAction(e -> {
            // Handle 'No' button action
            popupStage.close();
        });

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(yesButton, noButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(label, buttonLayout);
        popupLayout.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(popupLayout, 250, 150);
        popupStage.setScene(popupScene);

        // Prevent closing the popup using the 'X' button
        popupStage.setOnCloseRequest(event -> event.consume());

        popupStage.showAndWait(); // ShowAndWait ensures the popup is closed before continuing
    }

    // Author : Aminul
    // Description : Shows Popup when Player Wins
    public void gameWinnerPopup(int winningPlayer) {

        winStage = new Stage();
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.initStyle(StageStyle.UNDECORATED);

        BorderPane winBase = new BorderPane();
        winBase.setStyle("-fx-background-image: url('/images/winBackground.jpg');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;" +
                "-fx-border-style: solid;" +
                "-fx-border-color: black;");

        VBox winner = new VBox(20);
        winner.setAlignment(Pos.CENTER);
        winner.setPadding(new Insets(20, 25, 20, 25));
        winner.setStyle("-fx-background-color:#4B2D08;");

        Text winnerText = new Text();
        winnerText.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        winnerText.setFill(Color.WHITESMOKE);
        winnerText.setText("PLAYER " + (winningPlayer) + " WON ");

        winner.getChildren().add(winnerText);

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        buttons.setPadding(new Insets(15, 25, 40, 25));
        Button playAgainButton = new Button("Play Again");
        Button returnButton = new Button("Back To Main Menu");

        playAgainButton.setPrefSize(150, 50);
        playAgainButton.setOnAction(e -> {
            winStage.close();
            game.resetData();
            game.initGame();
            logUpdate();
        });

        returnButton.setPrefSize(150, 50);
        returnButton.setOnAction(e -> {
            winStage.close();
            gameStage.close();
            game.resetData();
            try {
                start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        buttons.getChildren().add(playAgainButton);
        buttons.getChildren().add(returnButton);

        winBase.setTop(winner);
        winBase.setBottom(buttons);

        Scene dialogScene = new Scene(winBase, 300, 300);
        winStage.setScene(dialogScene);
        winStage.show();
    }

}

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// MVC DESIGN PATTERN : MODEL
// Database class stores and retrieves data related to the game.
public class Database {

    private Board board;
    private GameLogic game;
    private final static String DELIM = ",";
    private int playerPieceSize[] = new int[2];
    private int capturedPieceSize[] = new int[2];

    public Database(GameLogic game, Board board) {
        this.game = game;
        this.board = board;
    }

    // Author : Aminul
    // Description : Save Game into CSV File
    public void saveGame() {
        try {
            FileWriter save = new FileWriter("save.txt");

            save.write(Integer.toString(game.getCurrentPlayer().getPlayerID() - 1) + DELIM);
            save.write(Integer.toString(game.getMoveCount()) + DELIM);
            save.write(Integer.toString(GameLogic.getFlipCounter()) + DELIM);

            // SAVE LIST SIZE
            for (Player player : game.getPlayers()) {
                save.write(Integer.toString(player.getPlayerPiece().size()) + DELIM);
                save.write(Integer.toString(player.getCapturedPiece().size()) + DELIM);
            }

            for (int i = 0; i < game.getPlayers().size(); i++) {

                for (GamePiece piece : game.getPlayers().get(i).getPlayerPiece()) {

                    save.write(Integer.toString(piece.getCurrentRow()) + DELIM);
                    save.write(Integer.toString(piece.getCurrentCol()) + DELIM);
                    save.write(Integer.toString(piece.invertCounter) + DELIM);
                    save.write(encryptType(piece.getType()) + DELIM);
                    save.write(encryptColor(piece.getColor()) + DELIM);
                    save.write(encryptState(piece.getStateType()) + DELIM);
                }

                for (GamePiece piece : game.getPlayers().get(i).getCapturedPiece()) {
                    save.write(Integer.toString(piece.getCurrentRow()) + DELIM);
                    save.write(Integer.toString(piece.getCurrentCol()) + DELIM);
                    save.write(Integer.toString(piece.invertCounter) + DELIM);
                    save.write(encryptType(piece.getType()) + DELIM);
                    save.write(encryptColor(piece.getColor()) + DELIM);
                    save.write(encryptState(piece.getStateType()) + DELIM);

                }
            }

            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Author : Aminul
    // Description : Load Game from CSV File
    public void loadGame() {
        try {
            Scanner reader = new Scanner(new FileReader("save.txt"));
            reader.useDelimiter(DELIM);

            game.resetData();

            game.setCurrentPlayer(Integer.valueOf(reader.next()));
            game.setMoveCount(Integer.valueOf(reader.next()));
            game.setFlipCounter(Integer.valueOf(reader.next()));

            for (int i = 0; i < game.getPlayers().size(); i++) {
                playerPieceSize[i] = Integer.valueOf(reader.next());
                capturedPieceSize[i] = Integer.valueOf(reader.next());
            }

            for (int i = 0; i < game.getPlayers().size(); i++) {
                for (int j = 0; j < playerPieceSize[i]; j++) {

                    int pieceRow = Integer.valueOf(reader.next());
                    int pieceColumn = Integer.valueOf(reader.next());
                    int invertCounter = Integer.valueOf(reader.next());
                    PieceType pieceType = decryptType(reader.next());
                    PieceColor pieceColor = decryptColor(reader.next());
                    PieceStateType stateType = decryptState(reader.next());

                    if (pieceType == PieceType.POINT) {
                        game.getPlayers().get(i)
                                .addPlayerPiece(new Point(pieceRow, pieceColumn, pieceColor, stateType, invertCounter));
                    } else if (pieceType == PieceType.PLUS) {
                        game.getPlayers().get(i)
                                .addPlayerPiece(new Plus(pieceRow, pieceColumn, pieceColor, stateType));
                    } else if (pieceType == PieceType.TIME) {
                        game.getPlayers().get(i)
                                .addPlayerPiece(new Time(pieceRow, pieceColumn, pieceColor, stateType));
                    } else if (pieceType == PieceType.HOURGLASS) {
                        game.getPlayers().get(i)
                                .addPlayerPiece(new Hourglass(pieceRow, pieceColumn, pieceColor, stateType));
                    } else if (pieceType == PieceType.SUN) {
                        game.getPlayers().get(i)
                                .addPlayerPiece(new Sun(pieceRow, pieceColumn, pieceColor, stateType));
                    }
                }

                for (int j = 0; j < capturedPieceSize[i]; j++) {

                    int pieceRow = Integer.valueOf(reader.next());
                    int pieceColumn = Integer.valueOf(reader.next());
                    int invertCounter = Integer.valueOf(reader.next());
                    PieceType pieceType = decryptType(reader.next());
                    PieceColor pieceColor = decryptColor(reader.next());
                    PieceStateType stateType = decryptState(reader.next());

                    if (pieceType == PieceType.POINT) {
                        game.getPlayers().get(i)
                                .addCapturedPiece(
                                        new Point(pieceRow, pieceColumn, pieceColor, stateType, invertCounter));
                    } else if (pieceType == PieceType.PLUS) {
                        game.getPlayers().get(i)
                                .addCapturedPiece(new Plus(pieceRow, pieceColumn, pieceColor, stateType));
                    } else if (pieceType == PieceType.TIME) {
                        game.getPlayers().get(i)
                                .addCapturedPiece(new Time(pieceRow, pieceColumn, pieceColor, stateType));
                    } else if (pieceType == PieceType.HOURGLASS) {
                        game.getPlayers().get(i)
                                .addCapturedPiece(new Hourglass(pieceRow, pieceColumn, pieceColor, stateType));
                    } else if (pieceType == PieceType.SUN) {
                        game.getPlayers().get(i)
                                .addCapturedPiece(new Sun(pieceRow, pieceColumn, pieceColor, stateType));
                    }
                }

                board.addPiece(game.getPlayers().get(i).getPlayerPiece());
                board.addPiece(game.getPlayers().get(i).getCapturedPiece());

            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Author : Aminul
    // Description : Convert Piece Type to String
    private String encryptType(PieceType type) {
        String string = null;
        if (type.equals(PieceType.POINT))
            string = "1";
        else if (type.equals(PieceType.PLUS))
            string = "2";
        else if (type.equals(PieceType.TIME))
            string = "3";
        else if (type.equals(PieceType.HOURGLASS))
            string = "4";
        else if (type.equals(PieceType.SUN))
            string = "5";
        return string;
    }

    // Author : Aminul
    // Description : Convert Piece Color to String
    public static String encryptColor(PieceColor color) {
        String string = null;
        if (color.equals(PieceColor.BLUE))
            string = "1";
        else if (color.equals(PieceColor.YELLOW))
            string = "2";
        return string;
    }

    // Author : Aminul
    // Description : Convert Piece State to String
    private String encryptState(PieceStateType state) {

        if (state.equals(PieceStateType.PLUS_STATE))
            return "1";
        else if (state.equals(PieceStateType.TIME_STATE))
            return "2";
        else
            return "3";

    }

    // Author : Aminul
    // Description : Convert String to Piece Type
    private PieceType decryptType(String string) {
        PieceType type = null;
        if (string.equals("1"))
            type = PieceType.POINT;
        else if (string.equals("2"))
            type = PieceType.PLUS;
        else if (string.equals("3"))
            type = PieceType.TIME;
        else if (string.equals("4"))
            type = PieceType.HOURGLASS;
        else if (string.equals("5"))
            type = PieceType.SUN;
        return type;
    }

    // Author : Aminul
    // Description : Convert String to Piece Color
    private PieceColor decryptColor(String string) {
        PieceColor color = null;
        if (string.equals("1"))
            color = PieceColor.BLUE;
        else if (string.equals("2"))
            color = PieceColor.YELLOW;
        return color;
    }

    // Author : Aminul
    // Description : Convert String to Piece State
    private PieceStateType decryptState(String string) {
        PieceStateType stateType = null;
        if (string.equals("1"))
            stateType = PieceStateType.PLUS_STATE;
        else if (string.equals("2"))
            stateType = PieceStateType.TIME_STATE;
        else
            stateType = PieceStateType.DEFAULT_STATE;
        return stateType;
    }
}
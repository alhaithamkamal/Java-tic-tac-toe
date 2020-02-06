package tictac.logic;
import tictac.database.*;
import java.util.ArrayList;
import tictac.ui.GameBodyB;

public class SingleMode extends Game {

    public SingleMode(boolean isRecorded, Player oppenent, User user, char myMark, GameBodyB ui) {
        super(isRecorded, Constants.SOLO, oppenent, user, myMark, ui);
    }
    /**
     * this function return a new board with computer movement depending on the
     * min and max algorithm
     * @param board : Board
     * @return bestChild : Board
     */
    private Board findBestMove(Board board) {
        ArrayList<Position> positions = board.getFreePositions();
        Board bestChild = null;
        int previous = Integer.MIN_VALUE;
        for (Position p : positions) {
            Board child = new Board(board, p, oppenentMark);
            int current = min(child);
            if (current > previous) {
                bestChild = child;
                previous = current;
            }
        }
        return bestChild;
    }
    public int max(Board board) {
        GameState gameState = board.getGameState(myMark, oppenentMark);
        if (null != gameState) {
            switch (gameState) {
                case OppWin:
                    return 1;
                case YouWin:
                    return -1;
                case Draw:
                    return 0;
                default:
                    break;
            }
        }
        ArrayList<Position> positions = board.getFreePositions();
        int best = Integer.MIN_VALUE;
        for (Position p : positions) {
            Board b = new Board(board, p, oppenentMark);
            int move = min(b);
            if (move > best) {
                best = move;
            }
        }
        return best;
    }
    public int min(Board board) {
        GameState gameState = board.getGameState(myMark, oppenentMark);
        if (null != gameState) {
            switch (gameState) {
                case OppWin:
                    return 1;
                case YouWin:
                    return -1;
                case Draw:
                    return 0;
                default:
                    break;
            }
        }
        ArrayList<Position> positions = board.getFreePositions();
        int best = Integer.MAX_VALUE;
        for (Position p : positions) {
            Board b = new Board(board, p, myMark);
            int move = max(b);
            if (move < best) {
                best = move;
            }
        }
        return best;
    }
    /**
     * overriding the abstract function play to suit single mode playing logic
     * @param x : integer (the row number in the board)
     * @param y : integer (the column number in the board)
     */
    @Override
    public void play(int x, int y) {
        System.out.println(board);
        int result = 4;
        if (!gameEnded) {
            Position position = null;
            if (myTurn) {
                position = makeMove(x, y);
                if (position != null) {
                    board = new Board(board, position, myMark);
                    ui.setText(buttons[x][y], myMark);
                    recordStep(x, y, Constants.MINE);
                    myTurn = !myTurn;
                    result = evaluateGame();
                } 
            }
            if (!myTurn && !board.getFreePositions().isEmpty()) {
                board = findBestMove(board);
                myTurn = !myTurn;
                drawBoardOnButtons(board);
                result = evaluateGame();
            }
        }
        showResult(result);
    }

}
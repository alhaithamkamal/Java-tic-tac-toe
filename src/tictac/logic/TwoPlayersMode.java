package tictac.logic;

import tictac.animation.GameOver;
import tictac.database.*;
import tictac.ui.GameBodyScreen;

public class TwoPlayersMode extends Game {

    public TwoPlayersMode(boolean isRecorded, Player oppenent, User user, char myMark, GameBodyScreen ui) {
        super(isRecorded, Constants.DUAL, oppenent, user, myMark, ui);
    }

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
                } else {
                    System.out.println("Already marked!");
                }
            }
            if (!myTurn && !board.getFreePositions().isEmpty()) {
                position = makeMove(x, y);
                if (position != null) {
                    board = new Board(board, position, oppenentMark);
                    ui.setText(buttons[x][y], oppenentMark);
                    recordStep(x, y, Constants.OPPENENT);
                    myTurn = !myTurn;
                    result = evaluateGame();
                } else {
                    System.out.println("Already marked!");
                }
            }
        }
        showResult(result);
    }
}
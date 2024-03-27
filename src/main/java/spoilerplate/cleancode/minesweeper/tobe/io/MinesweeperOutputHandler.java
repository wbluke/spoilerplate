package spoilerplate.cleancode.minesweeper.tobe.io;

import spoilerplate.cleancode.minesweeper.tobe.board.GameBoard;

public interface MinesweeperOutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard gameBoard);

    void showGameWinningComment();

    void showGameLosingComment();

    void showCommentForSelectingCell();

    void showCommentForUserAction();

    void showExceptionMessage(Throwable e);

}

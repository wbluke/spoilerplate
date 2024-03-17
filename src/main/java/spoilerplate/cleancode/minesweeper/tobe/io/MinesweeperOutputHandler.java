package spoilerplate.cleancode.minesweeper.tobe.io;

import spoilerplate.cleancode.minesweeper.tobe.board.GameBoard;

public interface MinesweeperOutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard gameBoard);

    void printGameWinningComment();

    void printGameLosingComment();

    void printCommentForSelectingCell();

    void printCommentForSelectedCell(String cellInput);

    void printCommentForUserAction(String cellInput);

    void printCommentForFlagAction(String cellInput);

    void printExceptionMessage(Throwable e);

}

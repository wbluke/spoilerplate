package spoilerplate.cleancode.minesweeper.tobe.minesweeper.io;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.user.UserAction;

public interface MinesweeperInputHandler {

    UserAction getUserActionFromUser();

    CellPosition getCellPositionFromUser();

}

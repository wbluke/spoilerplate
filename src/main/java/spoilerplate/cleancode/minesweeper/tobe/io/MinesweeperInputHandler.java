package spoilerplate.cleancode.minesweeper.tobe.io;

import spoilerplate.cleancode.minesweeper.tobe.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.user.UserAction;

public interface MinesweeperInputHandler {

    UserAction getUserActionFromUser();

    CellPosition getCellPositionFromUser();

}

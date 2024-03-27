package spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

public class EmptyCellSignProvider implements CellSignProvidable {

    private static final String EMPTY_SIGN = "■";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.getStatus() == CellSnapshotStatus.EMPTY;
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return EMPTY_SIGN;
    }

}

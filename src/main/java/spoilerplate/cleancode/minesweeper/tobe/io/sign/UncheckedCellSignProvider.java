package spoilerplate.cleancode.minesweeper.tobe.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshotStatus;

public class UncheckedCellSignProvider implements CellSignProvidable {

    private static final String UNCHECKED_SIGN = "□";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.getStatus() == CellSnapshotStatus.UNCHECKED;
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return UNCHECKED_SIGN;
    }

}

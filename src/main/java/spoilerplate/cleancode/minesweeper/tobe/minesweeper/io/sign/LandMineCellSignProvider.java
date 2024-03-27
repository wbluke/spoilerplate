package spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

public class LandMineCellSignProvider implements CellSignProvidable {

    private static final String LAND_MINE_SIGN = "☼";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.getStatus() == CellSnapshotStatus.LAND_MINE;
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return LAND_MINE_SIGN;
    }

}

package spoilerplate.cleancode.minesweeper.tobe.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshotStatus;

public class NumberCellSignProvider implements CellSignProvidable {

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.getStatus() == CellSnapshotStatus.NUMBER;
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return String.valueOf(cellSnapshot.getNearbyLandMineCount());
    }

}

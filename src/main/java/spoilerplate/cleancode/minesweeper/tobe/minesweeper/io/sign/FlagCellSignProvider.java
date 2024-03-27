package spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

public class FlagCellSignProvider implements CellSignProvidable {

    private static final String FLAG_SIGN = "⚑";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.getStatus() == CellSnapshotStatus.FLAG;
    }

    @Override
    public String provide(CellSnapshot cellSnapshot) {
        return FLAG_SIGN;
    }

}

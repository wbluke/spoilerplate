package spoilerplate.cleancode.minesweeper.tobe.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshotStatus;

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

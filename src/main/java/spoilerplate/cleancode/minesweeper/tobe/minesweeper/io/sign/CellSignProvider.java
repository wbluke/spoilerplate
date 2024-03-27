package spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

import java.util.Arrays;

public enum CellSignProvider implements CellSignProvidable {

    EMPTY(CellSnapshotStatus.EMPTY) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return EMPTY_SIGN;
        }
    },
    LAND_MINE(CellSnapshotStatus.LAND_MINE) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return LAND_MINE_SIGN;
        }
    },
    FLAG(CellSnapshotStatus.FLAG) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return FLAG_SIGN;
        }
    },
    NUMBER(CellSnapshotStatus.NUMBER) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.getNearbyLandMineCount());
        }
    },
    UNCHECKED(CellSnapshotStatus.UNCHECKED) {
        @Override
        public String provide(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGN;
        }
    },
    ;

    private static final String EMPTY_SIGN = "■";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String FLAG_SIGN = "⚑";
    private static final String UNCHECKED_SIGN = "□";

    private final CellSnapshotStatus cellSnapshotStatus;

    CellSignProvider(CellSnapshotStatus cellSnapshotStatus) {
        this.cellSnapshotStatus = cellSnapshotStatus;
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return this.cellSnapshotStatus == cellSnapshot.getStatus();
    }

    public static String findCellSignFrom(CellSnapshot cellSnapshot) {
        CellSignProvider provider = findBy(cellSnapshot);
        return provider.provide(cellSnapshot);
    }

    private static CellSignProvider findBy(CellSnapshot snapshot) {
        return Arrays.stream(values())
            .filter(provider -> provider.supports(snapshot))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }

}

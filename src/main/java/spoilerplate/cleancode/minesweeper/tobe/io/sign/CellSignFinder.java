package spoilerplate.cleancode.minesweeper.tobe.io.sign;

import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshot;

import java.util.List;

public class CellSignFinder {

    private static final List<CellSignProvidable> CELL_SIGN_PROVIDERS = List.of(
        new EmptyCellSignProvider(),
        new LandMineCellSignProvider(),
        new FlagCellSignProvider(),
        new UncheckedCellSignProvider(),
        new NumberCellSignProvider()
    );

    public String findCellSignFrom(CellSnapshot cellSnapshot) {
        return CELL_SIGN_PROVIDERS.stream()
            .filter(provider -> provider.supports(cellSnapshot))
            .findFirst()
            .map(provider -> provider.provide(cellSnapshot))
            .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }

}

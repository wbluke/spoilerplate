package spoilerplate.cleancode.minesweeper.tobe.board.cell;

import java.util.Arrays;
import java.util.List;

public class Cells {

    private final List<Cell> cellList;

    private Cells(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public static Cells of(List<Cell> cellList) {
        return new Cells(cellList);
    }

    public static Cells of(Cell[][] cells) {
        List<Cell> cellList = Arrays.stream(cells)
            .flatMap(Arrays::stream)
            .toList();
        return of(cellList);
    }

    public boolean isAllCellChecked() {
        return cellList.stream()
            .allMatch(Cell::isChecked);
    }

}

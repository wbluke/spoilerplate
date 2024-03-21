package spoilerplate.cleancode.minesweeper.tobe.board.position;

import java.util.Objects;

public class CellPosition {

    private final int rowIndex;
    private final int colIndex;

    private CellPosition(int rowIndex, int colIndex) {
        if (rowIndex < 0 || colIndex < 0) {
            throw new IllegalArgumentException("올바르지 않은 좌표입니다.");
        }

        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public static CellPosition of(int rowIndex, int colIndex) {
        return new CellPosition(rowIndex, colIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && colIndex == that.colIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        return CellPosition.of(
            rowIndex + relativePosition.getDeltaRow(),
            colIndex + relativePosition.getDeltaCol()
        );
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        int nextRowIndex = rowIndex + relativePosition.getDeltaRow();
        int nextColIndex = colIndex + relativePosition.getDeltaCol();

        return nextRowIndex >= 0
            && nextColIndex >= 0;
    }

    public boolean isRowIndexLessThan(int rowSize) {
        return rowIndex < rowSize;
    }

    public boolean isColIndexLessThan(int colSize) {
        return colIndex < colSize;
    }

}

package spoilerplate.cleancode.minesweeper.tobe;

public class Cell {

    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";

    private int landMineCount;
    private boolean isLandMine;
    private boolean isFlagged;
    private boolean isOpened;

    private Cell(int landMineCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
        this.landMineCount = landMineCount;
        this.isLandMine = isLandMine;
        this.isFlagged = isFlagged;
        this.isOpened = isOpened;
    }

    public static Cell of() {
        return new Cell(0, false, false, false);
    }

    public void updateLandMineCount(int landMineCount) {
        this.landMineCount = landMineCount;
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isLandMine() {
        return this.isLandMine;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public boolean isAlreadyOpened() {
        return this.isOpened;
    }

    public boolean hasLandMineCount() {
        return this.landMineCount != 0;
    }

    public String getSign() {
        if (isOpened) {
            if (isLandMine) {
                return LAND_MINE_SIGN;
            }
            if (hasLandMineCount()) {
                return String.valueOf(landMineCount);
            }
            return EMPTY_SIGN;
        }

        if (isFlagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }

}

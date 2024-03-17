package spoilerplate.cleancode.minesweeper.tobe.board.cell;

public interface Cell {

    String FLAG_SIGN = "⚑";
    String UNCHECKED_SIGN = "□";

    boolean isLandMine();

    boolean hasLandMineCount();

    String getSign();

    void flag();

    void open();

    boolean isChecked();

    boolean isAlreadyOpened();

}

package spoilerplate.cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private static final int BOARD_ROW_SIZE = 8;
    private static final int BOARD_COL_SIZE = 10;
    private static final int LAND_MINE_COUNT = 10;

    private final Cell[][] board;

    public GameBoard() {
        board = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(int row, int col) {
        Cell cell = findCell(row, col);
        return cell.getSign();
    }

    public void flag(int row, int col) {
        Cell cell = findCell(row, col);
        cell.flag();
    }

    public void open(int row, int col) {
        Cell cell = findCell(row, col);
        cell.open();
    }

    public boolean isLandMine(int row, int col) {
        Cell cell = findCell(row, col);
        return cell.isLandMine();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
            .flatMap(Arrays::stream)
            .allMatch(Cell::isChecked);
    }

    public boolean isAlreadyOpened(int row, int col) {
        Cell cell = findCell(row, col);
        return cell.isAlreadyOpened();
    }

    public boolean hasLandMineCount(int row, int col) {
        Cell cell = findCell(row, col);
        return cell.hasLandMineCount();
    }

    private Cell findCell(int row, int col) {
        return board[row][col];
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.of();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int width = new Random().nextInt(colSize);
            int height = new Random().nextInt(rowSize);
            turnOnLandMine(height, width);
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMine(row, col)) {
                    continue;
                }
                int count = countSurroundLandMines(row, col);
                Cell cell = findCell(row, col);
                cell.updateNearbyLandMineCount(count);
            }
        }
    }

    private void turnOnLandMine(int height, int width) {
        Cell cell = findCell(height, width);
        cell.turnOnLandMine();
    }

    private int countSurroundLandMines(int row, int col) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMine(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMine(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMine(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMine(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMine(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMine(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMine(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMine(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

}

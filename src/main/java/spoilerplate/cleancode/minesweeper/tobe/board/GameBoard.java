package spoilerplate.cleancode.minesweeper.tobe.board;

import spoilerplate.cleancode.minesweeper.tobe.board.cell.Cell;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.EmptyCell;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.LandMineCell;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.NumberCell;
import spoilerplate.cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
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
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int col = new Random().nextInt(colSize);
            int row = new Random().nextInt(rowSize);

            board[row][col] = new LandMineCell();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMine(row, col)) {
                    continue;
                }
                int count = countSurroundLandMines(row, col);
                if (count == 0) {
                    continue;
                }

                board[row][col] = new NumberCell(count);
            }
        }
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

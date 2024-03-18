package spoilerplate.cleancode.minesweeper.tobe.board;

import spoilerplate.cleancode.minesweeper.tobe.board.cell.Cell;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.EmptyCell;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.LandMineCell;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.NumberCell;
import spoilerplate.cleancode.minesweeper.tobe.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.board.position.RelativePosition;
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

    public String getSign(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSign();
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public boolean isLandMineAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
            .flatMap(Arrays::stream)
            .allMatch(Cell::isChecked);
    }

    private boolean isOpened(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isOpened();
    }

    private boolean hasLandMineCountAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.hasLandMineCount();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
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
                CellPosition cellPosition = CellPosition.of(row, col);
                if (isLandMineAt(cellPosition)) {
                    continue;
                }
                int count = countSurroundedLandMines(cellPosition);
                if (count == 0) {
                    continue;
                }

                board[row][col] = new NumberCell(count);
            }
        }
    }

    private int countSurroundedLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        long count = RelativePosition.SURROUNDED_POSITIONS.stream()
            .filter(relativePosition -> cellPosition.canMoveBy(relativePosition, rowSize, colSize))
            .map(cellPosition::moveBy)
            .filter(this::isLandMineAt)
            .count();

        return (int) count;
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        if (isOpened(cellPosition)) {
            return;
        }
        if (isLandMineAt(cellPosition)) {
            return;
        }

        openAt(cellPosition);

        if (hasLandMineCountAt(cellPosition)) {
            return;
        }

        RelativePosition.SURROUNDED_POSITIONS.stream()
            .filter(relativePosition -> cellPosition.canMoveBy(relativePosition, getRowSize(), getColSize()))
            .map(cellPosition::moveBy)
            .forEach(this::openSurroundedCells);
    }

}

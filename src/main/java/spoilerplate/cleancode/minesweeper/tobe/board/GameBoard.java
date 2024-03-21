package spoilerplate.cleancode.minesweeper.tobe.board;

import spoilerplate.cleancode.minesweeper.tobe.board.cell.*;
import spoilerplate.cleancode.minesweeper.tobe.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.board.position.CellPositions;
import spoilerplate.cleancode.minesweeper.tobe.board.position.RelativePosition;
import spoilerplate.cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.List;

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
        Cells cells = Cells.of(board);
        return cells.isAllCellChecked();
    }

    public void initializeGame() {
        CellPositions cellPositions = CellPositions.from(board);

        initializeEmptyCells(cellPositions);

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMinePositions);

        initializeNumberCells(cellPositions, landMinePositions);
    }

    private void initializeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions = cellPositions.getAllPositions();

        for (CellPosition position : allPositions) {
            board[position.getRowIndex()][position.getColIndex()] = new EmptyCell();
        }
    }

    private void initializeLandMineCells(List<CellPosition> landMinePositions) {
        for (CellPosition position : landMinePositions) {
            board[position.getRowIndex()][position.getColIndex()] = new LandMineCell();
        }
    }

    private void initializeNumberCells(CellPositions cellPositions, List<CellPosition> landMinePositions) {
        List<CellPosition> numberCellCandidates = cellPositions.subtract(landMinePositions);

        numberCellCandidates.forEach(candidatePosition -> {
            int count = countSurroundedLandMines(candidatePosition);
            if (count != 0) {
                board[candidatePosition.getRowIndex()][candidatePosition.getColIndex()] = new NumberCell(count);
            }
        });
    }

    private int countSurroundedLandMines(CellPosition cellPosition) {
        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition);

        long count = surroundedPositions.stream()
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

        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition);
        surroundedPositions.forEach(this::openSurroundedCells);
    }

    private boolean isOpened(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isOpened();
    }

    private boolean hasLandMineCountAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.hasLandMineCount();
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        return RelativePosition.SURROUNDED_POSITIONS.stream()
            .filter(cellPosition::canCalculatePositionBy)
            .map(cellPosition::calculatePositionBy)
            .filter(position -> position.isRowIndexLessThan(rowSize))
            .filter(position -> position.isColIndexLessThan(colSize))
            .toList();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

}

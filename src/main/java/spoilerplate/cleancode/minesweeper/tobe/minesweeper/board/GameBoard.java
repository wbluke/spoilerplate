package spoilerplate.cleancode.minesweeper.tobe.minesweeper.board;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.*;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.position.CellPositions;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.position.RelativePosition;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
        gameStatus = GameStatus.IN_PROGRESS;
    }

    public void initializeGame() {
        gameStatus = GameStatus.IN_PROGRESS;
        CellPositions cellPositions = CellPositions.from(board);

        initializeEmptyCells(cellPositions);

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
        initializeLandMineCells(landMinePositions);

        initializeNumberCells(cellPositions, landMinePositions);
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();

        checkIfGameIsOver();
    }

    public void openAt(CellPosition cellPosition) {
        if (isLandMineAt(cellPosition)) {
            openLandMineCell(cellPosition);
            return;
        }

//        openSurroundedCells(cellPosition);
        openSurroundedCells2(cellPosition);
        checkIfGameIsOver();
    }

    public boolean isInProgress() {
        return this.gameStatus == GameStatus.IN_PROGRESS;
    }

    public boolean isWinning() {
        return this.gameStatus == GameStatus.WIN;
    }

    public boolean isLosing() {
        return this.gameStatus == GameStatus.LOSE;
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        return cellPosition.isRowIndexMoreThanOrEqual(getRowSize())
            || cellPosition.isColIndexMoreThanOrEqual(getColSize());
    }

    public CellSnapshot getCellSnapshot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getCellSnapshot();
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
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

    private boolean isLandMineAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    private void checkIfGameIsOver() {
        if (isAllCellChecked()) {
            gameStatus = GameStatus.WIN;
        }
    }

    private boolean isAllCellChecked() {
        Cells cells = Cells.of(board);
        return cells.isAllCellChecked();
    }

    private void openLandMineCell(CellPosition cellPosition) {
        openOneCell(cellPosition);

        gameStatus = GameStatus.LOSE;
    }

    private void openSurroundedCells(CellPosition cellPosition) {
        if (isOpened(cellPosition)) {
            return;
        }
        if (isLandMineAt(cellPosition)) {
            return;
        }

        openOneCell(cellPosition);

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

    private void openOneCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
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

    // ============================================================
    private void openSurroundedCells2(CellPosition cellPosition) {
        Stack<CellPosition> stack = new Stack<>();
        Deque<CellPosition> deque = new ArrayDeque<>();
        deque.push(cellPosition);

        while (!deque.isEmpty()) {
            openAndPushCellAt(deque);
        }
    }

    private void openAndPushCellAt(Deque<CellPosition> stack) {
        CellPosition current = stack.pop();
        if (isOpened(current)) {
            return;
        }
        if (isLandMineAt(current)) {
            return;
        }

        openOneCell(current);

        if (hasLandMineCountAt(current)) {
            return;
        }

        List<CellPosition> surroundedPositions = calculateSurroundedPositions(current);
        for (CellPosition surroundedPosition : surroundedPositions) {
            stack.push(surroundedPosition);
        }
    }

}

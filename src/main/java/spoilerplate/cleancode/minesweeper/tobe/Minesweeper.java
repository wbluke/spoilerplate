package spoilerplate.cleancode.minesweeper.tobe;

import spoilerplate.cleancode.minesweeper.tobe.board.BoardIndexConverter;
import spoilerplate.cleancode.minesweeper.tobe.board.GameBoard;
import spoilerplate.cleancode.minesweeper.tobe.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.config.GameConfig;
import spoilerplate.cleancode.minesweeper.tobe.game.GameInitializable;
import spoilerplate.cleancode.minesweeper.tobe.game.GameRunnable;
import spoilerplate.cleancode.minesweeper.tobe.io.MinesweeperInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.io.MinesweeperOutputHandler;

public class Minesweeper implements GameInitializable, GameRunnable {

    private final GameBoard gameBoard;
    private final MinesweeperInputHandler inputHandler;
    private final MinesweeperOutputHandler outputHandler;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    public Minesweeper(GameConfig gameConfig) {
        this.gameBoard = new GameBoard(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();

        while (gameBoard.isInProgress()) {
            outputHandler.showBoard(gameBoard);

            String cellInput = getCellInputFromUser();
            String userActionInput = getUserActionInputFromUser();
            try {
                actOnCell(cellInput, userActionInput);
            } catch (IllegalArgumentException e) {
                outputHandler.showExceptionMessage(e);
            }
        }

        outputHandler.showBoard(gameBoard);

        if (gameBoard.isWinning()) {
            outputHandler.showGameWinningComment();
        }
        if (gameBoard.isLosing()) {
            outputHandler.showGameLosingComment();
        }
    }

    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = boardIndexConverter.convertColIndex(cellInput, gameBoard.getColSize());
        int selectedRowIndex = boardIndexConverter.convertRowIndex(cellInput, gameBoard.getRowSize());
        CellPosition selectedCellPosition = CellPosition.of(selectedRowIndex, selectedColIndex);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(selectedCellPosition);
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            gameBoard.openAt(selectedCellPosition);
            return;
        }

        throw new IllegalArgumentException("잘못된 번호를 선택하셨습니다.");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private String getUserActionInputFromUser() {
        outputHandler.showCommentForUserAction();
        return inputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        String cellInput = inputHandler.getUserInput();

        outputHandler.showCommentForSelectedCell(cellInput);
        return cellInput;
    }

}

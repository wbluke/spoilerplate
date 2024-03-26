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
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameConfig gameConfig) {
        this.gameBoard = new GameBoard(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
    }

    public void initialize() {
        gameBoard.initializeGame();
    }

    public void run() {
        outputHandler.showGameStartComments();

        while (true) {
            outputHandler.showBoard(gameBoard);

            if (doesUserWinTheGame()) {
                outputHandler.showGameWinningComment();
                break;
            }
            if (doesUserLoseTheGame()) {
                outputHandler.showGameLosingComment();
                break;
            }

            String cellInput = getCellInputFromUser();
            String userActionInput = getUserActionInputFromUser(cellInput);
            try {
                actOnCell(cellInput, userActionInput);
            } catch (IllegalArgumentException e) {
                outputHandler.showExceptionMessage(e);
            }
        }
    }

    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = boardIndexConverter.convertColIndex(cellInput, gameBoard.getColSize());
        int selectedRowIndex = boardIndexConverter.convertRowIndex(cellInput, gameBoard.getRowSize());
        CellPosition selectedCellPosition = CellPosition.of(selectedRowIndex, selectedColIndex);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(selectedCellPosition);
            outputHandler.showCommentForFlagAction(cellInput);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (doesUserPickLandMine(selectedCellPosition)) {
                gameBoard.openAt(selectedCellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedCellPosition);
            checkIfGameIsOver();
            return;
        }

        throw new IllegalArgumentException("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserPickLandMine(CellPosition cellPosition) {
        return gameBoard.isLandMineAt(cellPosition);
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private String getUserActionInputFromUser(String cellInput) {
        outputHandler.showCommentForUserAction(cellInput);
        return inputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        String cellInput = inputHandler.getUserInput();

        outputHandler.showCommentForSelectedCell(cellInput);
        return cellInput;
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

}

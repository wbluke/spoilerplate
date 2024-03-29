package spoilerplate.cleancode.minesweeper.tobe.minesweeper;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.exception.GameException;
import spoilerplate.cleancode.minesweeper.tobe.game.GameInitializable;
import spoilerplate.cleancode.minesweeper.tobe.game.GameRunnable;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.MinesweeperInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.MinesweeperOutputHandler;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.user.UserAction;

public class Minesweeper implements GameInitializable, GameRunnable {

    private final GameBoard gameBoard;
    private final MinesweeperInputHandler inputHandler;
    private final MinesweeperOutputHandler outputHandler;

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

            try {
                CellPosition selectedCellPosition = getCellInputFromUser();
                UserAction userAction = getUserActionInputFromUser();

                actOnCell(selectedCellPosition, userAction);
            } catch (GameException e) {
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

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();

        CellPosition cellPosition = inputHandler.getCellPositionFromUser();
        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException("잘못된 번호를 선택하셨습니다.");
        }

        return cellPosition;
    }

    private UserAction getUserActionInputFromUser() {
        outputHandler.showCommentForUserAction();

        return inputHandler.getUserActionFromUser();
    }

    private void actOnCell(CellPosition selectedCellPosition, UserAction userAction) {
        if (doesUserChooseToPlantFlag(userAction)) {
            gameBoard.flagAt(selectedCellPosition);
            return;
        }

        if (doesUserChooseToOpenCell(userAction)) {
            gameBoard.openAt(selectedCellPosition);
            return;
        }

        throw new GameException("잘못된 번호를 선택하셨습니다.");
    }

    private boolean doesUserChooseToPlantFlag(UserAction userAction) {
        return userAction == UserAction.FLAG;
    }

    private boolean doesUserChooseToOpenCell(UserAction userAction) {
        return userAction == UserAction.OPEN;
    }

}

package spoilerplate.cleancode.minesweeper.tobe;

import spoilerplate.cleancode.minesweeper.tobe.io.ConsoleInput;
import spoilerplate.cleancode.minesweeper.tobe.io.ConsoleOutput;

public class Minesweeper {

    private final GameBoard gameBoard = new GameBoard();
    private final ConsoleInput consoleInput = new ConsoleInput();
    private final ConsoleOutput consoleOutput = new ConsoleOutput();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public void run() {
        consoleOutput.showGameStartComments();
        gameBoard.initializeGame();

        while (true) {
            consoleOutput.showBoard(gameBoard);

            if (doesUserWinTheGame()) {
                consoleOutput.printGameWinningComment();
                break;
            }
            if (doesUserLoseTheGame()) {
                consoleOutput.printGameLosingComment();
                break;
            }

            String cellInput = getCellInputFromUser();
            String userActionInput = getUserActionInputFromUser(cellInput);
            try {
                actOnCell(cellInput, userActionInput);
            } catch (IllegalArgumentException e) {
                consoleOutput.printExceptionMessage(e);
            }
        }
    }

    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            consoleOutput.printCommentForFlagAction(cellInput);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (doesUserPickLandMine(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        throw new IllegalArgumentException("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserPickLandMine(int selectedRow, int selectedCol) {
        return gameBoard.isLandMine(selectedRow, selectedCol);
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private String getUserActionInputFromUser(String cellInput) {
        consoleOutput.printCommentForUserAction(cellInput);
        return consoleInput.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutput.printCommentForSelectingCell();
        String cellInput = consoleInput.getUserInput();

        consoleOutput.printCommentForSelectedCell(cellInput);
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

    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex >= gameBoard.getRowSize()) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private void open(int row, int col) {
        if (row < 0 || row >= gameBoard.getRowSize() || col < 0 || col >= gameBoard.getColSize()) {
            return;
        }
        if (gameBoard.isAlreadyOpened(row, col)) {
            return;
        }
        if (gameBoard.isLandMine(row, col)) {
            return;
        }

        gameBoard.open(row, col);

        if (gameBoard.hasLandMineCount(row, col)) {
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}

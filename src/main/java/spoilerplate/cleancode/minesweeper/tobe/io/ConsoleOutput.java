package spoilerplate.cleancode.minesweeper.tobe.io;

import spoilerplate.cleancode.minesweeper.tobe.GameBoard;

public class ConsoleOutput {

    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void showBoard(GameBoard gameBoard) {
        int rowSize = gameBoard.getRowSize();
        int colSize = gameBoard.getColSize();

        System.out.println("   A B C D E F G H I J");
        for (int row = 0; row < rowSize; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < colSize; col++) {
                System.out.print(gameBoard.getSign(row, col) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    public void printGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    public void printCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    public void printCommentForSelectedCell(String cellInput) {
        System.out.println(cellInput + " 셀을 선택하셨습니다.");
    }

    public void printCommentForUserAction(String cellInput) {
        System.out.println(cellInput + " 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    public void printCommentForFlagAction(String cellInput) {
        System.out.println(cellInput + " 셀에 깃발을 꽂았습니다.");
    }

    public void printExceptionMessage(Throwable e) {
        System.out.println(e.getMessage());
    }

}

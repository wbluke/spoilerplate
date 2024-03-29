package spoilerplate.cleancode.minesweeper.tobe.minesweeper.io;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.GameBoard;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.sign.CellSignFinder;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.sign.CellSignProvider;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements MinesweeperOutputHandler {

    private final CellSignFinder cellSignFinder = new CellSignFinder();

    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard gameBoard) {
        int rowSize = gameBoard.getRowSize();
        int colSize = gameBoard.getColSize();

        System.out.println("    " + generateColAlphabets(colSize));

        for (int row = 0; row < rowSize; row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < colSize; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                CellSnapshot cellSnapshot = gameBoard.getCellSnapshot(cellPosition);

//                String cellSign = cellSignFinder.findCellSignFrom(cellSnapshot);
                String cellSign = CellSignProvider.findCellSignFrom(cellSnapshot);
                System.out.print(cellSign + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String generateColAlphabets(int colSize) {
        List<String> alphabets = IntStream.range(0, colSize)
            .mapToObj(index -> (char) ('a' + index))
            .map(Object::toString)
            .toList();
        return String.join(" ", alphabets);
    }

    @Override
    public void showGameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCommentForSelectingCell() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void showCommentForUserAction() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showExceptionMessage(Throwable e) {
        System.out.println(e.getMessage());
    }

}

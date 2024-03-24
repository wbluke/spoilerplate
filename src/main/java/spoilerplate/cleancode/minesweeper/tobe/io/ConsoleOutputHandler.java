package spoilerplate.cleancode.minesweeper.tobe.io;

import spoilerplate.cleancode.minesweeper.tobe.board.GameBoard;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshot;
import spoilerplate.cleancode.minesweeper.tobe.board.cell.CellSnapshotStatus;
import spoilerplate.cleancode.minesweeper.tobe.board.position.CellPosition;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements MinesweeperOutputHandler {

    private static final String EMPTY_SIGN = "■";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String FLAG_SIGN = "⚑";
    private static final String UNCHECKED_SIGN = "□";

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

                String cellSign = decideCellSignFrom(cellSnapshot);
//                String cellSign = CellSignFinder2.findCellSignFrom(cellSnapshot);
                System.out.print(cellSign + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String decideCellSignFrom(CellSnapshot cellSnapshot) {
        CellSnapshotStatus status = cellSnapshot.getStatus();
        if (status == CellSnapshotStatus.EMPTY) {
            return EMPTY_SIGN;
        }
        if (status == CellSnapshotStatus.FLAG) {
            return FLAG_SIGN;
        }
        if (status == CellSnapshotStatus.LAND_MINE) {
            return LAND_MINE_SIGN;
        }
        if (status == CellSnapshotStatus.UNCHECKED) {
            return UNCHECKED_SIGN;
        }
        if (status == CellSnapshotStatus.NUMBER) {
            return String.valueOf(cellSnapshot.getNearbyLandMineCount());
        }
        throw new IllegalArgumentException("게임판을 그릴 수 없습니다.");
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
    public void showCommentForSelectedCell(String cellInput) {
        System.out.println(cellInput + " 셀을 선택하셨습니다.");
    }

    @Override
    public void showCommentForUserAction(String cellInput) {
        System.out.println(cellInput + " 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showCommentForFlagAction(String cellInput) {
        System.out.println(cellInput + " 셀에 깃발을 꽂았습니다.");
    }

    @Override
    public void showExceptionMessage(Throwable e) {
        System.out.println(e.getMessage());
    }

}

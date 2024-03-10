package spoilerplate.cleancode.minesweeper.tobe.board;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int convertRowIndex(String cellInput, int boardRowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, boardRowSize);
    }

    public int convertColIndex(String cellInput, int boardColSize) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol, boardColSize);
    }

    private int convertRowFrom(String cellInputRow, int boardRowSize) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;

        if (rowIndex < 0 || rowIndex >= boardRowSize) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol, int boardColSize) {
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;

        if (colIndex < 0 || colIndex >= boardColSize) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return colIndex;
    }

}

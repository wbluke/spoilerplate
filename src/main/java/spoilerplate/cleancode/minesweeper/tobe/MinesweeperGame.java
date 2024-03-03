package spoilerplate.cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// 보드와 지뢰는 각각 따로 있는 상태 -> 리팩토링 : 보드와 지뢰를 하나의 클래스로 통합
// 고정 길이 배열의 형태 -> 요구사항 : 게임 난이도에 따라 길이 변경하도록 리팩토링
// main 분리
public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final String[][] BOARD = new String[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final Integer[][] LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final String FLAG_SIGN = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String CLOSED_CELL_SIGN = "□";
    public static final String OPENED_CELL_SIGN = "■";
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();
        initializeGame();

        while (true) {
            showBoard();

            if (doesUserWinTheGame()) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (doesUserLoseTheGame()) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }

            String cellInput = getCellInputFromUser();
            String userActionInput = getUserActionInputFromUser(cellInput);
            try {
                actOnCell(cellInput, userActionInput);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void actOnCell(String cellInput, String userActionInput) {
        int selectedCol = getSelectedCol(cellInput);
        int selectedRow = getSelectedRow(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRow][selectedCol] = FLAG_SIGN;
            System.out.println(cellInput + " 셀에 깃발을 꽂았습니다.");
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (doesUserPickLandMine(selectedRow, selectedCol)) {
                BOARD[selectedRow][selectedCol] = LAND_MINE_SIGN;
                changeGameStatusToLose();
                return;
            }

            open(selectedRow, selectedCol);
            checkIfGameIsOver();
            return;
        }

        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean doesUserPickLandMine(int selectedRow, int selectedCol) {
        return LAND_MINES[selectedRow][selectedCol];
    }

    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private static int getSelectedRow(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private static int getSelectedCol(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    private static String getUserActionInputFromUser(String cellInput) {
        System.out.println(cellInput + " 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();
    }

    private static String getCellInputFromUser() {
        System.out.println("오픈할 좌표를 입력하세요. (예: a1)");
        String cellInput = SCANNER.nextLine();

        System.out.println(cellInput + " 셀을 선택하셨습니다.");
        return cellInput;
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellOpened() {
        return Arrays.stream(BOARD)
            .flatMap(Arrays::stream)
            .noneMatch(CLOSED_CELL_SIGN::equals);
    }

    private static int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex >= BOARD_ROW_SIZE) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private static int convertColFrom(char cellInputCol) {
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

    private static void showBoard() {
        System.out.println("   A B C D E F G H I J");
        for (int i = 0; i < BOARD_ROW_SIZE; i++) {
            System.out.printf("%d  ", i + 1);
            for (int j = 0; j < BOARD_COL_SIZE; j++) {
                System.out.print(BOARD[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = CLOSED_CELL_SIGN;
            }
        }

        for (int i = 0; i < 10; i++) {
            int width = new Random().nextInt(BOARD_COL_SIZE);
            int height = new Random().nextInt(BOARD_ROW_SIZE);
            LAND_MINES[height][width] = true;
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                if (LAND_MINES[row][col]) {
                    LAND_MINE_COUNTS[row][col] = 0;
                    continue;
                }
                int count = countSurroundLandMines(row, col);
                LAND_MINE_COUNTS[row][col] = count;
            }
        }
    }

    private static int countSurroundLandMines(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && LAND_MINES[row - 1][col - 1]) {
            count++;
        }
        if (row - 1 >= 0 && LAND_MINES[row - 1][col]) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && LAND_MINES[row - 1][col + 1]) {
            count++;
        }
        if (col - 1 >= 0 && LAND_MINES[row][col - 1]) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && LAND_MINES[row][col + 1]) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && LAND_MINES[row + 1][col - 1]) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && LAND_MINES[row + 1][col]) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && LAND_MINES[row + 1][col + 1]) {
            count++;
        }
        return count;
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (!BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
            return;
        }
        if (LAND_MINES[row][col]) {
            return;
        }
        if (LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = String.valueOf(LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            BOARD[row][col] = OPENED_CELL_SIGN;
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

package spoilerplate.cleancode.minesweeper.tobe;

import java.util.Random;
import java.util.Scanner;

// 보드와 지뢰는 각각 따로 있는 상태 -> 리팩토링 : 보드와 지뢰를 하나의 클래스로 통합
// 고정 길이 배열의 형태 -> 요구사항 : 게임 난이도에 따라 길이 변경하도록 리팩토링
// main 분리
public class MinesweeperGame {

    private static String[][] board = new String[8][10];
    private static Integer[][] landMineCounts = new Integer[8][10];
    private static boolean[][] landMines = new boolean[8][10];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        showGameStartComments();
        Scanner scanner = new Scanner(System.in);
        initializeGame();
        while (true) {
            showBoard();
            if (gameStatus == 1) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            if (gameStatus == -1) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            System.out.println();
            System.out.println("오픈할 좌표를 입력하세요. (예: a1)");
            String cellInput = scanner.nextLine();
            System.out.println(cellInput + " 셀을 선택하셨습니다.");
            System.out.println(cellInput + " 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
            String userActionInput = scanner.nextLine();
            char cellInputCol = cellInput.charAt(0);
            char cellInputRow = cellInput.charAt(1);
            int selectedCol = convertColFrom(cellInputCol);
            int selectedRow = convertRowFrom(cellInputRow);
            if (userActionInput.equals("2")) {
                board[selectedRow][selectedCol] = "⚑";
                System.out.println(cellInput + " 셀에 깃발을 꽂았습니다.");
                checkIfGameIsOver();
                continue;
            }
            if (landMines[selectedRow][selectedCol]) {
                board[selectedRow][selectedCol] = "☼";
                gameStatus = -1;
                continue;
            } else {
                open(selectedRow, selectedCol);
            }
            checkIfGameIsOver();
        }
    }

    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        if (isAllOpened) {
            gameStatus = 1;
        }
    }

    private static boolean isAllCellOpened() {
        boolean isAllOpened = true;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                if (board[row][col] == "□") {
                    isAllOpened = false;
                }
            }
        }
        return isAllOpened;
    }

    private static int convertRowFrom(char cellInputRow) {
        return Character.getNumericValue(cellInputRow) - 1;
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
                return -1;
        }
    }

    private static void showBoard() {
        System.out.println("   A B C D E F G H I J");
        for (int i = 0; i < 8; i++) {
            System.out.printf("%d  ", i + 1);
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void initializeGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = "□";
            }
        }
        for (int i = 0; i < 10; i++) {
            int width = new Random().nextInt(10);
            int height = new Random().nextInt(8);
            landMines[height][width] = true;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int count = 0;
                if (landMines[row][col]) {
                    landMineCounts[row][col] = 0;
                    continue;
                }
                if (row - 1 >= 0 && col - 1 >= 0 && landMines[row - 1][col - 1]) {
                    count++;
                }
                if (row - 1 >= 0 && landMines[row - 1][col]) {
                    count++;
                }
                if (row - 1 >= 0 && col + 1 < 10 && landMines[row - 1][col + 1]) {
                    count++;
                }
                if (col - 1 >= 0 && landMines[row][col - 1]) {
                    count++;
                }
                if (col + 1 < 10 && landMines[row][col + 1]) {
                    count++;
                }
                if (row + 1 < 8 && col - 1 >= 0 && landMines[row + 1][col - 1]) {
                    count++;
                }
                if (row + 1 < 8 && landMines[row + 1][col]) {
                    count++;
                }
                if (row + 1 < 8 && col + 1 < 10 && landMines[row + 1][col + 1]) {
                    count++;
                }
                landMineCounts[row][col] = count;
            }
        }
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        if (board[row][col] != "□") {
            return;
        }
        if (landMines[row][col]) {
            return;
        }
        if (landMineCounts[row][col] != 0) {
            board[row][col] = String.valueOf(landMineCounts[row][col]);
            return;
        } else {
            board[row][col] = "■";
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

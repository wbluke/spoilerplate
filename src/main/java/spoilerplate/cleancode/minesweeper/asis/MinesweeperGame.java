package spoilerplate.cleancode.minesweeper.asis;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    private static String[][] board = new String[8][10];
    private static Integer[][] mineCounts = new Integer[8][10];
    private static boolean[][] mines = new boolean[8][10];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Scanner scanner = new Scanner(System.in);

        // 게임판 초기화
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = "□";
            }
        }
        // 지뢰 배치
        for (int i = 0; i < 10; i++) {
            int width = new Random().nextInt(10);
            int height = new Random().nextInt(8);
            mines[height][width] = true;
        }
        // 카운트 초기화
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                int count = 0;
                if (mines[i][j]) {
                    mineCounts[i][j] = -1;
                    continue;
                }
                if (i - 1 >= 0 && j - 1 >= 0 && mines[i - 1][j - 1]) {
                    count++;
                }
                if (i - 1 >= 0 && mines[i - 1][j]) {
                    count++;
                }
                if (i - 1 >= 0 && j + 1 < 10 && mines[i - 1][j + 1]) {
                    count++;
                }
                if (j - 1 >= 0 && mines[i][j - 1]) {
                    count++;
                }
                if (j + 1 < 10 && mines[i][j + 1]) {
                    count++;
                }
                if (i + 1 < 8 && j - 1 >= 0 && mines[i + 1][j - 1]) {
                    count++;
                }
                if (i + 1 < 8 && mines[i + 1][j]) {
                    count++;
                }
                if (i + 1 < 8 && j + 1 < 10 && mines[i + 1][j + 1]) {
                    count++;
                }
                mineCounts[i][j] = count;
            }
        }

        while (true) {
            System.out.println("   A B C D E F G H I J");
            for (int i = 0; i < 8; i++) {
                System.out.printf("%d  ", i + 1);
                for (int j = 0; j < 10; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }

            if (gameStatus == -1) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
//                break;
            }

            System.out.println();
            System.out.println("오픈할 좌표를 입력하세요. (예: a1)");
            String input = scanner.nextLine();
            System.out.println(input + "을(를) 선택하셨습니다.");

            char c = input.charAt(0);
            char r = input.charAt(1);

            int col;
            switch (c) {
                case 'a':
                    col = 0;
                    break;
                case 'b':
                    col = 1;
                    break;
                case 'c':
                    col = 2;
                    break;
                case 'd':
                    col = 3;
                    break;
                case 'e':
                    col = 4;
                    break;
                case 'f':
                    col = 5;
                    break;
                case 'g':
                    col = 6;
                    break;
                case 'h':
                    col = 7;
                    break;
                case 'i':
                    col = 8;
                    break;
                case 'j':
                    col = 9;
                    break;
                default:
                    col = -1;
                    break;
            }

            int row = Character.getNumericValue(r) - 1;

            if (mines[row][col]) {
                board[row][col] = "☼";
                gameStatus = -1;
            } else {
                if (mineCounts[row][col] != 0) {
                    board[row][col] = String.valueOf(mineCounts[row][col]);
                } else {
                    board[row][col] = "■";

                    // 재귀


                }

            }

            boolean isAllOpened = true;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 10; j++) {
                    if (board[i][j] == "□") {
                        isAllOpened = false;
                    }
                }
            }
            if (isAllOpened) {
                System.out.println("모든 지뢰를 찾았습니다. GAME CLEAR!");
                gameStatus = 1;
                break;
            }

        }

    }

}

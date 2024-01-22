package spoilerplate.cleancode.minesweeper.asis;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    private static char[][] board = new char[8][10];
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
                board[i][j] = '□';
            }
        }

        // 지뢰 배치
        for (int i = 0; i < 10; i++) {
            int width = new Random().nextInt(10);
            int height = new Random().nextInt(8);

            mines[height][width] = true;
        }

        while (gameStatus == 0) {
            // 게임판 출력
            System.out.println("   A B C D E F G H I J");
            for (int i = 0; i < 8; i++) {
                System.out.printf("%d  ", i + 1);
                for (int j = 0; j < 10; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("선택할 좌표를 입력하세요. (예: a1)");
            String input = scanner.nextLine();
            System.out.println(input + "을(를) 선택하셨습니다.");

        }

    }

}

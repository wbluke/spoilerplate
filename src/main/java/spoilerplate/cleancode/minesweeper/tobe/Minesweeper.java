package spoilerplate.cleancode.minesweeper.tobe;

import java.util.Random;

public class Minesweeper {

    // 보드와 지뢰는 각각 따로 있는 상태 -> 리팩토링 : 보드와 지뢰를 하나의 클래스로 통합
    // 고정 길이 배열의 형태 -> 요구사항 : 게임 난이도에 따라 길이 변경하도록 리팩토링

    private static final int WIDTH = 10;
    private static final int HEIGHT = 8;
    private static final int MINES = 10;
    private char[][] board;
    private boolean[][] mines;

    public Minesweeper() {
        board = new char[HEIGHT][WIDTH];
        mines = new boolean[HEIGHT][WIDTH];
        initializeBoard();
        placeMines();
    }

    public void run() {
        System.out.print("   ");
        for (int i = 0; i < WIDTH; i++) {
            System.out.print((char)('A' + i) + " ");
        }
        System.out.println();

        for (int i = 0; i < HEIGHT; i++) {
            System.out.printf("%d  ", i + 1);
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = '□';
            }
        }

        board[1][1] = '☼';
        board[2][2] = '■';
    }

    private void placeMines() {
        Random rand = new Random();
        int minesPlaced = 0;
        while (minesPlaced < MINES) {
            int row = rand.nextInt(HEIGHT);
            int col = rand.nextInt(WIDTH);
            if (!mines[row][col]) {
                mines[row][col] = true;
                minesPlaced++;
            }
        }
    }

}

package spoilerplate.cleancode.minesweeper.tobe.io;

import java.util.Scanner;

public class ConsoleInputHandler implements MinesweeperInputHandler {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }

}

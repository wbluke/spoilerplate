package spoilerplate.cleancode.minesweeper.tobe.io;

import java.util.Scanner;

public class ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    public String getUserInput() {
        return scanner.nextLine();
    }

}

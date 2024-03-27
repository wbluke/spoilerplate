package spoilerplate.cleancode.minesweeper.tobe.minesweeper.io;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.BoardIndexConverter;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.user.UserAction;

import java.util.Scanner;

public class ConsoleInputHandler implements MinesweeperInputHandler {

    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public UserAction getUserActionFromUser() {
        String userInput = scanner.nextLine();

        if ("1".equals(userInput)) {
            return UserAction.OPEN;
        }
        if ("2".equals(userInput)) {
            return UserAction.FLAG;
        }
        return UserAction.UNKNOWN;
    }

    @Override
    public CellPosition getCellPositionFromUser() {
        String userInput = scanner.nextLine();

        int selectedRowIndex = boardIndexConverter.convertRowIndex(userInput);
        int selectedColIndex = boardIndexConverter.convertColIndex(userInput);
        return CellPosition.of(selectedRowIndex, selectedColIndex);
    }

}

package spoilerplate.cleancode.minesweeper.tobe;

import spoilerplate.cleancode.minesweeper.tobe.gamelevel.Beginner;
import spoilerplate.cleancode.minesweeper.tobe.gamelevel.GameLevel;
import spoilerplate.cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import spoilerplate.cleancode.minesweeper.tobe.io.MinesweeperInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.io.MinesweeperOutputHandler;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();
        MinesweeperInputHandler minesweeperInputHandler = new ConsoleInputHandler();
        MinesweeperOutputHandler minesweeperOutputHandler = new ConsoleOutputHandler();
        Minesweeper minesweeper = new Minesweeper(gameLevel, minesweeperInputHandler, minesweeperOutputHandler);

        minesweeper.initialize();
        minesweeper.run();
    }

}

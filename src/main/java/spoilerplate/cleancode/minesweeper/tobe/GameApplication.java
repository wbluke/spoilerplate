package spoilerplate.cleancode.minesweeper.tobe;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.config.GameConfig;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.gamelevel.Beginner;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.ConsoleInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.ConsoleOutputHandler;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.Minesweeper;

public class GameApplication {

    public static void main(String[] args) {
        GameConfig gameConfig = new GameConfig(
            new Beginner(),
            new ConsoleInputHandler(),
            new ConsoleOutputHandler()
        );

        Minesweeper minesweeper = new Minesweeper(gameConfig);

        minesweeper.initialize();
        minesweeper.run();
    }

}

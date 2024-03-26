package spoilerplate.cleancode.minesweeper.tobe;

import spoilerplate.cleancode.minesweeper.tobe.config.GameConfig;
import spoilerplate.cleancode.minesweeper.tobe.gamelevel.Beginner;
import spoilerplate.cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

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

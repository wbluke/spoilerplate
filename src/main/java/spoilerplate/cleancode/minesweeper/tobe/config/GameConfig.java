package spoilerplate.cleancode.minesweeper.tobe.config;

import spoilerplate.cleancode.minesweeper.tobe.gamelevel.GameLevel;
import spoilerplate.cleancode.minesweeper.tobe.io.MinesweeperInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.io.MinesweeperOutputHandler;

public class GameConfig {

    private final GameLevel gameLevel;
    private final MinesweeperInputHandler inputHandler;
    private final MinesweeperOutputHandler outputHandler;

    public GameConfig(GameLevel gameLevel, MinesweeperInputHandler inputHandler, MinesweeperOutputHandler outputHandler) {
        this.gameLevel = gameLevel;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    public MinesweeperInputHandler getInputHandler() {
        return inputHandler;
    }

    public MinesweeperOutputHandler getOutputHandler() {
        return outputHandler;
    }

}

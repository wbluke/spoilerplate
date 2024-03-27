package spoilerplate.cleancode.minesweeper.tobe.minesweeper.config;

import spoilerplate.cleancode.minesweeper.tobe.minesweeper.gamelevel.GameLevel;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.MinesweeperInputHandler;
import spoilerplate.cleancode.minesweeper.tobe.minesweeper.io.MinesweeperOutputHandler;

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

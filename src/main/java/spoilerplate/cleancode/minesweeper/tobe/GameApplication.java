package spoilerplate.cleancode.minesweeper.tobe;

import spoilerplate.cleancode.minesweeper.tobe.gamelevel.GameLevel;
import spoilerplate.cleancode.minesweeper.tobe.gamelevel.VeryBeginner;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new VeryBeginner();
        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.run();
    }

}

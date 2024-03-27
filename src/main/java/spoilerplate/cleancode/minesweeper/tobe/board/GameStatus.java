package spoilerplate.cleancode.minesweeper.tobe.board;

public enum GameStatus {

    IN_PROGRESS("게임 진행중"),
    WIN("승리"),
    LOSE("패배");

    private final String description;

    GameStatus(String description) {
        this.description = description;
    }

}

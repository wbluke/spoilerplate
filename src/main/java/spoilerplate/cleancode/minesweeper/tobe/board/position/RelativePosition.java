package spoilerplate.cleancode.minesweeper.tobe.board.position;

import java.util.List;
import java.util.Objects;

public class RelativePosition {

    public static final List<RelativePosition> SURROUNDED_POSITIONS = List.of(
        RelativePosition.of(-1, -1),
        RelativePosition.of(-1, 0),
        RelativePosition.of(-1, 1),
        RelativePosition.of(0, -1),
        RelativePosition.of(0, 1),
        RelativePosition.of(1, -1),
        RelativePosition.of(1, 0),
        RelativePosition.of(1, 1)
    );

    private final int dx;
    private final int dy;

    private RelativePosition(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static RelativePosition of(int dx, int dy) {
        return new RelativePosition(dx, dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelativePosition that = (RelativePosition) o;
        return dx == that.dx && dy == that.dy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy);
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int plusX(int x) {
        return x + dx;
    }

    public int plusY(int y) {
        return y + dy;
    }

}

package model.data;

public class RelativePosition {
    private final int mPosX;
    private final int mPosY;

    public RelativePosition(final int x, final int y) {
        this.mPosX = x;
        this.mPosY = y;
    }

    public int getX() {
        return this.mPosX;
    }

    public int getY() {
        return this.mPosY;
    }

    public RelativePosition getMovedPosition(Direction dir) {
        int x = this.mPosX;
        int y = this.mPosY;

        switch (dir) {
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            case UP:
                y++;
                break;
            case DOWN:
                y--;
        }
        return new RelativePosition(x, y);
    }

    @Override
    public String toString() {
        return "(" + this.mPosX + ", " + this.mPosY + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RelativePosition) {
            return this.getX() == ((RelativePosition) other).getX() && this.getY() == ((RelativePosition) other).getY();
        }
        return false;
    }

}

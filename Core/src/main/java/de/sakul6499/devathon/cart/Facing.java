package de.sakul6499.devathon.cart;

/**
 * Created by lukas on 06.11.16.
 */
public enum Facing {
    NORTH   (180, Coordinate.Z_NEGATIVE, Coordinate.Z_POSITIVE, Coordinate.X_NEGATIVE, Coordinate.X_POSITIVE, Coordinate.Y_POSITIVE, Coordinate.Y_NEGATIVE),
    EAST    (-90, Coordinate.X_POSITIVE, Coordinate.X_NEGATIVE, Coordinate.Z_NEGATIVE, Coordinate.Z_POSITIVE, Coordinate.Y_POSITIVE, Coordinate.Y_NEGATIVE),
    SOUTH   (0  , Coordinate.Z_POSITIVE, Coordinate.Z_NEGATIVE, Coordinate.X_POSITIVE, Coordinate.X_NEGATIVE, Coordinate.Y_POSITIVE, Coordinate.Y_NEGATIVE),
    WEST    (90 , Coordinate.X_NEGATIVE, Coordinate.X_POSITIVE, Coordinate.Z_POSITIVE, Coordinate.Z_NEGATIVE, Coordinate.Y_POSITIVE, Coordinate.Y_NEGATIVE);

    private final int yawVal;

    private final Coordinate forward;
    private final Coordinate backward;
    private final Coordinate left;
    private final Coordinate right;
    private final Coordinate up;
    private final Coordinate down;

    Facing(int yawVal, Coordinate forward, Coordinate backward, Coordinate left, Coordinate right, Coordinate up, Coordinate down) {
        this.yawVal = yawVal;

        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    public int getYawVal() {
        return yawVal;
    }

    public Coordinate getForward() {
        return forward;
    }

    public Coordinate getBackward() {
        return backward;
    }

    public Coordinate getLeft() {
        return left;
    }

    public Coordinate getRight() {
        return right;
    }

    public Coordinate getUp() {
        return up;
    }

    public Coordinate getDown() {
        return down;
    }
}

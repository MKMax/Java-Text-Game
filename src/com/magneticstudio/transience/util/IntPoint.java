package com.magneticstudio.transience.util;

/**
 * A class that represents a 2d point
 * in space.
 *
 * @author Max
 */
public class IntPoint {

    public int x; // The x value of this position.
    public int y; // The y value of this position.

    /**
     * Creates a new point object with
     * default x and y values.
     */
    public IntPoint() {
        x = 0;
        y = 0;
    }

    /**
     * Creates a new point object with
     * specified x and y values.
     * @param nx The x value.
     * @param ny The y value.
     */
    public IntPoint(int nx, int ny) {
        x = nx;
        y = ny;
    }

    /**
     * Checks whether another point object
     * is equivalent to this one.
     * @param p The other point.
     * @return True if they have the same values of x and y.
     */
    public boolean isEquivalentTo(IntPoint p) {
        return p.getX() == x && p.getY() == y;
    }

    /**
     * Gets the X value of this position.
     * @return X value of this position.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X value of this position.
     * @param x New X value of this position.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the Y value of this position.
     * @return Y value of this position.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y value of this position.
     * @param y New Y value of this position.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets a string representation of this object.
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
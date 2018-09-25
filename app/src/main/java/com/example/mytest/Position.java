package com.example.mytest;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    private Point point;
    private Size screenSize;

    public Position(Point point, Size screenSize) {
        this.point = point;
        this.screenSize = screenSize;
    }

    public Point getPoint() {
        return point;
    }

    public Size getScreenSize() {
        return screenSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Objects.equals(point, position.point)
                && Objects.equals(screenSize, position.screenSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, screenSize);
    }

    @Override
    public String toString() {
        return "Position{"
                + "point=" + point
                + ", screenSize=" + screenSize
                + '}';
    }

}

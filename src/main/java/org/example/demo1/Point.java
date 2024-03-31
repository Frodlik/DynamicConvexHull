package org.example.demo1;

import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.effect.DropShadow;

import static org.example.demo1.Constants.POINT_RADIUS;
import static org.example.demo1.Constants.ACTIVE_POINT_COLOR;
import static org.example.demo1.Constants.INACTIVE_POINT_COLOR;
import static org.example.demo1.Constants.EPS;

public class Point extends Circle implements Comparable<Point> {
    public final double x, y;
    private boolean setInactiveCounter;
    private static final DropShadow ACTIVE_SHADOW, INACTIVE_SHADOW;

    static {
        ACTIVE_SHADOW = new DropShadow();
        ACTIVE_SHADOW.setColor(ACTIVE_POINT_COLOR);

        INACTIVE_SHADOW = new DropShadow();
        INACTIVE_SHADOW.setColor(INACTIVE_POINT_COLOR);
    }

    public Point(double x, double y, double screenX, double screenY) {
        super(screenX, screenY, POINT_RADIUS);
        super.setOnMouseClicked(e -> {
            if (e.getSource() instanceof Point) {
                e.consume();
            }
        });

        this.x = x;
        this.y = y;

        super.setEffect(ACTIVE_SHADOW);
        super.setFill(ACTIVE_POINT_COLOR);
        resetSetInactiveCounter();
    }

    public void resetSetInactiveCounter() {
        setInactiveCounter = true;
    }

    public void setInactive() {
        if (!this.setInactiveCounter) {
            super.setFill(INACTIVE_POINT_COLOR);
            super.setEffect(INACTIVE_SHADOW);
        } else {
            setInactiveCounter = false;
        }
    }

    public void remove() {
        if (super.getParent() instanceof Pane) {
            ((Pane) super.getParent()).getChildren().remove(this);
        }
    }

    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + "}";
    }

    @Override
    public int compareTo(Point other) {
        final double xdif = x - other.x;
        final double ydif = y - other.y;
        if (xdif > EPS || xdif > -EPS && ydif > EPS) {
            return 1;
        }
        if (xdif < -EPS || ydif < -EPS) {
            return -1;
        }
        return 0;
    }
}

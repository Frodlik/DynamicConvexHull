package org.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public final class Constants {
    public static final double EPS = 1e-7;
    public static final double POINT_RADIUS = 5;

    public static final Color ACTIVE_POINT_COLOR = Color.GOLD;
    public static final Color INACTIVE_POINT_COLOR = Color.SPRINGGREEN;
    public static final Color EDGE_COLOR = Color.GOLD;

    public static final double WINDOW_WIDTH = 1024;
    public static final double WINDOW_HEIGHT = 768;
    public static final double INSERT_WINDOW_WIDTH = WINDOW_WIDTH / 7.5;
    public static final double INSERT_WINDOW_HEIGHT = WINDOW_HEIGHT / 7.5;

    public static final double SCROLL_SPEED = 1.05;

    public static final String FORM_STYLE = "-fx-background-color : #455158;";

    private Constants() {
    }
}
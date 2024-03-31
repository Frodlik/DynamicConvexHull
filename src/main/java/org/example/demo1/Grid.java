package org.example.demo1;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Tooltip;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

import static org.example.demo1.Constants.*;

public class Grid extends BorderPane {
    private Pane canvas;
    private ConvexHullManager convexHull;
    private Tooltip toolTip;
    private double dragOriginX, dragOriginY;
    private double originX = -WINDOW_WIDTH/2.0, originY = WINDOW_HEIGHT/2.0;
    private double ratio = 1;

    private double width, height;

    public Grid(double width, double height) {
        canvas = new Pane();

        this.width = width;
        this.height = height;
        canvas.setPrefSize(this.width, this.height);
        this.setCenter(canvas);

        toolTip = new Tooltip();
        convexHull = new ConvexHullManager();

        canvas.setOnMouseClicked(e -> {
            if (!e.isStillSincePress())
                return;
            if (e.getSource() instanceof Pane) {
                Pane canvas = (Pane)e.getSource();
                double x = originX + e.getX()/ratio;
                double y = originY - e.getY()/ratio;
                addPoint(x, y);
            }
        });

        canvas.setOnMouseMoved(e -> {
            double x = originX + e.getX()/ratio;
            double y = originY - e.getY()/ratio;
            toolTip.setText("{x: " + x + ", y: " + y + "}");
            toolTip.show((Node) e.getSource(), e.getScreenX() + 15, e.getScreenY() + 15);
        });
        canvas.setOnMouseExited(e -> {
            toolTip.hide();
        });

        canvas.setOnMousePressed(e -> {
            dragOriginX = e.getX();
            dragOriginY = e.getY();
        });
        canvas.setOnMouseDragged(e -> {
            double offsetX = e.getX() - dragOriginX;
            double offsetY = e.getY() - dragOriginY;
            originX -= offsetX/ratio;
            originY += offsetY/ratio;
            for (var child : canvas.getChildren()) {
                if (child instanceof Point) {
                    Point p = (Point)child;
                    p.setCenterX(p.getCenterX() + offsetX);
                    p.setCenterY(p.getCenterY() + offsetY);
                } else if (child instanceof Line) {
                    Line l = (Line)child;
                    l.setStartX(l.getStartX() + offsetX);
                    l.setStartY(l.getStartY() + offsetY);
                    l.setEndX(l.getEndX() + offsetX);
                    l.setEndY(l.getEndY() + offsetY);
                }
            }
            dragOriginX = e.getX();
            dragOriginY = e.getY();
        });

        canvas.setOnScroll(e -> {
            double centerX = e.getX();
            double centerY = e.getY();
            double speed = (e.getDeltaY() > 0 ? SCROLL_SPEED : 2-SCROLL_SPEED);
            for (var child : canvas.getChildren()) {
                if (child instanceof Point) {
                    Point p = (Point)child;
                    p.setCenterX(centerX + speed * (p.getCenterX() - centerX));
                    p.setCenterY(centerY + speed * (p.getCenterY() - centerY));
                } else if (child instanceof Line) {Line l = (Line)child;
                    l.setStartX(centerX + speed * (l.getStartX() - centerX));
                    l.setStartY(centerY + speed * (l.getStartY() - centerY));
                    l.setEndX(centerX + speed * (l.getEndX() - centerX));
                    l.setEndY(centerY + speed * (l.getEndY() - centerY));
                }
            }
            centerX = originX + centerX/ratio;
            centerY = originY - centerY/ratio;
            ratio *= speed;
            originX = centerX + (originX - centerX)/speed;
            originY = centerY + (originY - centerY)/speed;
        });
    }

    public void addPoint(double x, double y) {
        double canvasX = (x - originX) * ratio;
        double canvasY = (originY - y) * ratio;
        Point newPoint = new Point(x, y, canvasX, canvasY);
        canvas.getChildren().add(newPoint);
        convexHull.add(newPoint);

        canvas.getChildren().removeIf(c -> c instanceof Line);
        var hull = convexHull.getConvexHull();
        System.out.println("New point added at " + newPoint.toString());
        System.out.println("Convex hull:");
        for (int i = 0; i + 1 < hull.size(); ++i) {
            System.out.print(hull.get(i).toString() + " ");
        }
        System.out.println();
        for (int i = 0; i + 1 < hull.size(); ++i) {
            Line line = new Line();
            line.setStartX(hull.get(i).getCenterX());
            line.setStartY(hull.get(i).getCenterY());
            line.setEndX(hull.get(i+1).getCenterX());
            line.setEndY(hull.get(i+1).getCenterY());
            line.setStroke(EDGE_COLOR);
            line.setStrokeWidth(hull.get(i).getRadius() / 2.0);
            line.setOnMouseClicked(t -> {
                if (t.getSource() instanceof Line) {
                    t.consume();
                }
            });
            canvas.getChildren().add(line);
            line.toBack();  // fix edges drawing over points
        }
    }
}
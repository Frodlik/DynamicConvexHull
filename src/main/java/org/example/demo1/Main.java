package org.example.demo1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.stage.StageStyle;

import static org.example.demo1.Constants.WINDOW_WIDTH;
import static org.example.demo1.Constants.WINDOW_HEIGHT;
import static org.example.demo1.Constants.INSERT_WINDOW_WIDTH;
import static org.example.demo1.Constants.INSERT_WINDOW_HEIGHT;
import static org.example.demo1.Constants.FORM_STYLE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("Dynamic Convex Hull");

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        Grid main = new Grid(WINDOW_WIDTH, WINDOW_HEIGHT);
        main.setStyle(FORM_STYLE);
        root.getChildren().add(main);

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() != KeyCode.F1) {
                return;
            }

            Stage insertStage = new Stage();
            insertStage.setAlwaysOnTop(true);
            insertStage.setResizable(false);

            Group insertRoot = new Group();
            //insertStage.setTitle("Insert point");
            insertStage.setScene(new Scene(insertRoot, INSERT_WINDOW_WIDTH, INSERT_WINDOW_HEIGHT));
            insertStage.show();

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(5);
            grid.setHgap(5);

            final TextField[] field = new TextField[2];
            final Button submit = new Button("Submit");
            submit.setPrefSize(115, 20);
            submit.setDisable(true);
            GridPane.setConstraints(submit, 0, 2);
            grid.getChildren().add(submit);
            submit.setOnMouseClicked(submitClickedEvent -> {
                double x = Double.parseDouble(field[0].getText());
                double y = Double.parseDouble(field[1].getText());
                insertStage.close();
                main.addPoint(x, y);
            });
            for (int i = 0; i < 2; ++i) {
                field[i] = new TextField();
                field[i].setPrefColumnCount(9);
                field[i].textProperty().addListener((observable, oldValue, value) -> {  // awful, just awful :|
                    try {
                        Double.parseDouble(field[0].getText());
                        Double.parseDouble(field[1].getText());
                        submit.setDisable(false);
                    } catch (NumberFormatException numberException) {
                        submit.setDisable(true);
                    }
                });
                GridPane.setConstraints(field[i], 0, i);
                grid.getChildren().add(field[i]);
            }
            insertRoot.getChildren().add(grid);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

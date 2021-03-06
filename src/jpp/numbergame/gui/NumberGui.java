package jpp.numbergame.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import jpp.numbergame.*;

import java.util.EventListener;
import java.util.List;
import java.util.Optional;

public class NumberGui extends Application implements EventListener {

    NumberGame numberGame = new NumberGame(4, 4, 2);

    private static final Color COLOR_Empty = Color.ALICEBLUE;
    private static final Color COLOR_2 = Color.AQUA;
    public static final Color COLOR_4 = Color.rgb(237, 224, 200);
    public static final Color COLOR_8 = Color.rgb(242, 177, 121);
    public static final Color COLOR_16 = Color.rgb(245, 149, 99);
    public static final Color COLOR_32 = Color.rgb(246, 124, 95);
    public static final Color COLOR_64 = Color.rgb(246, 94, 59);
    public static final Color COLOR_128 = Color.rgb(237, 207, 114);
    public static final Color COLOR_256 = Color.rgb(237, 204, 97);
    public static final Color COLOR_512 = Color.rgb(237, 200, 80);
    public static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    public static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    public static final Color COLOR_OTHER = Color.BLACK;
    public static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
    public static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
    public static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);

    private GridPane gridpane;
    private Scene scene;
    StackPane[][] tileArray = new StackPane[4][4];

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void startGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ButtonType buttonTypePlay = new ButtonType("PLAY");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypePlay, buttonTypeCancel);

        Optional <ButtonType> result = alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {

        gridpane = new GridPane();
        gridpane.setAlignment(Pos.CENTER);
        gridpane.setGridLinesVisible(true);

        gridpane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));

        BorderPane componentLayout = new BorderPane();
        componentLayout.setMaxSize(50.0, 50.0);
        componentLayout.setMinSize(50.0, 50.0);

        gridpane.setAlignment(Pos.CENTER);
        gridpane.setHgap(1);
        gridpane.setVgap(1);

        for (int i = 0; i < 4; i++) {
            gridpane.getColumnConstraints().add(0, new ColumnConstraints(200));
            gridpane.getRowConstraints().add(0, new RowConstraints(200));
        }

        Label points = new Label("POINTS: " + numberGame.getPoints());
        points.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 110));
        points.setTextAlignment(TextAlignment.RIGHT);
        points.setPadding(new Insets(20));
        componentLayout.setBottom(points);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int temp = numberGame.get(i, j);
                if (temp == 0) {
                    continue;
                }

                StackPane pane1 = new StackPane();
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(200);
                rectangle.setHeight(200);
                rectangle.setFill(numberColor(temp));
                pane1.getChildren().add(rectangle);
                Label label = new Label(Integer.toString(temp));
                label.setFont(Font.font(50));
                pane1.getChildren().add(label);
                pane1.setVisible(true);
                gridpane.add(pane1, i, j);
                tileArray[i][j] = pane1;
            }
        }


        componentLayout.setCenter(gridpane);

        scene = new Scene(componentLayout, 400, 400);
        primaryStage.setTitle("2048");

        scene.setOnKeyPressed(event -> {
            KeyCode keyPressed = event.getCode();
            Tile random;
            StackPane pane1;
            Rectangle rectangle;
            Label label;

            switch (keyPressed) {
                case LEFT:
                    if (numberGame.canMove(Direction.LEFT)) {
                        moveOnGui(numberGame.move(Direction.LEFT));

                    }
                    break;

                case RIGHT:
                    if (numberGame.canMove(Direction.RIGHT)) {
                        moveOnGui(numberGame.move(Direction.RIGHT));
                    }
                    break;

                case DOWN:
                    if (numberGame.canMove(Direction.DOWN)) {
                        moveOnGui(numberGame.move(Direction.DOWN));
                    }
                    break;

                case UP:
                    if (numberGame.canMove(Direction.UP)) {
                        moveOnGui(numberGame.move(Direction.UP));

                    }
                    break;

            }
            points.setText("Points: " + numberGame.getPoints());
            if (!numberGame.isFull()) {
                random = numberGame.addRandomTile();
                pane1 = new StackPane();
                rectangle = new Rectangle();
                rectangle.setWidth(200);
                rectangle.setHeight(200);
                rectangle.setFill(numberColor(random.getValue()));
                pane1.getChildren().add(rectangle);
                label = new Label(Integer.toString(random.getValue()));
                label.setFont(Font.font(80));
                pane1.getChildren().add(label);
                pane1.setVisible(true);
                gridpane.add(pane1, random.getCoordinate().getX(), random.getCoordinate().getY());
                tileArray[random.getCoordinate().getX()][random.getCoordinate().getY()] = pane1;
            }
            if (!numberGame.canMove()) {
                gameOver();
            }

        });
        startGame();
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public Color numberColor(int numberColor) {
        switch (numberColor) {
            case 2 -> {
                return COLOR_2;
            }
            case 4 -> {
                return COLOR_4;
            }
            case 8 -> {
                return COLOR_8;
            }
            case 16 -> {
                return COLOR_16;
            }
            case 32 -> {
                return COLOR_32;
            }
            case 64 -> {
                return COLOR_64;
            }
            case 128 -> {
                return COLOR_128;
            }
            case 264 -> {
                return COLOR_256;
            }
            case 512 -> {
                return COLOR_512;
            }
            case 1024 -> {
                return COLOR_1024;
            }
            case 2048 -> {
                return COLOR_2048;
            }
            default -> {
                return COLOR_OTHER;
            }
        }
    }

    public void moveOnGui(List <Move> moves) {
        //pane.getChildren().remove(tileArray);
        //tileArray = new StackPane[4][4];
        for (Move move : moves) {
            final Coordinate2D to = move.getTo();
            final Coordinate2D from = move.getFrom();

            gridpane.getChildren().remove(tileArray[from.getX()][from.getY()]);
            tileArray[from.getX()][from.getY()] = null;


            if (move.isMerge()) {
                gridpane.getChildren().remove(tileArray[to.getX()][to.getY()]);
                tileArray[to.getX()][to.getY()] = null;
            }
            StackPane stackPane = new StackPane();

            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(200);
            rectangle.setHeight(200);
            rectangle.setFill(numberColor(move.getNewValue()));
            stackPane.getChildren().add(rectangle);
            Label label = new Label(Integer.toString(move.getNewValue()));
            label.setFont(Font.font(80));
            stackPane.getChildren().add(label);
            stackPane.setVisible(true);
            gridpane.add(stackPane, to.getX(), to.getY());
            tileArray[to.getX()][to.getY()] = stackPane;


            if (!numberGame.canMove()) {
                gridpane.getChildren().addAll(stackPane);
                Label Game_Over = new Label();
                Game_Over.setFont(Font.font(75));
                Game_Over.setFont(Font.font("GAME OVER"));
                Game_Over.setText("GAME OVER");
                Game_Over.setPadding(new Insets(50));
                break;
            }
        }
    }

    private void gameOver() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("Game Over");
        alert.setContentText("Game Over");
        alert.getAlertType();

        alert.showAndWait();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public NumberGui() {
        super();
    }
}

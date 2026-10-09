package snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @version 2.0
 */
public class Main extends Application {
    public final static int DELAY = 200;

    private final BorderPane root = new BorderPane();
    private Timeline timeline;
    private World world;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::keyHandler);

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.setResizable(false);

        newGame();
        stage.show();
    }

    /** Builds a fresh game: new world, view, UI and game loop. */
    private void newGame() {
        world = new World();

        Pane worldPane = new WorldView(world);
        worldPane.setOnMouseClicked(this::mouseHandler);

        Pane topBar = createUserInterface(world);

        root.setTop(topBar);
        root.setCenter(worldPane);

        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.millis(DELAY), e -> {
            if (world.isRunning() && world.getHead().isAliveProperty().get()) {
                world.getHead().step();
            } else {
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void keyHandler(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case R:
                newGame();
                break;
            case SPACE:
                if (!world.getHead().isAliveProperty().get()) {
                    break; // ignore when dead; use R to restart
                }
                if (world.isRunning()) {
                    timeline.pause();
                    world.setRunning(false);
                } else {
                    timeline.play();
                    world.setRunning(true);
                }
                break;
            case W:
                world.getHead().setDirection(Direction.NORTH);
                break;
            case A:
                world.getHead().setDirection(Direction.WEST);
                break;
            case S:
                world.getHead().setDirection(Direction.SOUTH);
                break;
            case D:
                world.getHead().setDirection(Direction.EAST);
                break;
            default:
                break;
        }
    }

    private void mouseHandler(MouseEvent mouseEvent) {
        int col = (int) (mouseEvent.getX() / WorldView.UNIT);
        int row = (int) (mouseEvent.getY() / WorldView.UNIT);
        if (col >= 0 && col < world.getWidth() && row >= 0 && row < world.getHeight()) {
            world.getFood().setLocation(col, row);
        }
    }

    private Pane createUserInterface(World world) {
        Label scoreText = new Label();
        Label statusText = new Label();

        scoreText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        statusText.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        scoreText.textProperty().bind(Bindings.concat("Score: ", world.getScoreProperty()));

        Runnable updateStatus = () -> {
            if (!world.getHead().isAliveProperty().get()) {
                statusText.setText("Game Over  -  press R to restart");
            } else if (world.isRunning()) {
                statusText.setText("Press 'space' to pause");
            } else {
                statusText.setText("Press 'space' to start   -   WASD to steer");
            }
        };
        updateStatus.run();
        world.getRunningProperty().addListener((o, ov, nv) -> updateStatus.run());
        world.getHead().isAliveProperty().addListener((o, ov, nv) -> updateStatus.run());

        HBox bar = new HBox(24, scoreText, statusText);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(10, 14, 10, 14));
        bar.setStyle("-fx-background-color: #1e7d5a;");
        return bar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
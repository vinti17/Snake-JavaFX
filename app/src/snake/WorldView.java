package snake;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * A JavaFX Pane that displays the snake game represented by the given world
 */
public class WorldView extends Pane {
  protected static final double UNIT = 20;

  public WorldView(World world) {
    // Checkerboard background.
    for (int x = 0; x < world.getWidth(); x++) {
      for (int y = 0; y < world.getHeight(); y++) {
        Rectangle cell = new Rectangle(x * UNIT, y * UNIT, UNIT, UNIT);
        cell.setFill(((x + y) % 2 == 0) ? Color.web("#3cbf86") : Color.web("#34ac77"));
        getChildren().add(cell);
      }
    }

    // Food: a red dot.
    Food food = world.getFood();
    Circle foodView = new Circle(UNIT / 2, UNIT / 2, UNIT / 2 - 2, Color.web("#e74c3c"));
    bindLocation(food, foodView);
    getChildren().add(foodView);

    // Snake: chunky blue body, lighter blue head on top.
    Head head = world.getHead();
    Circle headView = addMoverView(head, UNIT / 2, Color.web("#2980b9"));
    headView.setStroke(Color.web("#154360"));

    addMoverView(head.getBody(), UNIT / 2 - 1, Color.web("#1b4f72"));

    head.bodyProperty().addListener((obs, ov, nv) -> {
      Circle c = addMoverView(nv, UNIT / 2 - 1, Color.web("#1b4f72"));
      c.setStroke(Color.web("#154360"));
      headView.toFront(); // keep the head drawn above the body
    });

    setPrefSize(world.getWidth() * UNIT, world.getHeight() * UNIT);
  }

  private Circle addMoverView(Mover mover, double radius, Color color) {
    Circle circle = new Circle(UNIT / 2, UNIT / 2, radius, color);
    bindLocation(mover, circle);
    getChildren().add(circle);
    return circle;
  }

  private void bindLocation(Actor actor, Shape actorView) {
    actorView.translateXProperty().bind(actor.xPosProperty().multiply(UNIT));
    actorView.translateYProperty().bind(actor.yPosProperty().multiply(UNIT));
  }
}
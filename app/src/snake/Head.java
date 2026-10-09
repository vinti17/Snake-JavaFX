package snake;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.*;

/**
 * Represents the head part of the snake
 */
public class Head extends Mover {
  private Property<Segment> bodyProperty;
  private BooleanProperty isAlive = new SimpleBooleanProperty(true);
  private List<Segment> snakeBody = new LinkedList<>();

  public Head(Segment body, Direction direction, int xPos, int yPos, World world) {
    super(direction, xPos, yPos, world);
    this.bodyProperty = new SimpleObjectProperty<>(body);
    snakeBody.add(getBody()); // we add the tail to the snakeBody
  }

  /** Ignore a direct 180-degree reversal, which would run into the neck */
  @Override
  public void setDirection(Direction newDirection) {
    Direction current = getDirection();
    if (newDirection.getDX() == -current.getDX()
        && newDirection.getDY() == -current.getDY()) {
      return; // reversal ignored
    }
    super.setDirection(newDirection);
  }

  public Segment getBody() {
    return bodyProperty.getValue();
  }

  public Property<Segment> bodyProperty() {
    return bodyProperty;
  }

  public List<Segment> getSnakeBody() {
    return snakeBody;
  }

  public void step() {
    World world = getWorld();
    Food food = world.getFood();

    // TODO: implement a step in the game. Move the snake if possible, increasing
    // its length when it eats food, and setting properties accordingly.

    if (!canMove()) {
      isAlive.set(false);
      return;
    }

    int nextX = getXPos() + getDirection().getDX();
    int nextY = getYPos() + getDirection().getDY();
    boolean eating = (nextX == food.getXPos() && nextY == food.getYPos());

    // Self-collision look-ahead: stop BEFORE the head enters the body
    int lastIndex = snakeBody.size() - 1;
    for (int i = 0; i < snakeBody.size(); i++) {
      if (!eating && i == lastIndex) {
        continue;
      }
      Segment seg = snakeBody.get(i);
      if (seg.getXPos() == nextX && seg.getYPos() == nextY) {
        isAlive.set(false);
        return; // freeze, head stays outside the body
      }
    }

    if (eating) {
      // Grow: put a new segment in the cell the head is leaving
      Segment oldFirst = getBody();
      BodySegment newSegment = new BodySegment(oldFirst, getDirection(), getXPos(), getYPos(), world);
      bodyProperty.setValue(newSegment); // notifies WorldView to draw it
      snakeBody.add(0, newSegment);
      move(); // head advances onto the food
      world.eatFood();
    } else {
      // Normal step: head advances, body follows it
      move();
      snakeBody.get(0).follow(getDirection());
    }
  }

  public BooleanProperty isAliveProperty() {
    return isAlive;
  }

}

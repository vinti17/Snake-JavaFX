package snake;

import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * World keeps track of the state of a game of snake
 */
public class World {
  private static final int WIDTH = 20, HEIGHT = 15;

  private final Food food;
  private final Head head;

  private final Random numberGenerator = new Random();

  private final BooleanProperty running = new SimpleBooleanProperty(false);
  private final IntegerProperty score = new SimpleIntegerProperty(0);

  public World() {
    head = randomSnake(); // create the snake first
    food = new Food(0, 0, this);
    placeFoodRandomly(); // then place food on a free cell
  }

  public void setRunning(boolean running) {
    this.running.set(running);
  }

  public void setScore(int score) {
    this.score.set(score);
  }

  public int getScore() {
    return score.get();
  }

  public boolean isRunning() {
    return running.get();
  }

  public Food getFood() {
    return food;
  }

  public Head getHead() {
    return head;
  }

  public int getWidth() {
    return WIDTH;
  }

  public int getHeight() {
    return HEIGHT;
  }

  public BooleanProperty getRunningProperty() {
    return running;
  }

  public IntegerProperty getScoreProperty() {
    return score;
  }

  private Head randomSnake() {
    Direction randomDir = Direction.values()[numberGenerator.nextInt(Direction.values().length)];
    int ranX = numberGenerator.nextInt(WIDTH - 4) + 2;
    int ranY = numberGenerator.nextInt(HEIGHT - 4) + 2;
    Segment tail = new TailSegment(randomDir, ranX, ranY, this);
    return new Head(tail, randomDir, ranX + randomDir.getDX(), ranY + randomDir.getDY(), this);
  }

  public void eatFood() {
    placeFoodRandomly();
    setScore(getScore() + 1);
  }

  /** Places the food on a random cell that the snake does not occupy. */
  private void placeFoodRandomly() {
    int x, y;
    boolean onSnake;
    do {
      x = numberGenerator.nextInt(WIDTH);
      y = numberGenerator.nextInt(HEIGHT);
      onSnake = (head.getXPos() == x && head.getYPos() == y);
      if (!onSnake) {
        for (Segment s : head.getSnakeBody()) {
          if (s.getXPos() == x && s.getYPos() == y) {
            onSnake = true;
            break;
          }
        }
      }
    } while (onSnake);
    food.setLocation(x, y);
  }
}
package snake;

/**
 * Represents a segment of a snake
 */
public abstract class Segment extends Mover {
  public Segment(Direction direction, int xPos, int yPos, World world) {
    super(direction, xPos, yPos, world);
  }

  public abstract void follow(Direction newDirection);
}

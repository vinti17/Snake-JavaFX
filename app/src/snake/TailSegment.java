package snake;

/**
 * Represents the tail part of a snake
 */
public class TailSegment extends Segment {
  public TailSegment(Direction direction, int xPos, int yPos, World world) {
    super(direction, xPos, yPos, world);
  }

  @Override
  public void follow(Direction newDirection) {
    move();
    setDirection(newDirection);
  }
}

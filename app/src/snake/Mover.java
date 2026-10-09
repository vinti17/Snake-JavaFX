package snake;

/**
 *
 * Represents a game element that can move, i.e., the parts of the snake
 */
public abstract class Mover extends Actor {
  private Direction direction;

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }

  public Mover(Direction direction, int xPos, int yPos, World world) {
    super(xPos, yPos, world);
    this.direction = direction;
  }

  public void move() {
    // TODO: implement movement using Actor.setLocation and the current direction.
    int newX = getXPos() + direction.getDX();
    int newY = getYPos() + direction.getDY();

    setLocation(newX, newY);
    // End
  }

  private boolean checkRange(int val, int minVal, int maxVal) {
    return val >= minVal && val < maxVal;
  }

  public boolean canMove() {
    // TODO: implement a check if the Actor object can move given the boundaries of
    // the world, which can be retrieved by getWorld().getWidth() for the width.
    // Use Mover.checkRange (above) and the current x and y position and direction.
    int newX = getXPos() + direction.getDX();
    int newY = getYPos() + direction.getDY();
    int WIDTH = getWorld().getWidth();
    int HEIGHT = getWorld().getHeight();

    if (!(checkRange(newX, 0, WIDTH) && checkRange(newY, 0, HEIGHT)))
      return false;
    return true;
    // End
  }

}

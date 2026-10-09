package snake;

public enum Direction {
  SOUTH(0, 1), EAST(1, 0), NORTH(0, -1), WEST(-1, 0);

  private final int dX, dY;

  private Direction(int dx, int dy) {
    dX = dx;
    dY = dy;
  }

  public int getDX() {
    return dX;
  }

  public int getDY() {
    return dY;
  }
}

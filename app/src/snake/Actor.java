package snake;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * Represent an actor (food/snake) in the game
 */
public abstract class Actor {

  private final IntegerProperty xPosProperty;
  private final IntegerProperty yPosProperty;
  private final World world;

  public Actor(int xPos, int yPos, World world) {
    this.xPosProperty = new SimpleIntegerProperty(xPos);
    this.yPosProperty = new SimpleIntegerProperty(yPos);
    this.world = world;
  }

  public int getXPos() {
    return xPosProperty.intValue();
  }

  public int getYPos() {
    return yPosProperty.intValue();
  }

  public World getWorld() {
    return world;
  }

  public void setLocation(int xPos, int yPos) {
    xPosProperty.set(xPos);
    yPosProperty.set(yPos);
  }

  public boolean sameLocation(Actor other) {
    return getXPos() == other.getXPos() && getYPos() == other.getYPos();
  }

  public IntegerProperty xPosProperty() {
    return xPosProperty;
  }

  public IntegerProperty yPosProperty() {
    return yPosProperty;
  }
}

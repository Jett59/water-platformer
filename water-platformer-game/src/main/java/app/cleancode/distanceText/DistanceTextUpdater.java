package app.cleancode.distanceText;

import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.events.TextChangeEvent;

@AttachedTo("distanceText")
public class DistanceTextUpdater extends GameListener {

  @ImportGameObject
  public GameObject<?> distanceText;

  @ImportGameObject
  public GameObject<?> water;

  @ImportGameObject
  public GameObject<?> player;

  private double initialPlayerYDelta;

  @Override
  public void update(State state) {
    distanceText.handleEvent(
        new TextChangeEvent("Distance: %.3f".formatted(Math.abs(player.getY() - water.getY()))));
    distanceText.move(distanceText.getX(), initialPlayerYDelta + player.getY());
  }

  @Override
  public void startup(State state) {
    initialPlayerYDelta = distanceText.getY() - player.getY();
  }

}

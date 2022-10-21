package app.cleancode.player;

import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.GameProperty;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.annotations.ImportGameProperty;

@AttachedTo("player")
public class PlayerPowerUpCollector extends GameListener {
  private static double HELMET_MASS_MULTIPLIER = 0.5;

  @ImportGameObject
  public GameObject<?> player;

  @ImportGameProperty(owner = "player")
  public GameProperty isWearingJacket;

  private GameObject<?> helmet;
  private GameObject<?> jacket;

  @Override
  public void update(State state) {
    if (helmet != null) {
      if (!helmet.getProperty("equipped").getBoolean()) {
        state.destroyGameObject(helmet);
        player.detachObject(helmet);
        helmet = null;
        player.mass *= 1d / HELMET_MASS_MULTIPLIER; // Reverses the multiplication.
      }
    }
    if (jacket != null) {
      if (!jacket.getProperty("equipped").getBoolean()) {
        state.destroyGameObject(jacket);
        player.detachObject(jacket);
        jacket = null;
        isWearingJacket.set(false);
      }
    }
  }

  @Override
  public void startup(State state) {

  }

  @Override
  public void onCollision(Collidable other, Bound collisionBound) {
    if (helmet == null && other.toString().equals("helmet")) {
      helmet = (GameObject<?>) other;
      helmet.collidable = false;
      helmet.getProperty("equipped").set(true);
      player.mass *= HELMET_MASS_MULTIPLIER;
      player.attachObject(helmet);
    } else if (jacket == null && other.toString().equals("jacket")) {
      jacket = (GameObject<?>) other;
      jacket.collidable = false;
      jacket.getProperty("equipped").set(true);
      isWearingJacket.set(true);
      player.attachObject(jacket);
    }
  }

}

package app.cleancode.player;

import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;

@AttachedTo("player")
public class PlayerPowerUpCollector extends GameListener {

  @ImportGameObject
  public GameObject<?> player;

  private GameObject<?> helmet;
  private GameObject<?> jacket;

  @Override
  public void update(State state) {
    if (helmet != null) {
      if (!helmet.getProperty("equipped").getBoolean()) {
        state.destroyGameObject(helmet);
        helmet = null;
      }
      helmet.move(player.getX(), player.getY());
    }
    if (jacket != null) {
      if (!jacket.getProperty("equipped").getBoolean()) {
        state.destroyGameObject(jacket);
        jacket = null;
      }
      jacket.move(player.getX(), player.getY());
    }
  }

  @Override
  public void startup(State state) {

  }

  @Override
  public void onCollision(Collidable other, Bound collisionBound) {
    if (helmet == null && other.toString().equals("helmet")) {
      helmet = (GameObject<?>) other;
      helmet.getProperty("equipped").set(true);
    } else if (jacket == null && other.toString().equals("jacket")) {
      jacket = (GameObject<?>) other;
      jacket.getProperty("equipped").set(true);
    }
  }

}
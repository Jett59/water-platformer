package app.cleancode.water;

import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;

@AttachedTo("water")
public class WaterMover extends GameListener {

  @ImportGameObject
  public GameObject<?> water;

  @Override
  public void update(State state) {
    water.yVelocity = Math.min(-0.05, Math.max(water.getY() / 40, -0.2));
  }

  @Override
  public void startup(State state) {
    // TODO Auto-generated method stub
  }

}

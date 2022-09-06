package app.cleancode.player;

import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.keyboard.Key;

@AttachedTo("player")
public class PlayerMovement extends GameListener {
  public static double SPEED = 0.4;

  @ImportGameObject
  public GameObject<?> player;
  
  @Override
  public void update(State state) {
    if (state.keyState.isKeyDown(Key.LEFT)) {
      player.xVelocity = -SPEED;
    }else if (state.keyState.isKeyDown(Key.RIGHT)) {
      player.xVelocity = SPEED;
    }
  }

  @Override
  public void startup(State state) {
    // TODO Auto-generated method stub
    
  }

}

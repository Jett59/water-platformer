package app.cleancode.player;

import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.events.MovementEvent;
import app.cleancode.scaga.engine.keyboard.Key;

@AttachedTo("player")
public class PlayerMovement extends GameListener {
  public static double SPEED = 0.5;

  @ImportGameObject
  public GameObject<?> player;
  
  @Override
  public void update(State state) {
    if (state.keyState.isKeyDown(Key.LEFT)) {
      player.xVelocity = -SPEED;
      player.handleEvent(new MovementEvent(-1, SPEED));
    }else if (state.keyState.isKeyDown(Key.RIGHT)) {
      player.xVelocity = SPEED;
      player.handleEvent(new MovementEvent(1, SPEED));
    }
    Bound playerBound = player.getRegion().getTransformedBound();
    if (playerBound.getMaxX() < 0) {
      player.screenMove(state.getScreenWidth() + playerBound.getMaxX(), playerBound.getMinY());
    }if (playerBound.getMinX() > state.getScreenWidth()) {
      player.screenMove(playerBound.getMinX() - state.getScreenWidth(), playerBound.getMinY());
    }
  }

  @Override
  public void startup(State state) {
    // TODO Auto-generated method stub
    
  }

}

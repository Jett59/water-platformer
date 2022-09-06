package app.cleancode.player;

import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.annotations.ImportGameProperty;
import app.cleancode.scaga.engine.keyboard.Key;

@AttachedTo("player")
public class PlayerJump extends GameListener {
  private static final double JUMP_VELOCITY = 1;

  @ImportGameObject
  public GameObject<?> player;
  
  @Override
  public void update(State state) {
    if (state.keyState.isKeyDown(Key.SPACE) && player.isTouchingGround) {
      player.yVelocity = -JUMP_VELOCITY;
    }
  }

  @Override
  public void startup(State state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onCollision(Collidable other, Bound collisionBound) {
    if (other.toString().equals("doubleJump")) {
      player.yVelocity = -JUMP_VELOCITY;
    }
  }
  
}

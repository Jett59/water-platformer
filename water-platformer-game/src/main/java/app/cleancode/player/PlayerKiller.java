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
public class PlayerKiller extends GameListener {
  private static final long DEATH_DURATION = 1000000000;

  @ImportGameObject
  public GameObject<?> player;
  
  @ImportGameObject
  public GameObject<?> water;

  @ImportGameProperty(owner="player")
  public GameProperty isWearingJacket;

  @ImportGameProperty(owner="player")
  public GameProperty isDead;
  
  private boolean dead = false;
  private long deadTime = 0;
  
  @Override
  public void update(State state) {
    if (dead && (deadTime + DEATH_DURATION) <= System.nanoTime()) {
      water.move(0, 1.5);
      water.yVelocity = 0;
      player.move(0.5, 0.75);
      dead = false;
      // Set isDead to true for a frame to allow the game to rebuild itself.
      isDead.set(true);
    }else if (dead) {
      player.yVelocity = 0;
      player.xVelocity = 0;
    }else {
      // Not dead.
      isDead.set(false);
    }
  }

  @Override
  public void startup(State state) {
    
  }

  @Override
  public void onCollision(Collidable other, Bound collisionBound) {
    if (other.toString().equals("water") && !dead) {
      if (!isWearingJacket.getBoolean()) {
      dead = true;
      deadTime = System.nanoTime();
      }else {
        player.yVelocity = -PlayerJump.JUMP_VELOCITY;
      }
    }
  }
  
}

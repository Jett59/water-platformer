package app.cleancode.platform;

import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.ImportGameObject;

public class PlatformCreator extends GameListener {
private static double HELMET_CHANCE = 0.5;
private static double JACKET_CHANCE = 0.5;
  
  @ImportGameObject
  public GameObject<?> platform;

  @ImportGameObject
  public GameObject<?> doubleJump;

  @ImportGameObject
  public GameObject<?> helmet;

  @ImportGameObject
  public GameObject<?> jacket;

  @ImportGameObject
  public GameObject<?> player;

  private double lastPlatformHeight = 0.625;

  @Override
  public void update(State state) {
    while (player.getY() - 1 < lastPlatformHeight) {
      // Create the platforms for the next 1.5 screens worth.
      for (double heightOffset = 0; heightOffset < 1.5; heightOffset += 0.375) {
        double platformX = Math.random() * 0.8;
        double doubleJumpX = Math.random() * 0.93;
        state.createGameObject(platform, platformX, lastPlatformHeight - heightOffset);
        state.createGameObject(doubleJump, doubleJumpX, lastPlatformHeight - heightOffset - 0.1);
        if (Math.random() < HELMET_CHANCE) {
          state.createGameObject(helmet, platformX, lastPlatformHeight - heightOffset - 0.1);
        }else if (Math.random() < JACKET_CHANCE) {
          state.createGameObject(jacket, platformX, lastPlatformHeight - heightOffset - 0.1);
        }
      }
      lastPlatformHeight -= 1.5;
    }
  }

  @Override
  public void startup(State state) {
    state.destroyGameObject(platform);
    state.destroyGameObject(doubleJump);
    state.destroyGameObject(helmet);
    state.destroyGameObject(jacket);
  }

}

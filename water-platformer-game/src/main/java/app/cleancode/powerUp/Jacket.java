package app.cleancode.powerUp;

import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameProperty;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameProperty;

@AttachedTo("jacket")
public class Jacket extends GameListener {
  private static long EQUIP_DURATION = 30000000000l;

  @ImportGameProperty(owner = "jacket")
  public GameProperty equipped;

  private long equippedTime;

  @Override
  public void update(State state) {
    if (equipped.getBoolean() && equippedTime == 0) {
      equippedTime = System.nanoTime();
    } else if (equippedTime != 0 && equippedTime + EQUIP_DURATION < System.nanoTime()) {
      equipped.set(false);
      equippedTime = 0;
    }
  }

  @Override
  public void startup(State state) {

  }

}

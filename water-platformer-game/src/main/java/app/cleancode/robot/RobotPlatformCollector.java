package app.cleancode.robot;

import java.util.List;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.GameProperty;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.annotations.ImportGameProperty;

@AttachedTo("platform")
public class RobotPlatformCollector extends GameListener {

  @ImportGameObject
  public GameObject<?> platform;

  @ImportGameProperty(owner = "robot")
  public GameProperty platforms;

  @Override
  public void update(State state) {}

  @Override
  public void startup(State state) {
    List<GameObject<?>> platformsList = platforms.get();
    platformsList.add(platform);
  }

}

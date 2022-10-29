package app.cleancode.platform;

import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.GameProperty;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.annotations.ImportGameProperty;

@AttachedTo("platform")
public class PlatformPowerUpCreator extends GameListener {
  private static double HELMET_CHANCE = 0.5;
  private static double JACKET_CHANCE = 0.5;

  // We can't import the templates in the traditional way because they are destroyed before we get a
  // chance to run, but the PlatformCreator does have access to them so we define these global
  // variables for it to inject in for our use. Messy, but I can't think of anything better just
  // now.
  public static GameObject<?> helmetTemplate;
  public static GameObject<?> jacketTemplate;

  private boolean shouldHaveHelmet;
  private boolean shouldHaveJacket;

  @ImportGameProperty(owner = "player")
  public GameProperty isDead;

  @ImportGameObject
  public GameObject<?> platform;

  private GameObject<?> helmet;
  private GameObject<?> jacket;

  @Override
  public void update(State state) {
    if (helmet != null && isDead.getBoolean() && helmet.destroyed) {
      helmet = null; // Recreate it.
    }
    if (jacket != null && isDead.getBoolean() && jacket.destroyed) {
      jacket = null; // Recreate it.
    }
    double platformX = platform.getX();
    double platformY = platform.getY();
    if (helmet == null && shouldHaveHelmet) {
      helmet = state.createGameObject(helmetTemplate, platformX, platformY - 0.1);
    }
    if (jacket == null && shouldHaveJacket) {
      jacket = state.createGameObject(jacketTemplate, platformX, platformY - 0.1);
    }
  }

  @Override
  public void startup(State state) {
    if (Math.random() < HELMET_CHANCE) {
      shouldHaveHelmet = true;
    } else if (Math.random() < JACKET_CHANCE) {
      shouldHaveJacket = true;
    }
  }

}

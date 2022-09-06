package app.cleancode.camera;

import java.awt.Dimension;
import java.awt.Toolkit;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.ImportGameObject;

public class CameraMover extends GameListener {
  private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
private static final double CAMERA_Y_OFFSET = screenSize.height / 3 * 2;
  
  @ImportGameObject
  public GameObject<?> player;
  
  @Override
  public void update(State state) {
    // Adjust the camera y so it is following the player.
    double cameraY = state.getCameraY() + CAMERA_Y_OFFSET;
    double playerY = player.getScreenY();
    double newCameraY = (cameraY * 4 + playerY) / 5 - CAMERA_Y_OFFSET;
    state.setCameraY(newCameraY);
  }

  @Override
  public void startup(State state) {
    // TODO Auto-generated method stub
    
  }

}

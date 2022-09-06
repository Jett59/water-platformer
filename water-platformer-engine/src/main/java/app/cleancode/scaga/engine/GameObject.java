package app.cleancode.scaga.engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.engine.config.GameObjectConfig;
import app.cleancode.scaga.engine.events.CollisionEvent;
import app.cleancode.scaga.engine.events.Event;
import javafx.scene.Node;

public abstract class GameObject<NodeType extends Node> implements Collidable {
  private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  public NodeType node;
  public double mass = 0;
  public double drag = 0;
  public boolean isTouchingGround = false;
  public boolean collidable;
  public boolean solid;

  protected List<GameListener> attachedListeners = new ArrayList<>();

  public void attachListener(GameListener listener) {
    this.attachedListeners.add(listener);
  }

  protected Map<String, GameProperty> properties = new HashMap<>();

  public GameProperty getProperty(String property) {
    return properties.get(property);
  }

  public abstract String getName();

  public double xVelocity = 0;
  public double yVelocity = 0;
  public double zVelocity = 0;

  public void move(double newX, double newY) {
    node.setTranslateX(newX);
    node.setTranslateY(newY);
  }

  /**
   * Get the screen x position. This is the x coordinate in pixels.
   * 
   * @return the x coordinate in pixels
   */
  public double getScreenX() {
    return node.getTranslateX();
  }

  /**
   * Get the screen y position. This is the y coordinate in pixels.
   * 
   * @return the y coordinate in pixels
   */
  public double getScreenY() {
    return node.getTranslateY();
  }

  /**
   * Get the x position. This is scaled so every 1 is the width of the screen.
   * 
   * @return the x position
   */
  public double getX() {
    return getScreenX() / screenSize.width;
  }

  /**
   * Get the y position. This is scaled so every 1 is the height of the screen.
   * 
   * @return the y position
   */
  public double getY() {
    return getScreenY() / screenSize.height;
  }

  public abstract void init();

  public void handleEvent(Event evt) {
    if (evt instanceof CollisionEvent) {
      CollisionEvent collision = (CollisionEvent) evt;
      for (GameListener listener : attachedListeners) {
        listener.onCollision(collision.other, collision.collisionBound);
      }
    }
  }

  @Override
  public String toString() {
    return getName();
  }

  @SuppressWarnings("unchecked")
  protected GameObject<NodeType> duplicate(GameObjectLoader objectLoader,
      GameListenerLoader listenerLoader, State state, List<GameObject<?>> objects)
      throws Exception {
    GameObject<NodeType> result = (GameObject<NodeType>) objectLoader.loadGameObject(getName());
    var gameObjects = new ArrayList<>();
    gameObjects.add(result);
    gameObjects.addAll(objects);
    GameObject<?>[] gameObjectArray = gameObjects.toArray(new GameObject<?>[] {});
    for (GameListener listener : attachedListeners) {
      GameListener newListener = listener.getClass().getConstructor().newInstance();
      listenerLoader.prepareListener(newListener, gameObjectArray);
    }
    return result;
  }

  public GameObject(GameObjectConfig config) {
    this.collidable = config.getCollidable();
    this.solid = config.getSolid();
    for (String propertyName : config.getProperties()) {
      properties.put(propertyName, new GameProperty(this));
    }
  }

  public void setScale(double scale) {
    if (node != null) {
      node.setScaleX(scale);
      node.setScaleY(scale);
    }
  }

  public double getScale() {
    return node.getScaleX();
  }
}

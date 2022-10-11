package app.cleancode.scaga.shape.objects;

import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.config.GameObjectConfig;
import app.cleancode.scaga.engine.events.Event;
import app.cleancode.scaga.shape.Rectangle2D;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.Node;

public class RectangleGameObject extends GameObject<Rectangle2D> {
  private final double x, y, width, height;
  private final String name;

  public RectangleGameObject(GameObjectConfig config) {
    super(config);
    this.x = config.getX();
    this.y = config.getY();
    this.width = config.getWidth();
    this.height = config.getHeight();
    this.name = config.getName();

    super.mass = config.getMass();
    super.drag = config.getDrag();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void init() {
    node = new Rectangle2D(x, y, width, height);
    node.setId(name);
  }

  @Override
  public void handleEvent(Event evt) {
    super.handleEvent(evt);
  }

  @Override
  public double getScreenX() {
    return node.getX();
  }

  @Override
  public double getScreenY() {
    return node.getY();
  }

  @Override
  public void screenMove(double newX, double newY) {
    node.setX(newX);
    node.setY(newY);
  }

  @Override
  public Polygon2D getRegion() {
    return node.getRegion();
  }
}

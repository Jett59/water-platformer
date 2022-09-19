package app.cleancode.scaga.shape;

import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.shape.Rectangle;

public class Rectangle2D extends Rectangle implements Collidable {
  private Polygon2D region;

  public Rectangle2D(double x, double y, double width, double height) {
    super(x, y, width, height);
    region = new Polygon2D(0, 0, width, 0, width, height, 0, height);
    region.xProperty().bind(xProperty());
    region.yProperty().bind(yProperty());
  }

  public Polygon2D getRegion() {
    return region;
  }
}

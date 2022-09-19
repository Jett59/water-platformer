package app.cleancode.scaga.shape.polygon;

import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.colliders.PolygonCollider;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;

public class Polygon2D {
  private final Polygon internalPolygon;
  private Bound transformedBoundCache;
  private boolean isBoundCached = false;

  @SuppressWarnings("exports")
  public Polygon2D(Polygon polygon) {
    this.internalPolygon = polygon;
    internalPolygon.translateXProperty()
        .addListener((observable, oldValue, newValue) -> isBoundCached = false);
    internalPolygon.translateYProperty()
        .addListener((observable, oldValue, newValue) -> isBoundCached = false);
  }

  public Polygon2D(double... points) {
    this(new Polygon(points));
  }

  public double[] getPoints() {
    return internalPolygon.getPoints().stream().mapToDouble(Double::valueOf).toArray();
  }

  public Bound getTransformedBound() {
    if (!isBoundCached) {
      Bounds internalBounds = internalPolygon.getBoundsInParent();
      transformedBoundCache = new Bound(internalBounds.getMinX(), internalBounds.getMinY(),
          internalBounds.getWidth(), internalBounds.getHeight());
      isBoundCached = true;
    }
    return transformedBoundCache;
  }

  public boolean isEmpty() {
    return internalPolygon.getBoundsInLocal().isEmpty();
  }

  public boolean intersects(Polygon2D other) {
    return !PolygonCollider.intersect(this, other).getBoundsInLocal().isEmpty();
  }

  @SuppressWarnings("exports")
  public DoubleProperty xProperty() {
    return internalPolygon.translateXProperty();
  }

  @SuppressWarnings("exports")
  public DoubleProperty yProperty() {
    return internalPolygon.translateYProperty();
  }
}

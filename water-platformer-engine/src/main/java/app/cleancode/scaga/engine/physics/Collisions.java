package app.cleancode.scaga.engine.physics;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.colliders.PolygonCollider;
import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.collisions.Collision;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.geometry.Bounds;
import javafx.scene.shape.Shape;

public class Collisions {
  private List<Collidable> objects;

  public Collisions() {
    objects = new ArrayList<>();
  }

  public List<Collision> check(GameObject<?> obj) {
    List<Collision> collisions = new ArrayList<>();
    for (Collidable object : objects) {
      if (!object.equals(obj)) {
        Polygon2D objRegion = obj.getRegion();
        Polygon2D objectRegion = object.getRegion();
        Shape intersection = PolygonCollider.intersect(objRegion, objectRegion);
        if (!intersection.getBoundsInLocal().isEmpty()) {
          Bounds intersectionBounds = intersection.getBoundsInParent();
          Bound collisionBound =
              new Bound(intersectionBounds.getMinX(), intersectionBounds.getMinY(),
                  intersectionBounds.getWidth(), intersectionBounds.getHeight());
          collisions.add(new Collision(object, collisionBound));
        }
      }
    }
    return collisions;
  }

  public void registerObject(Collidable c) {
    if (!objects.contains(c)) {
      objects.add(c);
    }
  }

  public void removeCollidable(Collidable collidable) {
    objects.remove(collidable);
  }

}

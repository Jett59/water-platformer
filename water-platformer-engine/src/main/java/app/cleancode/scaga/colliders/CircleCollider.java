package app.cleancode.scaga.colliders;

import app.cleancode.scaga.bounds.Bound;

public class CircleCollider {
  public static boolean intersects(Bound a, Bound b) {
    double distanceBetweenMiddles = Math.sqrt(Math.pow(a.getCenterX() - b.getCenterX(), 2)
        + Math.pow(a.getCenterY() - b.getCenterY(), 2));
    return distanceBetweenMiddles <= a.getMaxRadius() + b.getMaxRadius();
  }
}

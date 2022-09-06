package app.cleancode.scaga.collisions;

import app.cleancode.scaga.shape.polygon.Polygon2D;

public interface Collidable {
    Polygon2D getRegion();
}

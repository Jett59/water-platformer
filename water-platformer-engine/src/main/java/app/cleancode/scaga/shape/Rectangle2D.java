package app.cleancode.scaga.shape;

import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.shape.Rectangle;

public class Rectangle2D extends Rectangle implements Collidable {
    public Rectangle2D(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Polygon2D getRegion() {
        return new Polygon2D(getX(), getY(), getX() + getWidth(), getY(), getX() + getWidth(),
                getY() + getHeight(), getX(), getY() + getHeight());
    }
}

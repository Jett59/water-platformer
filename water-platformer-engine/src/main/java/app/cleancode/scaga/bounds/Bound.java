package app.cleancode.scaga.bounds;

public class Bound {
  private final double minX, minY, width, height;
  private double maxRadius; // Maximum distance from edge to middle. Used in the circle collider.

  public Bound(double minX, double minY, double width, double height) {
    this.minX = minX;
    this.minY = minY;
    this.width = width;
    this.height = height;
    maxRadius = Math.sqrt(Math.pow(width / 2d, 2) + Math.pow(height / 2d, 2));
  }

  public double getMinX() {
    return minX;
  }

  public double getMinY() {
    return minY;
  }

  public double getCenterX() {
    return minX + width / 2d;
  }

  public double getCenterY() {
    return minY + height / 2d;
  }

  public double getMaxX() {
    return minX + width;
  }

  public double getMaxY() {
    return minY + height;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public boolean isEmpty() {
    return width <= 0 || height <= 0;
  }

  public double getMaxRadius() {
    return maxRadius;
  }
}

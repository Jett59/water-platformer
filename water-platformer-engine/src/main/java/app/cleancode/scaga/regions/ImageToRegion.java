package app.cleancode.scaga.regions;

import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class ImageToRegion {
  private static final int POLYGON_PRECISION = 45;

  public static Polygon2D getRegion(Image img) {
    var reader = img.getPixelReader();
    ObservableList<Double> polygonPoints = FXCollections.observableArrayList();
    for (double angle = 0; angle < 360; angle += 360 / POLYGON_PRECISION) {
      double rad = Math.toRadians(angle);
      Point2D point = null;
      double x = img.getWidth() / 2d, y = img.getHeight() / 2d;
      double distance = 0;
      do {
        if (reader.getColor((int) x, (int) y).getOpacity() != 0) {
          point = new Point2D(x, y);
        }

        distance++;
        x = Math.cos(rad) * distance + img.getWidth() / 2d;
        y = Math.sin(rad) * distance + img.getHeight() / 2d;
      } while (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight());
      if (point != null) {
        polygonPoints.add(point.getX());
        polygonPoints.add(point.getY());
      }
    }
    return new Polygon2D(polygonPoints.stream().mapToDouble(Double::doubleValue).toArray());
  }
}

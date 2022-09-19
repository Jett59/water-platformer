package app.cleancode.scaga.colliders;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PolygonCollider {
  public static Shape intersect(Polygon2D a, Polygon2D b) {
    if (CircleCollider.intersects(a.getTransformedBound(), b.getTransformedBound())) {
      return Shape.intersect(getInternalPolygon(a), getInternalPolygon(b));
    } else {
      return new Polygon();
    }
  }

  private static MethodHandle internalPolygonGetter;
  static {
    try {
      Field internalPolygonField = Polygon2D.class.getDeclaredField("internalPolygon");
      internalPolygonField.setAccessible(true);
      internalPolygonGetter = MethodHandles.lookup().unreflectGetter(internalPolygonField);
    } catch (NoSuchFieldException e) {
      System.err.println("Error: Polygon2D is not compatible with PolygonCollider");
      e.printStackTrace();
      System.exit(-1);
    } catch (SecurityException e) {
      System.err.println("Error: PolygonCollider was not allowed to access Polygon2D");
      e.printStackTrace();
      System.exit(-1);
    } catch (IllegalAccessException e) {
      System.err.println("Error: access is denied");
      e.printStackTrace();
      System.exit(-1);
    }
  }

  private static javafx.scene.shape.Polygon getInternalPolygon(Polygon2D p) {
    try {
      return (Polygon) internalPolygonGetter.invoke(p);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}

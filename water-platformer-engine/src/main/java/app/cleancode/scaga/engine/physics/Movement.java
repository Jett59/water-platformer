package app.cleancode.scaga.engine.physics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.collisions.Collision;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.PhysicalLaw;
import app.cleancode.scaga.engine.events.CollisionEvent;
import app.cleancode.scaga.engine.events.StopEvent;
import javafx.scene.Node;

public class Movement extends PhysicalLaw {
  private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  private final Map<GameObject<? extends Node>, Long> lastMovementTimes;
  private Collisions collider;

  public Movement() {
    lastMovementTimes = new HashMap<>();
    collider = new Collisions();
  }

  @Override
  public void handle(GameObject<?> obj) {
    if (obj.xVelocity != 0 || obj.yVelocity != 0) {
      if (lastMovementTimes.containsKey(obj)) {
        long lastMovementTime = lastMovementTimes.get(obj);
        long currentTime = System.nanoTime();
        long delta = currentTime - lastMovementTime;
        double xMoveAmount = delta / 1000000000d * obj.xVelocity * screenSize.width;
        double yMoveAmount = delta / 1000000000d * obj.yVelocity * screenSize.height;
        double origX = obj.getScreenX(), origY = obj.getScreenY();
        obj.screenMove(origX + xMoveAmount, origY + yMoveAmount);
        if (obj.collidable && obj.solid) {
          Collision intersection = collider.check(obj);
          Bound intersectionBounds = intersection.intersectionRegion;
          if (!intersectionBounds.isEmpty()) {
            for (int i = 0; i < 4
                && !(intersectionBounds = (intersection = collider.check(obj)).intersectionRegion)
                    .isEmpty(); i++) {
              if (isSolid(intersection.other)) {
              if (intersectionBounds.getWidth() > intersectionBounds.getHeight()) {
                if (yMoveAmount < 0) {
                  yMoveAmount += intersectionBounds.getHeight();
                } else if (yMoveAmount > 0) {
                  yMoveAmount -= intersectionBounds.getHeight();
                  obj.isTouchingGround = true;
                } else {
                  if (intersectionBounds.getCenterY() < obj.getRegion().getTransformedBound()
                      .getCenterY()) {
                    yMoveAmount = intersectionBounds.getHeight() * -1;
                    obj.isTouchingGround = true;
                  } else {
                    yMoveAmount = intersectionBounds.getHeight();
                  }
                }
                obj.yVelocity = 0;
              } else {
                if (xMoveAmount < 0) {
                  xMoveAmount += intersectionBounds.getWidth();
                } else if (xMoveAmount > 0) {
                  xMoveAmount -= intersectionBounds.getWidth();
                } else {
                  if (intersectionBounds.getCenterX() < obj.getRegion().getTransformedBound()
                      .getCenterX()) {
                    xMoveAmount = intersectionBounds.getWidth() * -1;
                  } else {
                    xMoveAmount = intersectionBounds.getWidth();
                  }
                }
                obj.xVelocity = 0;
                obj.handleEvent(new StopEvent());
              }
              obj.screenMove(origX + xMoveAmount, origY + yMoveAmount);
              }
            obj.handleEvent(new CollisionEvent(intersection.other, intersectionBounds));
            if (intersection.other instanceof GameObject<?>) {
              ((GameObject<?>) intersection.other)
                  .handleEvent(new CollisionEvent(obj, intersectionBounds));
            }
          }
          intersection = collider.check(obj);
          if (isSolid(intersection.other) && !intersection.intersectionRegion.isEmpty()) {
            obj.screenMove(origX, origY);
            }
          }
        }
      }
    }
    lastMovementTimes.put(obj,System.nanoTime());
    if (obj.collidable){
      collider.registerObject(obj);
    }
  }

  @Override
  public void destroyGameObject(GameObject<?> object) {
    lastMovementTimes.remove(object);
    if (object.collidable) {
      collider.removeCollidable(object);
    }
  }

  private boolean isSolid(Collidable collidable) {
    if (collidable instanceof GameObject) {
      return ((GameObject<?>) collidable).solid;
    } else {
      return true;
    }
  }

}

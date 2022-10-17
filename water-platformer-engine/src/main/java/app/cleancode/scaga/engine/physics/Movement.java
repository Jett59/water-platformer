package app.cleancode.scaga.engine.physics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
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
        double originalXMoveAmount = delta / 1000000000d * obj.xVelocity * screenSize.width;
        double originalYMoveAmount = delta / 1000000000d * obj.yVelocity * screenSize.height;
        double originalX = obj.getScreenX(), originalY = obj.getScreenY();
        obj.screenMove(originalX + originalXMoveAmount, originalY + originalYMoveAmount);
        if (obj.collidable && isSolid(obj)) {
          List<Collision> collisions = collider.check(obj);
          double xMoveAmount = originalXMoveAmount, yMoveAmount = originalYMoveAmount;
          Bound objBound = obj.getRegion().getTransformedBound();
          double objCenterX = objBound.getCenterX();
          double objCenterY = objBound.getCenterY();
          for (Collision collision : collisions) {
            Bound collisionBound = collision.intersectionRegion;
            if (isSolid(collision.other)) {
              double xMoveAmountChange = 0;
              double yMoveAmountChange = 0;
              double collisionCenterX = collisionBound.getCenterX();
              double collisionCenterY = collisionBound.getCenterY();
              // I can' think of a better way of figuring this out right now so this will have to do.
              boolean isXIntersection = collisionBound.getWidth() < collisionBound.getHeight();
              boolean isYIntersection = !isXIntersection;
              if (isXIntersection) {
                if (objCenterX > collisionCenterX && originalXMoveAmount < 0) {
                  xMoveAmountChange = collisionBound.getWidth();
                } else if (objCenterX < collisionCenterX && originalXMoveAmount > 0) {
                  xMoveAmountChange = -collisionBound.getWidth();
                }
              }
              if (isYIntersection   ) {
                if (objCenterY > collisionCenterY && originalYMoveAmount < 0) {
                  yMoveAmountChange = collisionBound.getHeight();
                } else if (objCenterY < collisionCenterY && originalYMoveAmount > 0) {
                  yMoveAmountChange = -collisionBound.getHeight();
                }
              }
              if (xMoveAmountChange != 0) {
                obj.handleEvent(new StopEvent());
                obj.xVelocity = 0;
              }
              if (yMoveAmountChange != 0) {
                obj.yVelocity = 0;
                if (yMoveAmountChange < 0) {
                  obj.isTouchingGround = true;
                }
              }
              double oldXMoveAmountChange = xMoveAmount - originalXMoveAmount;
              double oldYMoveAmountChange = yMoveAmount - originalYMoveAmount;
              // If the move amount is negative, get the number which is greatest out of the two,
              // otherwise the number which is less.
              if (originalXMoveAmount < 0 && oldXMoveAmountChange < xMoveAmountChange) {
                xMoveAmount += xMoveAmountChange;
              } else if (originalXMoveAmount > 0 && oldXMoveAmountChange > xMoveAmountChange) {
                xMoveAmount += xMoveAmountChange;
              }
              if (originalYMoveAmount < 0 && oldYMoveAmountChange < yMoveAmountChange) {
                yMoveAmount += yMoveAmountChange;
              } else if (originalYMoveAmount > 0 && oldYMoveAmountChange > yMoveAmountChange) {
                yMoveAmount += yMoveAmountChange;
              }
            }
            obj.handleEvent(new CollisionEvent(collision.other, collisionBound));
          }
          obj.screenMove(originalX + xMoveAmount, originalY + yMoveAmount);
        }
      }
    }
    lastMovementTimes.put(obj, System.nanoTime());
    if (obj.collidable)

    {
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

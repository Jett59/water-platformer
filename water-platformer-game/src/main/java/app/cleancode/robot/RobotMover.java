package app.cleancode.robot;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.collisions.Collidable;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.GameProperty;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.annotations.ImportGameProperty;
import app.cleancode.scaga.engine.keyboard.Key;

@AttachedTo("robot")
public class RobotMover extends GameListener {
  private static final double PLATFORM_AVOIDANCE_BUFFER_WIDTH = 0.05;

  @ImportGameProperty(owner = "robot")
  public GameProperty platforms;

  @ImportGameObject
  public GameObject<?> player;

  private static record NearestPlatforms(GameObject<?> lower, GameObject<?> higher) {
  }

  private NearestPlatforms findNearestPlatform(double playerY) {
    List<GameObject<?>> platformsList = platforms.get();
    int min = 0, max = platformsList.size();
    while (min < max) {
      int mid = (min + max) / 2;
      GameObject<?> midPlatform = platformsList.get(mid);
      double platformY = midPlatform.getY();
      if (platformY > playerY) {
        min = mid + 1; // Mid is inclusive.
      } else if (platformY < playerY) {
        max = mid;
      } else {
        min = max = mid;
        break;
      }
    }
    min = Math.min(min, platformsList.size() - 1);
    GameObject<?> selectedPlatform = platformsList.get(min);
    GameObject<?> lowerPlatform = null, higherPlatform = null;
    boolean canGoLower = min > 0;
    boolean canGoHigher = min + 1 < platformsList.size();
    lowerPlatform = selectedPlatform.getY() < playerY && canGoLower ? platformsList.get(min - 1)
        : selectedPlatform;
    higherPlatform = selectedPlatform.getY() > playerY && canGoHigher ? platformsList.get(min + 1)
        : selectedPlatform;
    return new NearestPlatforms(lowerPlatform, higherPlatform);
  }

  private boolean isRobotMode = false;

  private final Robot robot;

  public RobotMover() throws Exception {
    robot = new Robot();
  }

  private boolean isJumping;
  private GameObject<?> targetPlatform;

  @Override
  public void update(State state) {
    if (state.keyState.isKeyDown(Key.R)) {
      isRobotMode = true;
    } else if (state.keyState.isKeyDown(Key.M)) {
      isRobotMode = false;
      // Reset all the keys in the robot.
      robot.keyRelease(KeyEvent.VK_SPACE);
      robot.keyRelease(KeyEvent.VK_LEFT);
      robot.keyRelease(KeyEvent.VK_RIGHT);
      isJumping = false;
    }
    if (isRobotMode) {
      if (targetPlatform != null && targetPlatform.destroyed) {
        List<GameObject<?>> platformsList = platforms.get();
        platformsList.remove(targetPlatform);
        targetPlatform = null;
      }
      /**
       * Half the (velocity * mass) is the total height of the jump due to gravity.
       * 
       * Let x = the average velocity. Since the velocity decreases linearly, the average velocity
       * from v -> 0 will be equal to v/2. That is as far as I could get with maths. It turns out
       * (via testing) that the formula is vx/m, where m is the mass.
       * 
       * Also note that this formula gives the value if there were an infinite number of frames per
       * second. Unfortunately computers aren't that powerful yet and the actual height is slightly
       * above that.
       */
      double predictedMaxPlayerJumpY = player.yVelocity < 0
          ? player.getY() - player.yVelocity * (player.yVelocity / 2) / player.mass
          : player.getY();
      // Check if the next highest platform is within our reach. If not, target the one below us so
      // we don't fall too far.
      var nearestPlatforms = findNearestPlatform(player.getY());
      targetPlatform = nearestPlatforms.higher;
      if (targetPlatform.getY() < predictedMaxPlayerJumpY) {
        targetPlatform = nearestPlatforms.lower;
      }
      boolean platformBelowPlayer = targetPlatform.getY() > player.getY();
      // Also jump at the same time.
      if (!isJumping) {
        robot.keyPress(KeyEvent.VK_SPACE);
        isJumping = true;
      }
      Bound targetPlatformBound = targetPlatform.getRegion().getTransformedBound();
      Bound playerBound = player.getRegion().getTransformedBound();
      // Start with a simple "which way is the closest to the platform".
      boolean shouldMoveLeft = playerBound.getCenterX() > targetPlatformBound.getCenterX();
      // But get more complicated to allow for platforms on top of each other.
      if (player.getY() > targetPlatform.getY()) {
        double playerMinX = playerBound.getMinX();
        double playerMaxX = playerBound.getMaxX();
        // Add the avoidance width so that the player doesn't try to get too close and hit the edge
        // of a platform.
        double adjustedPlatformAvoidanceWidth =
            state.getScreenWidth() * PLATFORM_AVOIDANCE_BUFFER_WIDTH;
        double platformMinX = targetPlatformBound.getMinX() - adjustedPlatformAvoidanceWidth;
        double platformMaxX = targetPlatformBound.getMaxX() + adjustedPlatformAvoidanceWidth;
        boolean minXInPlatform = playerMinX > platformMinX && playerMinX < platformMaxX;
        boolean maxXInPlatform = playerMaxX > platformMinX && playerMaxX < platformMaxX;
        if (minXInPlatform && !maxXInPlatform) {
          shouldMoveLeft = false;
        } else if (!minXInPlatform && maxXInPlatform) {
          shouldMoveLeft = true;
        } else if (minXInPlatform && maxXInPlatform) {
          // Go towards which ever edge is closer.
          double minXDelta = playerMinX - platformMinX;
          double maxXDelta = platformMaxX - playerMaxX;
          if (minXDelta < maxXDelta) {
            shouldMoveLeft = true;
          } else {
            shouldMoveLeft = false;
          }
        }
      }
      double playerCenterX = playerBound.getCenterX();
      double platformMinX = targetPlatformBound.getMinX();
      double platformCenterX = targetPlatformBound.getCenterX();
      double platformMaxX = targetPlatformBound.getMaxX();
      // Go the opposite way if it will be faster to get to the near edge and to the platform.
      if (shouldMoveLeft) {
        if (platformMinX + state.getScreenWidth() - playerCenterX < playerCenterX
            - platformCenterX) {
          shouldMoveLeft = false;
        }
      } else {
        if (state.getScreenWidth() - platformMaxX + playerCenterX < platformCenterX
            - playerCenterX) {
          shouldMoveLeft = true;
        }
      }
      boolean shouldStopMoving =
          platformBelowPlayer && playerCenterX > platformMinX && playerCenterX < platformMaxX;
      if (shouldStopMoving) {
        robot.keyRelease(KeyEvent.VK_LEFT);
        robot.keyRelease(KeyEvent.VK_RIGHT);
      } else if (shouldMoveLeft) {
        robot.keyRelease(KeyEvent.VK_RIGHT);
        robot.keyPress(KeyEvent.VK_LEFT);
      } else {
        robot.keyRelease(KeyEvent.VK_LEFT);
        robot.keyPress(KeyEvent.VK_RIGHT);
      }
    }
  }

  @Override
  public void startup(State state) {
    platforms.set(new ArrayList<>());
  }

  @Override
  public void onCollision(Collidable other, Bound collisionBound) {
    if (targetPlatform != null && other.toString().equals("platform") && other != targetPlatform) {
      // We collided with another platform. Reset our one.
      targetPlatform = null;
    }
  }

}

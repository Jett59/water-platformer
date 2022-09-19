package app.cleancode.scaga.animation;

import java.util.List;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/*
 * a class for creating animations. This uses a film strip system to switch frames.
 */
public class Animation extends Transition {
  private int currentFramePosition = 0;
  private int cellWidth;
  private int cellHeight;
  private int cellCount;
  private ImageView filmStrip;
  private List<Polygon2D> regions;

  public Animation(int cellCount, int totalWidth, int cellHeight, ImageView imageView,
      Duration duration, List<Polygon2D> regions) {
    this.cellCount = cellCount;
    this.cellHeight = cellHeight;
    this.cellWidth = totalWidth / cellCount;
    this.filmStrip = imageView;
    this.regions = regions;
    super.setCycleDuration(duration);
    super.setCycleCount(-1);

    updateViewPort(0);
  }

  @Override
  protected void interpolate(double frac) {
    updateViewPort(frac);
  }

  private void updateViewPort(double frac) {
    if (cellCount != 1) {
      double frameLength = 1d / (cellCount - 1);
      currentFramePosition = (int) (Math.floor(frac / frameLength));
      filmStrip
          .setViewport(new Rectangle2D(currentFramePosition * cellWidth, 0, cellWidth, cellHeight));
    } else {
      filmStrip.setViewport(new Rectangle2D(0, 0, cellWidth, cellHeight));
    }
  }

  /*
   * gets the bounds of this animation.
   * 
   * changing the values on this field will not effect the animation.
   * 
   * @return the bounds of this animation.
   */
  public app.cleancode.scaga.shape.polygon.Polygon2D getRegion() {
    return regions.get(currentFramePosition);
  }

  /*
   * returns the image view for this animation.
   * 
   * @return the image view for this animation
   */
  public ImageView getView() {
    return filmStrip;
  }

  public void active() {
    if (filmStrip.getParent() != null) {
      for (Polygon2D region : regions) {
        region.xProperty()
            .bind(filmStrip.translateXProperty().add(filmStrip.getParent().translateXProperty()));
        region.yProperty()
            .bind(filmStrip.translateYProperty().add(filmStrip.getParent().translateYProperty()));
      }
    }
  }
}

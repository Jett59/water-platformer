package app.cleancode.scaga.animation;

import java.util.List;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
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
    private List<Polygon> regions;

    public Animation(int cellCount, int totalWidth, int cellHeight, ImageView imageView,
            Duration duration, List<Polygon> regions) {
        this.cellCount = cellCount;
        this.cellHeight = cellHeight;
        this.cellWidth = totalWidth / cellCount;
        this.filmStrip = imageView;
        this.regions = regions;
        super.setCycleDuration(duration);
        super.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        updateViewPort(frac);
    }

    private void updateViewPort(double frac) {
        double frameLength = 1d / (cellCount - 1);
        currentFramePosition = (int) (Math.floor(frac / frameLength));
        filmStrip.setViewport(
                new Rectangle2D(currentFramePosition * cellWidth, 0, cellWidth, cellHeight));
    }

    /*
     * gets the bounds of this animation.
     * 
     * changing the values on this field will not effect the animation.
     * 
     * @return the bounds of this animation.
     */
    public app.cleancode.scaga.shape.polygon.Polygon2D getRegion() {
        return new app.cleancode.scaga.shape.polygon.Polygon2D(regions.get(currentFramePosition));
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
            for (Polygon region : regions) {
                region.translateXProperty().bind(filmStrip.translateXProperty()
                        .add(filmStrip.getParent().translateXProperty()));
                region.translateYProperty().bind(filmStrip.translateYProperty()
                        .add(filmStrip.getParent().translateYProperty()));
                region.scaleXProperty().bind(filmStrip.scaleXProperty()
                        .add(filmStrip.getParent().scaleXProperty()).divide(2));
                region.scaleYProperty().bind(filmStrip.scaleYProperty()
                        .add(filmStrip.getParent().scaleYProperty()).divide(2));
            }
        }
    }
}

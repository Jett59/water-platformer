package app.cleancode.scaga.progress;

import app.cleancode.scaga.shape.Rectangle2D;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.Group;

public class ProgressBar extends Group {
    private final Rectangle2D outer;
    private final Rectangle2D inner;
    private double progress;
    private final double width;

    public ProgressBar(double x, double y, double width, double height, double initialProgress,
            String baseId) {
        this.progress = initialProgress;
        this.outer = new Rectangle2D(x, y, width, height);
        this.inner = new Rectangle2D(x, y, width * progress, height);
        this.width = width;
        outer.setId(baseId + "_outer");
        inner.setId(baseId + "_inner");
        getChildren().add(inner);
        getChildren().add(outer);
    }

    public void setProgress(double value) {
        this.progress = value;
        outer.setWidth(width * progress);
    }

    public double getProgress() {
        return progress;
    }

    public Polygon2D getRegion() {
        return outer.getRegion();
    }
}

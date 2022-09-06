package app.cleancode.scaga.progress;

import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.config.GameObjectConfig;
import app.cleancode.scaga.engine.events.Event;
import app.cleancode.scaga.engine.events.ProgressUpdateEvent;
import app.cleancode.scaga.shape.polygon.Polygon2D;

public class ProgressBarGameObject extends GameObject<ProgressBar> {
    private final String name;
    private final double x, y, width, height;

    public ProgressBarGameObject(GameObjectConfig config) {
        super(config);
        this.name = config.getName();
        this.x = config.getX();
        this.y = config.getY();
        this.width = config.getWidth();
        this.height = config.getHeight();

        super.mass = config.getMass();
        super.drag = config.getDrag();
    }

    @Override
    public Polygon2D getRegion() {
        return node.getRegion();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void init() {
        node = new ProgressBar(x, y, width, height, 1, name);
    }

    @Override
    public void handleEvent(Event evt) {
        super.handleEvent(evt);
        switch (evt.getType()) {
            case PROGRESS_UPDATE: {
                node.setProgress(((ProgressUpdateEvent) evt).newValue);
                break;
            }
            default:
                break;
        }
    }
}

package app.cleancode.scaga.sounds;

import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.config.GameObjectConfig;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.Node;

public class SoundGameObject extends GameObject<Node> {
    private final String name;
    private final String soundName;
    private final SoundLoader soundLoader;
    public Sound sound;

    public SoundGameObject(GameObjectConfig config) {
        super(config);
        this.name = config.getName();
        this.soundName = config.getSoundName();
        this.soundLoader = new SoundLoader();
    }

    @Override
    public Polygon2D getRegion() {
        return new Polygon2D(0, 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void init() {
        sound = soundLoader.loadSound(soundName);
    }

}

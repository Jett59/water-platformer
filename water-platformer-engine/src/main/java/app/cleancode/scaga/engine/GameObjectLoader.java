package app.cleancode.scaga.engine;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.cleancode.scaga.characters.CharacterGameObject;
import app.cleancode.scaga.engine.config.GameObjectConfig;
import app.cleancode.scaga.progress.ProgressBarGameObject;
import app.cleancode.scaga.resources.ResourceReader;
import app.cleancode.scaga.shape.objects.RectangleGameObject;
import app.cleancode.scaga.sounds.SoundGameObject;
import app.cleancode.scaga.sprites.SpriteGameObject;
import javafx.scene.Node;

public class GameObjectLoader {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final String GAME_OBJECT_PATH_FORMAT = "/objects/%s.json";
    private final ObjectMapper mapper;
    private final ResourceReader resourceReader;

    public GameObjectLoader() {
        mapper = new ObjectMapper();
        resourceReader = new ResourceReader();
    }

    @SuppressWarnings("unchecked")
    public GameObject<?> loadGameObject(String name) {
        GameObject<? extends Node> result;
        String path = String.format(GAME_OBJECT_PATH_FORMAT, name);
        try {
            String json = resourceReader.getResourceAsString(path);
            GameObjectConfig config = mapper.readValue(json, GameObjectConfig.class);
            config.setX(config.getX() * screenSize.width);
            config.setY(config.getY() * screenSize.height);
            config.setWidth(config.getWidth() * screenSize.width);
            config.setHeight(config.getHeight() * screenSize.height);
            if (config.getProperties() == null) {
                config.setProperties(new String[0]);
            }
            switch (config.getType()) {
                case CHARACTER: {
                    result = new CharacterGameObject(config);
                    break;
                }
                case RECTANGLE: {
                    result = new RectangleGameObject(config);
                    break;
                }
                case SPRITE: {
                    result = new SpriteGameObject(config);
                    break;
                }
                case SOUND: {
                    result = new SoundGameObject(config);
                    break;
                }
                case PROGRESS: {
                    result = new ProgressBarGameObject(config);
                    break;
                }
                case CUSTOM: {
                    result = (GameObject<? extends Node>) Class.forName(config.getBaseClass())
                            .getConstructor(GameObjectConfig.class).newInstance(config);
                    break;
                }
                default:
                    throw new IllegalStateException(
                            "game object type " + config.getType().name() + "is not known");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

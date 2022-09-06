package app.cleancode.scaga.sounds;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.cleancode.scaga.resources.ResourceReader;
import app.cleancode.scaga.sounds.config.SoundConfig;
import javafx.scene.media.Media;

public class SoundLoader {
    private final ResourceReader resourceReader;
    private final ObjectMapper jsonParser;

    public SoundLoader() {
        resourceReader = new ResourceReader();
        jsonParser = new ObjectMapper();
    }

    public Sound loadSound(String name) {
        try {
            SoundConfig config = jsonParser.readValue(
                    resourceReader.getResourceAsString(String.format("/sounds/%s.json", name)),
                    SoundConfig.class);
            Sound result = new Sound(new Media(resourceReader
                    .getResourceUriString(String.format("/sounds/%s", config.getFileName()))));
            result.setAutoRepeat(config.getAutoRepeat());
            result.setAutoStart(config.getAutoStart());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

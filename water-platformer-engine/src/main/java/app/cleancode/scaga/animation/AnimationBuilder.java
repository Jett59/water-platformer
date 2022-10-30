package app.cleancode.scaga.animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import app.cleancode.scaga.animation.config.AnimationConfig;
import app.cleancode.scaga.regions.ImageToRegion;
import app.cleancode.scaga.resources.ResourceReader;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;

public class AnimationBuilder {
    private ResourceReader resourceReader;

    public AnimationBuilder() {
        resourceReader = new ResourceReader();
    }

    public Animation buildAnimation(String character, String animation, int cellCount,
            Duration duration, double height, boolean reversed) {
        String templatePath = String.format("/characters/%s/%s/[index].png", character, animation);
        Image testImage = SwingFXUtils.toFXImage(
                resourceReader.getResourceAsImage(templatePath.replace("[index]", "0")), null);
        int imageHeight = (int) Math.ceil(testImage.getHeight());
        double scale = height / imageHeight;
        int cellWidth = (int) Math.ceil(testImage.getWidth() * scale);
        List<Polygon2D> regions = new ArrayList<>();
        BufferedImage swingFilmStrip = new BufferedImage(cellWidth * cellCount, (int) height,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graphics = swingFilmStrip.getGraphics();
        for (int i = 0; i < cellCount; i++) {
            String path = templatePath.replace("[index]", Integer.toString(i));
            BufferedImage bufferedCell =
                    new BufferedImage(cellWidth, (int) height, BufferedImage.TYPE_4BYTE_ABGR);
            BufferedImage readCell = resourceReader.getResourceAsImage(path);
            Graphics cellGraphics = bufferedCell.getGraphics();
            cellGraphics.drawImage(readCell, 0, 0, cellWidth, (int) height, null);
            cellGraphics.dispose();
            Image cell = SwingFXUtils.toFXImage(bufferedCell, null);
            if (reversed) {
                var reversedImage = new WritableImage(cellWidth, (int) height);
                var writer = reversedImage.getPixelWriter();
                var reader = cell.getPixelReader();
                for (int x = 0; x < cellWidth; x++) {
                    for (int y = 0; y < (int) height; y++) {
                        writer.setArgb(cellWidth - x - 1, y, reader.getArgb(x, y));
                    }
                }
                cell = reversedImage;
                bufferedCell = SwingFXUtils.fromFXImage(cell, null);
            }
            Polygon2D imageRegion = ImageToRegion.getRegion(cell);
            regions.add(imageRegion);
            graphics.drawImage(bufferedCell, cellWidth * (i - 1), 0, cellWidth, (int) height, null);
        }
        graphics.dispose();
        Image filmStrip = SwingFXUtils.toFXImage(swingFilmStrip, null);
        var result = new Animation(cellCount, (int) filmStrip.getWidth(), (int) height,
                new ImageView(filmStrip), duration, regions);
        return result;
    }

    public Animation buildAnimation(String character, String animation, int cellCount,
            String duration, double height, boolean reversed) {
        return buildAnimation(character, animation, cellCount, Duration.valueOf(duration), height,
                reversed);
    }

    public Animation buildAnimation(AnimationConfig config) {
        return buildAnimation(config.getCharacter(), config.getAnimation(), config.getFrames(),
                config.getDuration(), config.getHeight(), config.getReversed());
    }
}

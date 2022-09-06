package app.cleancode.scaga.resources;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ResourceReader {
    public String getResourceAsString(String name) {
        try {
            return Files.readString(Paths.get("assets", name));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getResourceAsImage(String path) {
        try {
            return ImageIO.read(Paths.get("assets", path).toFile());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getResourceUriString(String name) {
        try {
            return Paths.get("assets", name).toUri().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

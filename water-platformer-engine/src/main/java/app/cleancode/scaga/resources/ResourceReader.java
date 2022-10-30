package app.cleancode.scaga.resources;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ResourceReader {
  private static String assetsPrefix = "assets";

  private static boolean isAssetsDirectory(String path) {
    return Files.exists(Paths.get(path));
  }

  static {
    if (isAssetsDirectory("assets")) {
      assetsPrefix = "assets";
    } else if (isAssetsDirectory("water-platformer-game/assets")) {
      assetsPrefix = "water-platformer-game/assets";
    } else {
      Path jpackageDirectory = Path.of(System.getProperty("jpackage.app-path")).getParent();
      Path siblingAppDirectory = jpackageDirectory.resolve("app");
      Path parentSiblingAppDirectory = jpackageDirectory.resolveSibling("app");
      if (isAssetsDirectory(siblingAppDirectory.resolve("assets").toString())) {
        assetsPrefix = siblingAppDirectory.resolve("assets").toString();
      } else if (isAssetsDirectory(parentSiblingAppDirectory.resolve("assets").toString())) {
        assetsPrefix = parentSiblingAppDirectory.resolve("assets").toString();
      } else {
        throw new IllegalStateException("Can't find the assets root");
      }
    }
  }

  public String getResourceAsString(String name) {
    try {
      return Files.readString(Paths.get(assetsPrefix, name));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public BufferedImage getResourceAsImage(String path) {
    try {
      return ImageIO.read(Paths.get(assetsPrefix, path).toFile());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public String getResourceUriString(String name) {
    try {
      return Paths.get(assetsPrefix, name).toUri().toURL().toExternalForm();
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }
}

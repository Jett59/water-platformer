package app.cleancode.scaga.resources;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ResourceReader {
  private static String assetsPrefix = "assets";
  static {
    // For jpackaged apps, we assume the assets is under app/ because there is no way to configure
    // it to use the root of the jpackage directory (our working directory).
    boolean isJpackage = System.getProperty("jpackage.app-path") != null;
    if (isJpackage) {
      assetsPrefix = Path.of(System.getProperty("java.home")).resolveSibling("app")
          .resolve("assets").toString();
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

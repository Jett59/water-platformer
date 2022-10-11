package app.cleancode.scaga.sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.config.GameObjectConfig;
import app.cleancode.scaga.engine.events.Event;
import app.cleancode.scaga.regions.ImageToRegion;
import app.cleancode.scaga.resources.ResourceReader;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class SpriteGameObject extends GameObject<ImageView> {
  private static final String PATH_FORMAT = "/sprites/%s.png";

  private final String spriteName;
  private final String name;
  private final double x, y;
  private double width, height;

  private final ResourceReader resourceReader;

  private Polygon2D region;

  public SpriteGameObject(GameObjectConfig config) {
    super(config);
    this.spriteName = config.getSpriteName();
    this.name = config.getName();

    this.x = config.getX();
    this.y = config.getY();

    this.width = config.getWidth();
    this.height = config.getHeight();

    super.mass = config.getMass();
    super.drag = config.getDrag();

    this.resourceReader = new ResourceReader();
  }

  @Override
  public Polygon2D getRegion() {
    return region;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void init() {
    if (width != 0 && height != 0) {
      System.err.println("error occured while trying to build sprite for " + name);
      throw new IllegalArgumentException("only one of either width and height can be specified");
    }
    BufferedImage bufferedImage =
        resourceReader.getResourceAsImage(String.format(PATH_FORMAT, spriteName));
    double scale;
    if (width != 0) {
      scale = width / bufferedImage.getWidth();
      height = bufferedImage.getHeight() * scale;
    } else {
      scale = height / bufferedImage.getHeight();
      width = bufferedImage.getWidth() * scale;
    }
    BufferedImage scaledImage =
        new BufferedImage((int) width, (int) height, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics graphics = scaledImage.getGraphics();
    graphics.drawImage(bufferedImage, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
    graphics.dispose();
    Image img = SwingFXUtils.toFXImage(scaledImage, null);
    region = ImageToRegion.getRegion(img);
    node = new ImageView(img);
    region.xProperty().bind(node.translateXProperty());
    region.yProperty().bind(node.translateYProperty());
    screenMove(x, y);
  }

  @Override
  public void handleEvent(Event evt) {
    super.handleEvent(evt);
  }

}

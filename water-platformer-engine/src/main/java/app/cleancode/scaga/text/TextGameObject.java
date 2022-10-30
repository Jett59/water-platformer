package app.cleancode.scaga.text;

import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.config.GameObjectConfig;
import app.cleancode.scaga.engine.events.Event;
import app.cleancode.scaga.engine.events.TextChangeEvent;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextGameObject extends GameObject<Text> {
  private final String name;
  private String text;
  @SuppressWarnings("unused")
  private double x, y, width, height;

  public TextGameObject(GameObjectConfig config) {
    super(config);
    this.name = config.getName();

    this.x = config.getX();
    this.y = config.getY();
    this.width = config.getWidth();
    this.height = config.getHeight();

    this.text = config.getText();

    super.mass = config.getMass();
    super.drag = config.getDrag();
    super.collidable = false;
  }

  @Override
  public Polygon2D getRegion() {
    return null;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void init() {
    node = new Text(text);
    node.setId(name);
    node.setWrappingWidth(width);
    node.setFont(Font.font(20d));
    screenMove(x, y);
  }

  @Override
  public void handleEvent(Event evt) {
    super.handleEvent(evt);
    switch (evt.getType()) {
      case TEXT_CHANGE: {
        TextChangeEvent textChangeEvent = (TextChangeEvent) evt;
        this.text = textChangeEvent.text;
        node.setText(text);
        break;
      }
      default:
        break;
    }
  }

}

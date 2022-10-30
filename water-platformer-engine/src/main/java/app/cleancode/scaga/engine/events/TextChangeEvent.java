package app.cleancode.scaga.engine.events;

public class TextChangeEvent implements Event {
public final String text;
  
public TextChangeEvent(String text) {
  this.text = text;
}

  @Override
  public Type getType() {
    return Type.TEXT_CHANGE;
  }
  
}

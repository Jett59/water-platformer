package app.cleancode.scaga.engine;

public class GameProperty {
    private Object value;
    public final GameObject<?> owner;

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) value;
    }

    public void set(Object value) {
        this.value = value;
    }

    public boolean getBoolean() {
        return value != null ? get() : false;
    }

    public byte getByte() {
        return value != null ? get() : 0;
    }

    public char getChar() {
      return value != null ? get() : '\0';
    }

    public double getDouble() {
      return value != null ? get() : 0;
    }

    public float getFloat() {
      return value != null ? get() : 0;
    }

    public int getInt() {
      return value != null ? get() : 0;
    }

    public long getLong() {
      return value != null ? get() : 0;
    }

    public short getShort() {
      return value != null ? get() : 0;
    }

    public String getString() {
      return get();
    }

    public String toString() {
        return value.toString();
    }

    public GameProperty(GameObject<?> owner) {
        this.owner = owner;
    }
}

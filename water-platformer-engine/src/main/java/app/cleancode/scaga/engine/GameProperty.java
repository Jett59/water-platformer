package app.cleancode.scaga.engine;

import java.util.Objects;

public class GameProperty {
    private Object value;
    public final GameObject<?> owner;

    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) value;
    }

    public void set(Object value) {
        this.value = value;
        System.out.println("Game property owned by " + owner + ": " + value);
        System.out.println(new Exception().getStackTrace()[1]);
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
        return Objects.toString(value);
    }

    public GameProperty(GameObject<?> owner) {
        this.owner = owner;
    }
}

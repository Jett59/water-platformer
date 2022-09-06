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
        return get();
    }

    public byte getByte() {
        return get();
    }

    public char getChar() {
        return get();
    }

    public double getDouble() {
        return get();
    }

    public float getFloat() {
        return get();
    }

    public int getInt() {
        return get();
    }

    public long getLong() {
        return get();
    }

    public short getShort() {
        return get();
    }

    public String getString() {
        return get();
    }

    public String toString() {
        return value.toString();
    }

    public GameProperty(GameObject<?> owner) {
        System.out.println("Created game property");
        this.owner = owner;
    }
}

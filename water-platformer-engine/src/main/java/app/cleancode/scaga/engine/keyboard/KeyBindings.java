package app.cleancode.scaga.engine.keyboard;

public class KeyBindings {
    public static final Key MOVE_LEFT = Key.LEFT;
    public static final Key MOVE_RIGHT = Key.RIGHT;
    public static final Key JUMP = Key.SPACE;

    public static Key forName(String name) {
        return Key.valueOf(name.toUpperCase());
    }
}

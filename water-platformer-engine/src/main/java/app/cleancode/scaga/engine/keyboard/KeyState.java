package app.cleancode.scaga.engine.keyboard;

import java.util.HashMap;
import java.util.Map;

public class KeyState {
    protected final Map<Key, KeyStatus> keyState;

    public KeyState() {
        this.keyState = new HashMap<>();
        for (Key key : Key.values()) {
            keyState.put(key, new KeyStatus());
        }
    }

    public boolean isKeyDown(Key key) {
        return keyState.get(key).down;
    }

    public void setKeyState(Key key, boolean state) {
        keyState.get(key).down = state;
    }
}

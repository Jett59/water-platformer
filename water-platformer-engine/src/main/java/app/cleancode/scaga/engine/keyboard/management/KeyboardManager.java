package app.cleancode.scaga.engine.keyboard.management;

import app.cleancode.scaga.engine.keyboard.Key;
import app.cleancode.scaga.engine.keyboard.KeyBindings;
import app.cleancode.scaga.engine.keyboard.KeyState;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class KeyboardManager implements EventHandler<KeyEvent> {
    private final KeyState keyState;

    public KeyboardManager(KeyState keyState) {
        this.keyState = keyState;
    }

    public void bind(Stage stage) {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, this);
        stage.addEventHandler(KeyEvent.KEY_RELEASED, this);
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            keyPressed(event.getCode());
        } else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            keyReleased(event.getCode());
        }
    }

    private void keyPressed(KeyCode code) {
        updateKeyState(code, true);
    }

    private void keyReleased(KeyCode code) {
        updateKeyState(code, false);
    }

    private void updateKeyState(KeyCode code, boolean newState) {
        switch (code) {
            case LEFT: {
                keyState.setKeyState(Key.LEFT, newState);
                break;
            }
            case RIGHT: {
                keyState.setKeyState(Key.RIGHT, newState);
                break;
            }
            case UP: {
                keyState.setKeyState(Key.UP, newState);
                break;
            }
            case DOWN: {
                keyState.setKeyState(Key.DOWN, newState);
                break;
            }
            case SPACE: {
                keyState.setKeyState(Key.SPACE, newState);
                break;
            }
            case ENTER: {
                keyState.setKeyState(Key.ENTER, newState);
                break;
            }
            default:
              try {
                    keyState.setKeyState(KeyBindings.forName(code.getChar()), newState);
              }catch (IllegalArgumentException e) {
                // Ignore unknown keys.
              }
                break;
        }
    }
}

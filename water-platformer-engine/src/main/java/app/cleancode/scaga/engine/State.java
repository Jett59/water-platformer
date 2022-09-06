package app.cleancode.scaga.engine;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.function.Consumer;
import app.cleancode.scaga.engine.keyboard.KeyState;
import app.cleancode.scaga.engine.scene.Scene;
import javafx.scene.Node;

public class State {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private boolean initialized = false;
    public final KeyState keyState;
    private final Scene scene;
    private final GameObjectLoader objectLoader;
    private final GameListenerLoader listenerLoader;
    private final Consumer<GameObject<?>> gameObjectDestroyedCallback;

    protected State(KeyState keyState, Scene scene,
            Consumer<GameObject<?>> gameObjectDestroyedCallback) {
        this.keyState = keyState;
        this.scene = scene;
        this.objectLoader = new GameObjectLoader();
        this.listenerLoader = new GameListenerLoader();
        this.gameObjectDestroyedCallback = gameObjectDestroyedCallback;
    }

    public void createGameObject(GameObject<?> template, double x, double y) {
        try {
            GameObject<? extends Node> newGameObject =
                    template.duplicate(objectLoader, listenerLoader, this, this.scene.objects);
            newGameObject.init();
            newGameObject.move(x * screenSize.width, y * screenSize.height);
            scene.objects.add(newGameObject);
            scene.listeners.addAll(newGameObject.attachedListeners);
            scene.gamePane.getChildren().add(newGameObject.node);
            for (GameListener listener : newGameObject.attachedListeners) {
                listener.startup(this);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while creating object " + template.getName(), e);
        }
    }

    public void destroyGameObject(GameObject<?> object) {
        for (GameListener listener : object.attachedListeners) {
            scene.listeners.remove(listener);
        }
        scene.objects.remove(object);
        scene.gamePane.getChildren().remove(object.node);
        gameObjectDestroyedCallback.accept(object);
    }

    public void init() {
        if (!initialized) {
            for (GameListener listener : scene.listeners) {
                listener.startup(this);
            }
        } else {
            throw new IllegalStateException("state already initialized");
        }
    }
    
    public double getCameraX() {
      return scene.camera.getTranslateX();
    }
    public void setCameraX(double x) {
      scene.camera.setTranslateX(x);
    }
    public double getCameraY() {
      return scene.camera.getTranslateY();
    }
    public void setCameraY(double y) {
      scene.camera.setTranslateY(y);
    }
}

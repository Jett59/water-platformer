package app.cleancode.scaga.engine.scene;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameObject;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Scene {
    public final List<GameListener> listeners;
    public final List<GameObject<? extends Node>> objects;
    public final Pane gamePane;
    public final Camera camera;
    public final Color backgroundColor;

    public Scene(List<GameObject<? extends Node>> objects, List<GameListener> listeners,
            Pane gamePane, Camera camera, Color backgroundColor) {
        this.listeners = new CopyOnWriteArrayList<>(listeners);
        this.objects = new CopyOnWriteArrayList<>(objects);
        this.gamePane = gamePane;
        this.camera = camera;
        this.backgroundColor = backgroundColor;
    }
}

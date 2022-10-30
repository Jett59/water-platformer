package app.cleancode.scaga;

import java.nio.file.Paths;
import java.util.function.Consumer;
import app.cleancode.scaga.engine.GameListener;
import app.cleancode.scaga.engine.GameLoop;
import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.PhysicalLaw;
import app.cleancode.scaga.engine.State;
import app.cleancode.scaga.engine.keyboard.KeyState;
import app.cleancode.scaga.engine.keyboard.management.KeyboardManager;
import app.cleancode.scaga.engine.physics.Drag;
import app.cleancode.scaga.engine.physics.Gravity;
import app.cleancode.scaga.engine.physics.Movement;
import app.cleancode.scaga.engine.scene.SceneLoader;
import app.cleancode.scaga.resources.ResourceReader;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameStart extends Application {
  private static PhysicalLaw[] laws = new PhysicalLaw[] {new Gravity(), new Drag(), new Movement()};

  public static void begin(String[] args) {
    launch(args);
  }

  private Pane nodes = new Pane();

  private KeyState keyState;

  private State state;

  private app.cleancode.scaga.engine.scene.Scene scene;

  @SuppressWarnings("exports")
  @Override
  public void start(Stage primaryStage) throws Exception {
    Scene scene = new Scene(nodes);
    scene.getStylesheets().add(new ResourceReader().getResourceUriString("styles/app.css"));
    primaryStage.setTitle("Game");
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    primaryStage.setScene(scene);
    primaryStage.show();

    this.scene = new SceneLoader().getScene("/scenes/default.json");
    keyState = new KeyState();
    scene.setFill(this.scene.backgroundColor);

    // todo: make it easier to initialize State
    var stateConstructor =
        State.class.getDeclaredConstructor(KeyState.class, this.scene.getClass(), Consumer.class);
    stateConstructor.setAccessible(true);
    state = stateConstructor.newInstance(keyState, this.scene, new Consumer<GameObject<?>>() {

      @Override
      public void accept(GameObject<?> obj) {
        for (PhysicalLaw law : laws) {
          law.destroyGameObject(obj);
        }
      }

    });
    state.init();
    new KeyboardManager(keyState).bind(primaryStage);
    nodes.getChildren().add(this.scene.gamePane);
    scene.setCamera(this.scene.camera);
    GameLoop loop = new GameLoop(this::tick);
    loop.start();
  }

  @SuppressWarnings("unchecked")
  public void tick() {
    for (GameListener gameListener : scene.listeners) {
      gameListener.update(state);
    }
    for (GameObject<? extends Node> object : scene.objects) {
      object.isTouchingGround = false;
    }
    for (PhysicalLaw law : laws) {
      for (GameObject<? extends Node> gameObject : scene.objects) {
        law.handle((GameObject<Node>) gameObject);
      }
    }
  }

}

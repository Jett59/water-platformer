package app.cleancode.scaga.engine;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {
    private Runnable tick;

    public GameLoop(Runnable tick) {
        this.tick = tick;
    }

    @Override
    public void handle(long now) {
        tick.run();
    }
}

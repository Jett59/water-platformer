package app.cleancode.scaga.engine.physics;

import java.util.HashMap;
import java.util.Map;

import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.PhysicalLaw;
import javafx.scene.Node;

public class Gravity extends PhysicalLaw {
    public static double GRAVITY = 1;

    private final Map<GameObject<? extends Node>, Long> lastGravityEffectTimes;

    public Gravity() {
        this.lastGravityEffectTimes = new HashMap<>();
    }

    @Override
    public void handle(GameObject<?> obj) {
        if (!obj.isTouchingGround) {
            if (lastGravityEffectTimes.containsKey(obj)) {
                long lastGravityEffectTime = lastGravityEffectTimes.get(obj);
                long currentTime = System.nanoTime();
                long delta = currentTime - lastGravityEffectTime;
                double acceleration = obj.mass * GRAVITY;
                acceleration *= delta / 1000000000d;
                obj.yVelocity += acceleration;
            }
        }
        lastGravityEffectTimes.put(obj, System.nanoTime());
    }

}

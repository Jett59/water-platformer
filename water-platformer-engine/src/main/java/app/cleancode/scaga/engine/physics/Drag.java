package app.cleancode.scaga.engine.physics;

import java.util.HashMap;
import java.util.Map;

import app.cleancode.scaga.engine.GameObject;
import app.cleancode.scaga.engine.PhysicalLaw;
import app.cleancode.scaga.engine.events.StopEvent;
import javafx.scene.Node;

public class Drag extends PhysicalLaw {

    private final Map<GameObject<? extends Node>, Long> lastDragTimes;

    public Drag() {
        lastDragTimes = new HashMap<>();
    }

    @Override
    public void handle(GameObject<?> obj) {
        if (lastDragTimes.containsKey(obj)) {
            long lastDragTime = lastDragTimes.get(obj);
            long currentTime = System.nanoTime();
            long delta = currentTime - lastDragTime;
            double dragAmount = delta / 1000000000d * obj.drag;
            if (obj.isTouchingGround) {
                if (obj.xVelocity < 0) {
                    obj.xVelocity = Math.min(0, obj.xVelocity + dragAmount);
                    if (obj.xVelocity == 0) {
                        obj.handleEvent(new StopEvent());
                    }
                } else if (obj.xVelocity > 0) {
                    obj.xVelocity = Math.max(0, obj.xVelocity - dragAmount);
                    if (obj.xVelocity == 0) {
                        obj.handleEvent(new StopEvent());
                    }
                }
            } else {
                if (obj.xVelocity < 0) {
                    obj.xVelocity = Math.min(0, obj.xVelocity + dragAmount / 6d);
                    if (obj.xVelocity == 0) {
                        obj.handleEvent(new StopEvent());
                    }
                } else if (obj.xVelocity > 0) {
                    obj.xVelocity = Math.max(0, obj.xVelocity - dragAmount / 6d);
                    if (obj.xVelocity == 0) {
                        obj.handleEvent(new StopEvent());
                    }
                }
            }
        }
        lastDragTimes.put(obj, System.nanoTime());
    }

}

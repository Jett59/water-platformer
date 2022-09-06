package app.cleancode.scaga.engine.events;

import app.cleancode.scaga.bounds.Bound;
import app.cleancode.scaga.collisions.Collidable;

public class CollisionEvent implements Event {
    public final Collidable other;
    public final Bound collisionBound;

    public CollisionEvent(Collidable other, Bound collisionBound) {
        this.other = other;
        this.collisionBound = collisionBound;
    }

    @Override
    public Type getType() {
        return Event.Type.COLLISION;
    }

}

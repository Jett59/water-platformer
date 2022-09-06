package app.cleancode.scaga.collisions;

import app.cleancode.scaga.bounds.Bound;

public class Collision {
    public final Bound intersectionRegion;
    public final Collidable other;

    public Collision(Collidable other, Bound intersectionRegion) {
        this.intersectionRegion = intersectionRegion;
        this.other = other;
    }
}

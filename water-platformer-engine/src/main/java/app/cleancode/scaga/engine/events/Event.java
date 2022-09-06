package app.cleancode.scaga.engine.events;

public interface Event {
    Type getType();

    public static enum Type {
        COLLISION, MOVE, STOP, PROGRESS_UPDATE, ATTACK
    }

}

package app.cleancode.scaga.engine.events;

public class MovementEvent implements Event {
    public final int direction;
    public final double amount;

    public MovementEvent(int direction, double amount) {
        this.direction = direction;
        this.amount = amount;
    }

    @Override
    public Type getType() {
        return Type.MOVE;
    }

}

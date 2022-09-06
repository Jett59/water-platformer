package app.cleancode.scaga.engine.events;

public class ProgressUpdateEvent implements Event {
    public final double newValue;

    public ProgressUpdateEvent(double newValue) {
        this.newValue = newValue;
    }

    @Override
    public Type getType() {
        return Type.PROGRESS_UPDATE;
    }

}

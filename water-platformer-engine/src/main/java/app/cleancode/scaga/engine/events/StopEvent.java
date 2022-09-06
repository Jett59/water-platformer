package app.cleancode.scaga.engine.events;

public class StopEvent implements Event {

    @Override
    public Type getType() {
        return Type.STOP;
    }

}

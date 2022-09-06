package app.cleancode.scaga.engine.events;

public class AttackEvent implements Event {

    @Override
    public Type getType() {
        return Type.ATTACK;
    }

}

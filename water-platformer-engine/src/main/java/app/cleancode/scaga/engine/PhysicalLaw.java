package app.cleancode.scaga.engine;

public abstract class PhysicalLaw {
    public abstract void handle(GameObject<?> obj);

    public void destroyGameObject(GameObject<?> object) {}
}

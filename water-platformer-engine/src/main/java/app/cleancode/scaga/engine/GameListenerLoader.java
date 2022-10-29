package app.cleancode.scaga.engine;

import java.lang.reflect.Field;

import app.cleancode.scaga.engine.annotations.AttachedTo;
import app.cleancode.scaga.engine.annotations.ImportGameObject;
import app.cleancode.scaga.engine.annotations.ImportGameProperty;
import javafx.scene.Node;

public class GameListenerLoader {
    public void prepareListener(GameListener listener, GameObject<?>[] gameObjects) {
        try {
            Class<? extends GameListener> claz = listener.getClass();
            if (claz.isAnnotationPresent(AttachedTo.class)) {
                String objectName = claz.getAnnotation(AttachedTo.class).value();
                for (GameObject<? extends Node> object : gameObjects) {
                    if (object.getName().equalsIgnoreCase(objectName)) {
                        object.attachListener(listener);
                        break;
                    }
                }
            }
            for (Field field : claz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ImportGameObject.class)) {
                    field.setAccessible(true);
                    String objectName = getObjectName(field);
                    for (GameObject<? extends Node> object : gameObjects) {
                        if (object.getName().equalsIgnoreCase(objectName)) {
                            field.set(listener, object);
                            break;
                        }
                    }
                }
                if (field.isAnnotationPresent(ImportGameProperty.class)) {
                    String propertyName = field.getName();
                    String owner = field.getDeclaredAnnotation(ImportGameProperty.class).owner();
                    for (GameObject<?> object : gameObjects) {
                        if (object.getName().equalsIgnoreCase(owner)
                                && object.properties.containsKey(propertyName)) {
                            field.setAccessible(true);
                            field.set(listener, object.getProperty(propertyName));
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getObjectName(Field field) throws Exception {
        String annotationParam = field.getDeclaredAnnotation(ImportGameObject.class).value();
        return annotationParam.isEmpty() ? field.getName() : annotationParam;
    }
}

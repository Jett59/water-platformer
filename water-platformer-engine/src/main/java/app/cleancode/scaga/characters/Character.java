package app.cleancode.scaga.characters;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.cleancode.scaga.animation.Animation;
import app.cleancode.scaga.animation.AnimationBuilder;
import app.cleancode.scaga.animation.config.AnimationConfig;
import app.cleancode.scaga.resources.ResourceReader;
import app.cleancode.scaga.shape.polygon.Polygon2D;
import javafx.scene.Group;

public class Character extends Group {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private final Map<String, Animation> animations;
    private final ResourceReader resourceReader;
    private final ObjectMapper objectMapper;
    private CharacterConfig config;

    public Character(String characterName) {
        animations = new HashMap<>();
        resourceReader = new ResourceReader();
        objectMapper = new ObjectMapper();
        try {
            config = objectMapper.readValue(
                    resourceReader.getResourceAsString(
                            String.format("/config/characters/%s.json", characterName)),
                    CharacterConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating config for character " + characterName, e);
        }
        AnimationBuilder animationBuilder = new AnimationBuilder();
        for (AnimationConfig animation : config.getAnimations()) {
            animation.setHeight(animation.getHeight() * screenSize.height);

            animation.setReversed(false);
            Animation builtAnimation = animationBuilder.buildAnimation(animation);
            animations.put(animation.getAnimation() + ".Right", builtAnimation);
            builtAnimation.getView().setVisible(false);
            getChildren().add(builtAnimation.getView());
            builtAnimation.active();
            animation.setReversed(true);
            builtAnimation = animationBuilder.buildAnimation(animation);
            animations.put(animation.getAnimation() + ".Left", builtAnimation);
            builtAnimation.getView().setVisible(false);
            getChildren().add(builtAnimation.getView());
            builtAnimation.active();
        }
        direction = 1;
        currentState = State.IDLE;
        refreshState();
    }

    private State currentState;
    private int direction;

    public void changeState(State newState) {
        if (newState != null && !newState.equals(currentState)) {
            Animation currentAnimation = animations.get(getFullStateString());
            currentAnimation.getView().setVisible(false);
            currentAnimation.stop();
            currentState = newState;
            Animation newAnimation = animations.get(getFullStateString());
            newAnimation.play();
            newAnimation.getView().setVisible(true);
        }
    }

    public void setDirection(int direction) {
        if (direction != this.direction) {
            this.direction = direction;
            refreshState();
        }
    }

    private void refreshState() {
        String currentStateString = getFullStateString();
        animations.forEach((key, value) -> {
            if (!key.equals(currentStateString)) {
                value.getView().setVisible(false);
                value.stop();
            } else {
                value.getView().setVisible(true);
                value.playFromStart();
            }
        });
    }

    private String getDirectionString() {
        return direction >= 0 ? "Right" : "Left";
    }

    private String getFullStateString() {
        return String.format("%s.%s", currentState.getId(), getDirectionString());
    }

    public static enum State {
        ATTACKING("Attack"), DIEING("Die"), IDLE("Idle"), JUMPING("Jump"), RUNNING("Run");

        private String id;

        private State(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public Polygon2D getRegion() {
        return animations.get(getFullStateString()).getRegion();
    }
}

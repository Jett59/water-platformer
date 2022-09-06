package app.cleancode.scaga.sounds;

import javafx.animation.Animation;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    private final MediaPlayer mediaPlayer;

    public Sound(Media media) {
        this.mediaPlayer = new MediaPlayer(media);
    }

    public void setAutoRepeat(boolean value) {
        if (value) {
            mediaPlayer.setCycleCount(Animation.INDEFINITE);
        }
    }

    public void setAutoStart(boolean value) {
        mediaPlayer.setAutoPlay(value);
    }

    public void play() {
        mediaPlayer.play();
    }
}

package app.cleancode.scaga.sounds.config;

public class SoundConfig {
    private String fileName;
    private boolean autoRepeat;
    private boolean autoStart;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean getAutoRepeat() {
        return autoRepeat;
    }

    public void setAutoRepeat(boolean autoRepeat) {
        this.autoRepeat = autoRepeat;
    }

    public boolean getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
    }
}

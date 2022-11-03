package settings;

public class ConfigFile {
    int musicVolume, soundVolume;
    Boolean fullScreen;
    int highScore;

    public ConfigFile() {
        musicVolume = 3;
        soundVolume = 3;
        fullScreen = false;
        highScore = 0;
    }

    protected int getMusicVolume() {
        return musicVolume;
    }

    protected int getSoundVolume() {
        return soundVolume;
    }

    protected Boolean getFullScreen() {
        return fullScreen;
    }

    protected int getHighScore() {
        return highScore;
    }

    protected void setMusicVolume(int volume) {
        musicVolume = volume;
    }

    protected void setSoundVolume(int volume) {
        soundVolume = volume;
    }

    protected void setFullScreen(Boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    protected void setHighScore(int score) {
        highScore = score;
    }
}

package settings;

/**
 * Contains data for game settings such as volume of music and soundeffects, fullscreen status and stores high scores.
 * Data inside this class will be accessed and modified using setters and getters from the Settings class. 
 * 
 * @author Jeffrey Jin (jjj9)
 * @see settings.Settings
 */
public class ConfigFile {
    int musicVolume, soundVolume;
    Boolean fullScreen;
    int highScore;

    /**
     * Constructs a new configuration file with default values for settings.
     */
    protected ConfigFile() {
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

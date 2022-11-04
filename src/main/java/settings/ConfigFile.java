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

    /**
     * @return the current music volume
     */
    protected int getMusicVolume() {
        return musicVolume;
    }

    /**
     * @return the current sound effect volume
     */
    protected int getSoundVolume() {
        return soundVolume;
    }

    /**
     * @return the status of the fullscreen
     */
    protected Boolean getFullScreen() {
        return fullScreen;
    }

    /**
     * @return the current high score
     */
    protected int getHighScore() {
        return highScore;
    }

    /**
     * Saves the volume of the music.
     * 
     * @param volume the new music volume
     */
    protected void setMusicVolume(int volume) {
        musicVolume = volume;
    }

    /**
     * Saves the volume of the sound effects.
     * 
     * @param volume the new sound effect volume
     */
    protected void setSoundVolume(int volume) {
        soundVolume = volume;
    }

    /**
     * Saves the status of full screen.
     * 
     * @param fullScreen the status of full screen
     */
    protected void setFullScreen(Boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    /**
     * Saves the high score of the player.
     * 
     * @param score the current high score of the player
     */
    protected void setHighScore(int score) {
        highScore = score;
    }
}

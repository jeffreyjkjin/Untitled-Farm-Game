package settings;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Random;

public class SettingsTest {
    
    @Test
    public void readAndWriteDataFromConfigFile() {
        Settings settings = Settings.getInstance();
        Random randGen = new Random();

        // Randomly generate values to write and read from the config file
        int musicVolume = randGen.nextInt(6);
        int soundVolume = randGen.nextInt(6);
        Boolean fullScreen = randGen.nextBoolean();
        int highScore = randGen.nextInt(10000);

        settings.setMusicVolume(musicVolume);
        settings.setSoundVolume(soundVolume);
        settings.setFullScreen(fullScreen);
        settings.setHighScore(highScore);

        assertEquals(musicVolume, settings.getMusicVolume());
        assertEquals(soundVolume, settings.getSoundVolume());
        assertEquals(fullScreen, settings.getFullScreen());
        assertEquals(highScore, settings.getHighScore());
    }
}

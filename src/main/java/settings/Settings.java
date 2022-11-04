package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Contains the logic to read and write data from settings.json configuration file.
 * Data stored in the ConfigFile object can be accessed and modified using several setters and getters.
 * Should be created in the GamePanel object so that the data in config file can be accessed or modified by GamePanel and the other objects within it.
 * 
 * @author Jeffrey Jin (jjj9)
 * @see settings.ConfigFile
 */
public class Settings {
    
    ObjectMapper mapper;
    ConfigFile file;

    /**
     * Creates an ObjectMapper object which is used to read the settings.json configuration file.
     * Saves the data from the config file to a ConfigFile object.
     * If settings.json is not found, a new config file will be instantiated and set to the default values.
     */
    public Settings() {
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        try {
            file = mapper.readValue(new File("src/main/resources/settings.json"), ConfigFile.class);
        }
        catch (FileNotFoundException e) {
            createNewConfigFile();
            saveConfigFile();
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Instantiates a new ConfigFile objects with default values.
     * Should only be called when the settings.json file cannot be found.
     */
    private void createNewConfigFile() {
        file = new ConfigFile();
    }

    /**
     * Saves the games current settings data to settings.json.
     */
    public void saveConfigFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/settings.json"), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMusicVolume() {
        return file.getMusicVolume();
    }

    public int getSoundVolume() {
        return file.getSoundVolume();
    }

    public Boolean getFullScreen() {
        return file.getFullScreen();
    }

    public int getHighScore() {
        return file.getHighScore();
    }

    public void setMusicVolume(int volume) {
        file.setMusicVolume(volume);
        saveConfigFile();
    }

    public void setSoundVolume(int volume) {
        file.setSoundVolume(volume);
        saveConfigFile();
    }

    public void setFullScreen(Boolean fullScreen) {
        file.setFullScreen(fullScreen);
        saveConfigFile();
    }

    public void setHighScore(int score) {
        file.setHighScore(score);
        saveConfigFile();
    }
}

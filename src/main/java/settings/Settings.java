package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Settings {
    
    ObjectMapper mapper;
    ConfigFile file;

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

    private void createNewConfigFile() {
        file = new ConfigFile();
    }

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

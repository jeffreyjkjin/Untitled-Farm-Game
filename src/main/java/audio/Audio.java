package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.net.URL;

import app.GamePanel;

/**
 * An abstract object that manages the playback of audio files.
 * Contains an array of URLs which point to each audio file.
 * Also stores and controls playback volume.
 * 
 * @author Long Nguyen (dln3)
 * @author Jeffrey Jin (jjj9)
 * @see audio.Music
 * @see audio.Sounds
 */
public abstract class Audio {
    GamePanel gp;    
    Clip clip;
    URL audioURL[];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    /**
     * Sets the current file to the i'th audio file in the URl array.
     * Loads the desired audio file and adjusts its volume.
     * 
     * @param i the index of the file in the URL array
     */
    protected void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines the volume of the audio files based on the volumeScale.
     * volumeScale ranges from zero to five.
     * Sets the volume of this object.
     */
    public void checkVolume(){
        switch(volumeScale){
            case 0: 
                volume = -80f; 
                break;
            case 1: 
                volume = -20f; 
                break;
            case 2: 
                volume = -12f; 
                break;
            case 3: 
                volume = -5f; 
                break;
            case 4:    
                volume = 1f; 
                break;
            case 5: 
                volume = 6f; 
                break;
        }
        fc.setValue(volume);
    }

    /**
     * Decrements the volumeScale by one.
     * Does not change anything if the volumeScale is less than zero.
     */
    public void lowerVolume() {
        if (volumeScale > 0) {
            volumeScale--;
        }
    }

    /**
     * Increments the volumeScale by one.
     * Does not change if the volumeScale is greater than five.
     */
    public void increaseVolume() {
        if (volumeScale < 5) {
            volumeScale++;
        }
    }

    /**
     * @return the current volumeScale of this object
     */
    public int getVolumeScale() {
        return volumeScale;
    }
}

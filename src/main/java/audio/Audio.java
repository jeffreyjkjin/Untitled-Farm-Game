package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.net.URL;

import app.GamePanel;

/**
 * Audio class manages the volume of sound effects and musics
 */
public abstract class Audio {
    GamePanel gp;    
    Clip clip;
    URL audioURL[];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    /**
     * setting the audio file to play
     * @param i
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
     * check the volume to set it when player changes the loudness
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
     * lowering volume
     */
    public void lowerVolume() {
        if (volumeScale > 0) {
            volumeScale--;
        }
    }

    /**
     * increasing volume
     */
    public void increaseVolume() {
        if (volumeScale < 5) {
            volumeScale++;
        }
    }

    /**
     * @return the volume depending on the player's volume settings
     */
    public int getVolumeScale() {
        return volumeScale;
    }
}

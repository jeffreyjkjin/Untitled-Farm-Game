package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.net.URL;

public abstract class Audio {
    Clip clip;
    URL audioURL[];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

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

    public void lowerVolume() {
        if (volumeScale > 0) {
            volumeScale--;
        }
    }

    public void increaseVolume() {
        if (volumeScale < 5) {
            volumeScale++;
        }
    }

    public int getVolumeScale() {
        return volumeScale;
    }
}

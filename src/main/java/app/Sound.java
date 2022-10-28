package app;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30]; // 30 different sounds; increase as needed
    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/bgmusic1.wav");
        soundURL[1] = getClass().getResource("/sounds/bgmusic2.wav");
        soundURL[2] = getClass().getResource("/sounds/cluck1.wav");
        soundURL[3] = getClass().getResource("/sounds/cluck2.wav");
        soundURL[4] = getClass().getResource("/sounds/pickup.wav");
        soundURL[5] = getClass().getResource("/sounds/chickendeath.wav");
        soundURL[6] = getClass().getResource("/sounds/select.wav");
        soundURL[7] = getClass().getResource("/sounds/enter.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}

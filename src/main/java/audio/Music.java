package audio;

import java.net.URL;

import javax.sound.sampled.Clip;

import app.GamePanel;

public class Music extends Audio {
    public Music(GamePanel gp) {
        this.gp = gp;

        audioURL = new URL[2]; // 2 music tracks, increase as needed
        audioURL[0] = getClass().getResource("/music/bgmusic1.wav");
        audioURL[1] = getClass().getResource("/music/bgmusic2.wav");

        volumeScale = gp.settings.getMusicVolume();
    }

    public void play(int index) {
        setFile(index);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
}

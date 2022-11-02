package audio;

import java.net.URL;

import javax.sound.sampled.Clip;

public class Music extends Audio {
    public Music() {
        audioURL = new URL[2]; // 2 music tracks, increase as needed
        audioURL[0] = getClass().getResource("/sounds/bgmusic1.wav");
        audioURL[1] = getClass().getResource("/sounds/bgmusic2.wav");
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

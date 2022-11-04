package audio;

import java.net.URL;

import javax.sound.sampled.Clip;

import app.GamePanel;

/**
 * Music manages playing and stopping background music
 * This class has an array of URL of music files
 */
public class Music extends Audio {
    public Music(GamePanel gp) {
        this.gp = gp;

        audioURL = new URL[2]; // 2 music tracks, increase as needed
        audioURL[0] = getClass().getResource("/music/bgmusic1.wav");
        audioURL[1] = getClass().getResource("/music/bgmusic2.wav");

        volumeScale = gp.settings.getMusicVolume();
    }

    /**
     * Play and loop background music depending on which file is set
     * @param index index of the file in URL array
     */
    public void play(int index) {
        setFile(index);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    /**
     * Stop the music file that is being played
     */
    public void stop() {
        clip.stop();
    }
}

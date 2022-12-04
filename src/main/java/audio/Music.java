package audio;

import java.net.URL;

import javax.sound.sampled.Clip;

/**
 * Manages and controls music tracks used by this game.
 * Music files can be accessed by indexing an array which contains the URLs of the files.
 * 
 * @author Long Nguyen (dln3)
 * @author Jeffrey Jin (jjj9)
 * @see audio.Audio
 */
public class Music extends Audio {
    private static Music musicInstance = null;
    
    /**
     * Constructs a new Music object and saves the music files to a URL array.
     * This audio array can be resized as more music tracks are needed.
     * Volume of the music is also loaded from the configuration file.
     * 
     * @see settings.Settings
     */
    private Music() {
        super();

        audioURL = new URL[2]; // 2 music tracks, increase as needed
        audioURL[0] = getClass().getResource("/music/bgmusic1.wav");
        audioURL[1] = getClass().getResource("/music/bgmusic2.wav");

        volumeScale = settings.getMusicVolume();
    }

    /**
     * @return singleton instance of Music
     */
    public static Music getInstance() {
        if (musicInstance == null) {
            musicInstance = new Music();
        }

        return musicInstance;
    }

    /**
     * Plays the music file at position index in the URL array.
     * Music track is loops until the stop method is called.
     * 
     * @param index index of the file in URL array
     */
    public void play(int index) {
        if (volumeScale != 0) {
            setFile(index);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    /**
     * Stops the music file that is currently being played.
     */
    public void stop() {
        clip.stop();
    }
}

package audio;

import java.net.URL;

/**
 * Controls and manages sound effects for the game.
 * Sound effects are accessed by indexing an array which contains the URLs of the sound files.
 * 
 * @author Long Nguyen (dln3)
 * @author Jeffrey Jin (jjj9)
 * @see audio.Audio
 */
public class SoundEffects extends Audio {
    private static SoundEffects soundInstance = null;

    /**
     * Instantiates a new SoundEffects object and saves sound effect files to a URL array.
     * This audio array can be resized as more sound effects are added.
     * Volume of the sound effects are also loaded from the configuration file.
     * 
     * @see settings.Settings
     */
    private SoundEffects() {
        super();

        audioURL = new URL[12]; // 12 sound effects, increase as needed
        audioURL[0] = getClass().getResource("/sounds/cluck1.wav");
        audioURL[1] = getClass().getResource("/sounds/cluck2.wav");
        audioURL[2] = getClass().getResource("/sounds/pickup.wav");
        audioURL[3] = getClass().getResource("/sounds/chickendeath.wav");
        audioURL[4] = getClass().getResource("/sounds/select.wav");
        audioURL[5] = getClass().getResource("/sounds/enter.wav");
        audioURL[6] = getClass().getResource("/sounds/eggcracking.wav");
        audioURL[7] = getClass().getResource("/sounds/trap.wav");
        audioURL[8] = getClass().getResource("/sounds/unlockgate.wav");
        audioURL[9] = getClass().getResource("/sounds/konamicode.wav");
        audioURL[10] = getClass().getResource("/sounds/resetscore.wav");
        audioURL[11] = getClass().getResource("/sounds/error.wav");

        volumeScale = settings.getSoundVolume();
    }

    /**
     * @return singleton instance of SoundEffects
     */
    public static SoundEffects getInstance() {
        if (soundInstance == null) {
            soundInstance = new SoundEffects();
        }

        return soundInstance;
    }

    /**
     * Plays the sound effect at position index in the URL array.
     * 
     * @param index index of the file in URL array
     */
    public void play(int index){
        setFile(index);
        clip.start();
    }
}

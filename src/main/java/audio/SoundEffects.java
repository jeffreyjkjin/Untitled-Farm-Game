package audio;

import java.net.URL;

import app.GamePanel;

/**
 * Controls and manages sound effects for the game.
 * Sound effects are accessed by indexing an array which contains the URLs of the sound files.
 * Should be instantianted from GamePanel so that other objects can access its sound effects.
 * 
 * @author Long Nguyen (dln3)
 * @author Jeffrey Jin (jjj9)
 * @see audio.Audio
 */
public class SoundEffects extends Audio {

    /**
     * Instantiates a new SoundEffects object and saves sound effect files to a URL array.
     * This audio array can be resized as more sound effects are added.
     * Links a GamePanel object to this object so that it can access its other objects.
     * Volume of the sound effects are also loaded from the configuration file.
     * 
     * @param gp GamePanel object that is used to run the game
     * @see app.GamePanel
     * @see settings.Settings
     */
    public SoundEffects(GamePanel gp) {
        this.gp = gp;

        audioURL = new URL[10]; // 10 sound effects, increase as needed
        audioURL[0] = getClass().getResource("/sounds/cluck1.wav");
        audioURL[1] = getClass().getResource("/sounds/cluck2.wav");
        audioURL[2] = getClass().getResource("/sounds/pickup.wav");
        audioURL[3] = getClass().getResource("/sounds/chickendeath.wav");
        audioURL[4] = getClass().getResource("/sounds/select.wav");
        audioURL[5] = getClass().getResource("/sounds/enter.wav");
        audioURL[6] = getClass().getResource("/sounds/eggcracking.wav");
        audioURL[7] = getClass().getResource("/sounds/trap.wav");
        audioURL[8] = getClass().getResource("/sounds/unlockgate.wav");

        volumeScale = gp.settings.getSoundVolume();
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

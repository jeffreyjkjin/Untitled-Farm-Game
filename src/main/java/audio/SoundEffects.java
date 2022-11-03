package audio;

import java.net.URL;

import app.GamePanel;

public class SoundEffects extends Audio {

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

    public void play(int index){
        setFile(index);
        clip.start();
    }
}

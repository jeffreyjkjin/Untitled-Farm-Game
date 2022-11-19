package audio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AudioTest {

    class AudioHelper extends Audio{
        public AudioHelper() {
            super();
        }
    }
    
    AudioHelper audio;

    @Before
    public void setupTests() {
        audio = new AudioHelper();
    }

    @Test
    public void decreaseVolumeScaleOnce() {
        audio.lowerVolume();

        assertEquals(2, audio.volumeScale);
    }

    @Test
    public void increaseVolumeScaleOnce() {
        audio.increaseVolume();

        assertEquals(4, audio.volumeScale);
    }

    @Test
    public void decreaseVolumeScaleManyTimes(){
        //when
        for (int i = 0; i < 10; i++) {
            audio.lowerVolume();
        }

        //then
        assertEquals(0, audio.volumeScale);
    }

    @Test
    public void increaseVolumeScaleManyTimes(){
        //when
        for (int i = 0; i < 10; i++) {
            audio.increaseVolume();
        }

        //then
        assertEquals(5, audio.volumeScale);
    }

}

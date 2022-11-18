package audio;

import static org.junit.Assert.*;
import org.junit.Test;

public class AudioTest {

    @Test
    public void LowerVolumeTest(){
        Audio audio = new Audio2();

        //when
        audio.lowerVolume();

        //then
        assertEquals(audio.volumeScale,2);
    }

    @Test
    public void IncreaseVolumeTest(){
        Audio audio = new Audio2();

        //when
        audio.increaseVolume();

        //then
        assertEquals(audio.volumeScale,4);
    }

    @Test
    public void ShouldBe0(){
        Audio audio = new Audio2();
        audio.volumeScale = 0;

        //when
        audio.lowerVolume();

        //then
        assertEquals(audio.volumeScale,0);
    }

    @Test
    public void ShouldBe5(){
        Audio audio = new Audio2();
        audio.volumeScale = 5;

        //when
        audio.increaseVolume();

        //then
        assertEquals(audio.volumeScale,5);
    }

    @Test
    public void EqualsVolumeScale(){
        Audio audio = new Audio2();
        
        //when
        int result = audio.getVolumeScale();

        //then
        assertEquals(result, 3);
    }

}

class Audio2 extends Audio{
    public Audio2(){
        super();
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

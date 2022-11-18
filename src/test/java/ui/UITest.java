package ui;

import static org.junit.Assert.*;
import org.junit.Test;

import app.GamePanel;

public class UITest {
    private GamePanel gp;

    @Test
    public void ShouldBe2Mover(){
        UI uitest = new UI2(gp);
        uitest.selectPosition = 0;

        //when
        uitest.moveSelectorUp();

        //then
        assertEquals(uitest.selectPosition, 2);
    }

    @Test
    public void ShouldBe0Mover(){
        UI uitest = new UI2(gp);
        uitest.selectPosition = 2;

        //when
        uitest.moveSelectorDown();

        //then
        assertEquals(uitest.selectPosition, 0);
    }

    @Test
    public void ShouldBe1Getter(){
        UI uitest = new UI2(gp);
        uitest.selectPosition = 1;

        //when
        uitest.getSelectorPosition();

        //then
        assertEquals(uitest.selectPosition, 1);
    }

    @Test
    public void ShouldBe0Resetter(){
        UI uitest = new UI2(gp);
        uitest.selectPosition = 1;

        //when
        uitest.resetSelectorPosition();;

        //then
        assertEquals(uitest.selectPosition, 0);
    }
}

class UI2 extends UI{ 
    public UI2(GamePanel gp) {
        super(gp);

        totalOptions = 2;
    }

    public void moveSelectorUp() {
        selectPosition--;
        if (selectPosition < 0) {
            selectPosition = totalOptions;
        }
    }

    public void moveSelectorDown() {
        selectPosition++;
        if (selectPosition > totalOptions) {
            selectPosition = 0;
        }
    }

    public int getSelectorPosition() {
        return selectPosition;
    }

    public void resetSelectorPosition() {
        selectPosition = 0;
    }
}

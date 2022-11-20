package ui;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import app.GamePanel;

public class UITest {
    public class UIHelper extends UI {
        public UIHelper(GamePanel gp) {
            super(gp);
            totalOptions = 5;
        }
    }

    UIHelper ui;
    GamePanel gp;

    @Before
    public void setupTests() {
        ui = new UIHelper(gp);
    }

    @Test
    public void moveSelectorUpOnce() {
        ui.moveSelectorUp();

        assertEquals(5, ui.getSelectorPosition());
    }

    @Test
    public void moveSelectorDownOnce() {
        ui.moveSelectorDown();

        assertEquals(1, ui.getSelectorPosition());
    }

    @Test
    public void moveSelectorUpManyTimes() {
        for (int i = 0; i < 10; i++) {
            ui.moveSelectorUp();
        }

        assertEquals(2, ui.getSelectorPosition());
    }

    @Test
    public void moveSelectorDownManyTimes() {
        for (int i = 0; i < 10; i++) {
            ui.moveSelectorDown();
        }

        assertEquals(4, ui.getSelectorPosition());
    }

}
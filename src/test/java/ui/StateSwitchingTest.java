package ui;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

import app.GamePanel;
import app.StateManager;
import app.StateManager.gameState;
import map.MapManager;
import object.OBJ_Gate;

class StateSwitchingTest {

	@Test
	void startWithTitle() {
		GamePanel gp = new GamePanel();
		
		assertEquals(gp.stateM.getCurrentState(), gameState.TITLE);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), TitleScreen.class);
	}
	
	@Test
	void TitletoPlay() {
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.TITLE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 0;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.PLAY);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), PlayScreen.class);
	}
	
	@Test
	void TitletoSetting() {

		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.TITLE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 1;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.SETTINGS);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), SettingsScreen.class);
	}

	
	@Test
	void TitletoCredit() {
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.TITLE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 2;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.CREDITS);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), CreditsScreen.class);
	}
	
	@Test
	void PlaytoPause() {
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.PLAY);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.PAUSE);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), PauseScreen.class);
	}
	
	@Test
	void PlaytoWin() {
		//given
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.PLAY);
		gp.mapM.currMap =  gp.mapM.mapList.length-1; // final map
		gp.mapM.getMap().objects[0] = new OBJ_Gate();
		gp.mapM.getMap().objects[0].update(gp); // open
		gp.mapM.getMap().keyNum = gp.player.keyCount;
		
		//when
		gp.player.objectInteraction(0); // interact gate
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.WIN);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), WinScreen.class);
	}
	
	@Test
	void PlaytoLose() {
		//given
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.PLAY);
		
		//when
		gp.player.health = 0;
		gp.player.update();
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.LOSE);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), LoseScreen.class);
		
	}
	
	@Test
	void PausetoPlay() { 
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.PAUSE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.PLAY);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), PlayScreen.class);	
	}
	
	@Test
	void PausetoSetting() {
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.PAUSE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 1;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.SETTINGS);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), SettingsScreen.class);
		
	}
	
	@Test
	void PausetoTitle() {
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.PAUSE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 2;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.TITLE);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), TitleScreen.class);
		
	}
	
	@Test
	void WintoTitle() {
		
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.WIN);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 1;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.TITLE);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), TitleScreen.class);
		
	}
	
	@Test
	void WintoPlay() {
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.WIN);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 0;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.PLAY);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), PlayScreen.class);
		
	}
	
	@Test
	void LosetoTitle() { 
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.LOSE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 1;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.TITLE);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), TitleScreen.class);
		
	}
	
	@Test
	void LosetoPlay() { 
		
		//when
		GamePanel gp = new GamePanel();
		gp.stateM.setCurrentState(gameState.LOSE);
		
		KeyEvent e = mock(KeyEvent.class);
		when(e.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		
		gp.uiM.ui[gp.stateM.getCurrentState().getValue()].selectPosition = 0;
		
		//when
		gp.inputM.getCurrentInput().keyPressed(e);
		
		//then
		assertEquals(gp.stateM.getCurrentState(), gameState.PLAY);
		assertEquals(gp.uiM.ui[gp.stateM.getCurrentState().getValue()].getClass(), PlayScreen.class);
		
	}
	
	

}

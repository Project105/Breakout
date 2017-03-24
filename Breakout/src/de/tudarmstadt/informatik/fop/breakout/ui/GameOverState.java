package de.tudarmstadt.informatik.fop.breakout.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntryAL;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;

/**
 * A BasicGameState that is called after the end of each game for gathering the
 * player's data for creating a new HighscoreEntry
 * 
 * @author Marcel Geibel
 *
 */
public class GameOverState extends BasicGameState implements GameParameters {

	/**
	 * Attributes
	 */
	private int idState;
	private StateBasedEntityManager entityManager;
	UnicodeFont font = null;
	TextField textField = null;
	String playerName;
	int playerBlocks;
	float playerTime;
	int playerPoints;
	HighscoreEntryAL newHighscoreAL;
	boolean isTopTen = false;
	int indexOfPlayer = -1;

	/**
	 * Constructor, for setting the attributes of the class and pulling a
	 * highscore file to compare the player's score to
	 * 
	 * @param ID the ID of the game state
	 */
	public GameOverState(int ID) {
		idState = ID;
		entityManager = StateBasedEntityManager.getInstance();	
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		/*
		 * Screen setup
		 */
		// Setting up background entity
		Entity background = new Entity(BACKGROUND_ID);
		// setting up position
		background.setPosition(new Vector2f(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2));
		// adding image to entity
		background.addComponent(new ImageRenderComponent(new Image(BACKGROUND_IMAGE)));
		// adding entity to entityManager
		entityManager.addEntity(idState, background);

		/*
		 * Escape function entity
		 */
		// Setting up entity
		Entity escListener = new Entity("ESC_Listener");
		// Event if is Escape pressed
		KeyPressedEvent esc = new KeyPressedEvent(Input.KEY_ESCAPE);
		// Action switch states if esc pressed
		esc.addAction(new ChangeStateAction(MAINMENU_STATE));
		// adding EventComponent in entity
		escListener.addComponent(esc);
		// adding entity in entityManager
		entityManager.addEntity(idState, escListener);
		
		/*
		 * Enter function entity
		 */
		Entity enterListener = new Entity("ENTER_Listener");
		// Event if is Enter pressed
		KeyPressedEvent enter = new KeyPressedEvent(Input.KEY_ENTER);
		enter.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				if(indexOfPlayer != -1) {
					playerName = textField.getText();
					newHighscoreAL.get(indexOfPlayer).setPlayerName(playerName);
					newHighscoreAL.writeHighscore();
				}
			}
			
		});
		enter.addAction(new ChangeStateInitAction(HIGHSCORE_STATE));
		// adding EventComponent in entity
		enterListener.addComponent(enter);
		// adding entity in entityManager
		entityManager.addEntity(idState, enterListener);
		
		/*
		 * Text field
		 */
		font = new UnicodeFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 26));
		textField = new TextField(arg0, arg0.getDefaultFont(), 260, 300, 300, 50);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		entityManager.renderEntities(arg0, arg1, arg2);
		if (isTopTen) {
			arg2.drawString("Please enter your name:", 300, 200);
			textField.render(arg0, arg2);

		} else {
			arg2.drawString("Too bad, no new top 10 score for you!", 240, 200);
			arg2.drawString("Press enter to continue...", 280, 320);
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		entityManager.updateEntities(arg0, arg1, arg2);
		textField.setFocus(true);
		// pull highscore file
		newHighscoreAL = new HighscoreEntryAL();
		newHighscoreAL.readHighscore();
		//checks if the new highscore is part of the top 10
		for(int i = 0; i < newHighscoreAL.getAL().size(); i++) {
			if (newHighscoreAL.get(i).getPlayerName().equals("NEWENTRY")) {
				isTopTen = true;
				indexOfPlayer = i;
			}
		}
	}

	@Override
	public int getID() {
		return idState;
	}

}

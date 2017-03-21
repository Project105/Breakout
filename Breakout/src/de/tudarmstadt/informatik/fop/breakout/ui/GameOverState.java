package de.tudarmstadt.informatik.fop.breakout.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.gui.TextField;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntryAL;
import eea.engine.action.basicactions.ChangeStateAction;
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
	HighscoreEntry newHighscore;
	HighscoreEntryAL newHighscoreAL;
	boolean isTopTen = false;

	/**
	 * Constructor, for setting the attributes of the class and pulling a
	 * highscore file to compare the player's score to
	 * 
	 * @param ID
	 *            the ID of the BasicGameState
	 * @param blocks
	 *            the nr of blocks the player destroyed
	 * @param time
	 *            the time elapsed in the game
	 * @param points
	 *            the nr of points the player scored
	 */
	public GameOverState(int ID, int blocks, float time, int points) {
		idState = ID;
		entityManager = StateBasedEntityManager.getInstance();
		playerBlocks = blocks;
		playerTime = time;
		playerPoints = points;
		// pull highscore file
		newHighscoreAL = new HighscoreEntryAL();
		newHighscoreAL.readHighscore();
		// if (playerPoints > newHighscoreAL.getAL().get(9).getPoints()) THIS IS
		// CORRECT
		// THE FOLLOWING IS FOR TESTING ONLY
		if (playerBlocks > newHighscoreAL.getAL().get(9).getNumberOfDestroyedBlocks())
			isTopTen = true;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

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
		 * Text field
		 */
		font = new UnicodeFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 26));
		textField = new TextField(arg0, arg0.getDefaultFont(), 260, 300, 300, 50);
		

		/*
		 * Adding new highscore
		 * Will automatically ignore non top10 score
		 */
		newHighscore = new HighscoreEntry(playerName, playerBlocks, playerTime, playerPoints);
		newHighscoreAL.addHighscoreEntry(newHighscore);
		newHighscoreAL.writeHighscore();
		// Here the player object must be created using playerName,
		// playerBlocks, playerTime, playerPoints
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		entityManager.renderEntities(arg0, arg1, arg2);
		if (isTopTen) {
			//FOR TESTING ONLY
			arg2.drawString(playerName, 300, 230);

			arg2.drawString("Please enter your name:", 300, 200);
			textField.render(arg0, arg2);
			
		} else
			arg2.drawString("Too bad, no new top 10 score for you!", 240, 200);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		entityManager.updateEntities(arg0, arg1, arg2);
		playerName = textField.getText();
	}

	@Override
	public int getID() {
		return idState;
	}

}

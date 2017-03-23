package de.tudarmstadt.informatik.fop.breakout.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import de.tudarmstadt.informatik.fop.breakout.highscore.*;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;

/**
 * Class for displaying the current highscore rankings within a BaisGameState
 * 
 * @author Marcel Geibel
 */
public class HighscoreState extends BasicGameState implements GameParameters {

	/**
	 * Attributes
	 */

	private int idState;
	private StateBasedEntityManager entityManager;
	HighscoreEntryAL scores = new HighscoreEntryAL();

	/**
	 * Constructor
	 */

	/**
	 * Constructor, setting id and StateBasedEntityManager for this state
	 * 
	 * @param ID
	 *            the id of the game state
	 */
	public HighscoreState(int ID) {
		idState = ID;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		/*
		 * Screen background entity
		 */
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
		 * Main Menu Button
		 */
		Entity menu = new Entity("Main Menu");
		menu.setPosition(new Vector2f(218, 450));
		menu.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		ANDEvent andEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		// Action- going into Highscore State
		andEvents.addAction(new ChangeStateInitAction(MAINMENU_STATE));
		// adding eventComponent
		menu.addComponent(andEvents);
		// adding Entity in EntityManager
		entityManager.addEntity(idState, menu);
		scores.readHighscore();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		entityManager.renderEntities(arg0, arg1, arg2);
		arg2.drawString(scores.toString(), 40, 100);
		arg2.drawString("Main Menu", 190, 440);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		entityManager.updateEntities(arg0, arg1, arg2);
		scores.readHighscore();


	}

	@Override
	public int getID() {
		return idState;
	}

}
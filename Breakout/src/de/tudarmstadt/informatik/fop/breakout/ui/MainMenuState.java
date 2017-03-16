package de.tudarmstadt.informatik.fop.breakout.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/*
 * @Author Denis Andric
 */
public class MainMenuState extends BasicGameState implements GameParameters {

	private int idState;
	private StateBasedEntityManager entityManager;

	public MainMenuState(int id) {
		idState = id;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer sc, StateBasedGame sb) throws SlickException {

		// Setting position and image for background entity
		Entity background = new Entity("menu");
		background.setPosition(new Vector2f(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2));
		background.addComponent(new ImageRenderComponent(new Image(BACK_IMAGE)));
		entityManager.addEntity(idState, background);

		/*
		 =============== =============== =============== BUTTONS  =============== =============== ===============
		 */
		
		
		
		/*
		 =============== =============== NEW GAME BUTTON  =============== ===============
		 *
		 *
		 * New Game Button, setting position, image, events and action
		 * 
		 * On click need to go in GameplayState
		 */
		// Setting up entity
		Entity nGame = new Entity("New Game");
		// Postion setting
		nGame.setPosition(new Vector2f(218, 190));
		// setting image
		nGame.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		// Event- mouse relevant

		ANDEvent nEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		// Action- going into GameplayState
		nEvents.addAction(new ChangeStateInitAction(GAMEPLAY_STATE));
		// adding eventComponent
		nGame.addComponent(nEvents);
		// adding Entity in EntityManager
		entityManager.addEntity(idState, nGame);

		
		
		/*
		 =============== =============== HIGHSCORE BUTTON  =============== ===============
		 */
		Entity high = new Entity("Highscore");
		high.setPosition(new Vector2f(218, 290));
		high.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		ANDEvent andEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		// Action- going into Highscore State
		andEvents.addAction(new ChangeStateInitAction(HIGHSCORE_STATE));
		// adding eventComponent
		high.addComponent(andEvents);
		// adding Entity in EntityManager
		entityManager.addEntity(idState, high);

		
		
		
		
		/*
		 * unused Button1
		 */
		Entity credits = new Entity("Credits");
		credits.setPosition(new Vector2f(218, 390));

		credits.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		entityManager.addEntity(idState, credits);

		/*
		 * Exit Game Button, setting position, image, events and action
		 * 
		 * On click need to close the game
		 */
		// Setting up entity
		Entity exitGame = new Entity("Exit Game");
		// setting position
		exitGame.setPosition(new Vector2f(218, 490));
		// setting image
		exitGame.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		// Event- Mouse relevant
		ANDEvent exitEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		// Adding Action - Quitting the game
		exitEvents.addAction(new QuitAction());
		// Adding eventComponent
		exitGame.addComponent(exitEvents);
		// adding entity in entityManager
		entityManager.addEntity(idState, exitGame);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		entityManager.updateEntities(gc, sb, delta);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sb, g);
		g.drawString("New Game", 190, 180);
		g.drawString("Highscore", 190, 280);
		g.drawString("Credits", 190, 380);
		g.drawString("Exit Game", 190, 480);

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return idState;
	}

}

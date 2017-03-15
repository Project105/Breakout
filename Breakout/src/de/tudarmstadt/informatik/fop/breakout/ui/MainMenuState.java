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
		entityManager.addEntity(MAINMENU_STATE, background);

		// Buttons
		/*
		 * New Game Button, setting position, image, events and action
		 * 
		 * On click need to go in GameplayState
		 */

		Entity nGame = new Entity("New Game");
		nGame.setPosition(new Vector2f(218, 190));

		nGame.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		ANDEvent nEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		nEvents.addAction(new ChangeStateInitAction(GAMEPLAY_STATE));
		nGame.addComponent(nEvents);

		entityManager.addEntity(MAINMENU_STATE, nGame);
		/*
		 * unused Button0
		 */
		Entity butt0 = new Entity("butt0");
		butt0.setPosition(new Vector2f(218, 290));

		butt0.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		entityManager.addEntity(MAINMENU_STATE, butt0);

		/*
		 * unused Button1
		 */
		Entity butt1 = new Entity("butt1");
		butt1.setPosition(new Vector2f(218, 390));

		butt1.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		entityManager.addEntity(MAINMENU_STATE, butt1);

		/*
		 * Exit Game Button, setting position, image, events and action
		 * 
		 * On click need to close the game
		 */

		Entity exitGame = new Entity("Exit Game");
		exitGame.setPosition(new Vector2f(218, 490));
		exitGame.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));

		ANDEvent exitEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());

		exitEvents.addAction(new QuitAction());
		exitGame.addComponent(exitEvents);
		entityManager.addEntity(MAINMENU_STATE, exitGame);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		entityManager.updateEntities(gc, sb, delta);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sb, g);
		g.drawString("New Game", 190, 180);
		g.drawString("Unused", 190, 280);
		g.drawString("Credits", 190, 380);
		g.drawString("Exit Game", 190, 480);

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return idState;
	}

}

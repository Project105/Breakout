package de.tudarmstadt.informatik.fop.breakout.ui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.entities.Stick;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;

/*
 * @Author Denis Andric
 */
public class GameplayState extends BasicGameState implements GameParameters {
	private int idState;
	private StateBasedEntityManager entityManager;

	protected List<BorderFactory> borders = new ArrayList<BorderFactory>();

	public GameplayState(int ID) {
		idState = ID;
		entityManager = StateBasedEntityManager.getInstance();
	}

	public void makeBorderList() {

		borders.add(new BorderFactory(BorderType.LEFT));
		borders.add(new BorderFactory(BorderType.RIGHT));
		borders.add(new BorderFactory(BorderType.TOP));
	}

	public void BorderListToEntity() {
		for (BorderFactory e : borders) {
			Entity border = e.createEntity();
			entityManager.addEntity(idState, border);
		}

	}

	protected boolean colideLeftBorder(Entity object) {
		return !object.collides(entityManager.getEntity(GAMEPLAY_STATE, LEFT_BORDER_ID));
	}

	protected boolean colideRightBorder(Entity object) {
		return !object.collides(entityManager.getEntity(GAMEPLAY_STATE, RIGHT_BORDER_ID));
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

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

		makeBorderList();
		BorderListToEntity();

		Stick stick = new Stick(STICK_ID);
		// default Position
		stick.setPosition(new Vector2f(400, 580));
		// adding image to entity
		stick.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));

		// left Movement of stick
		Event borderTouchLeft = new Event("leftBorderColide") {

			@Override
			protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {

				return colideLeftBorder(stick);
			}

		};
		KeyDownEvent leftDown = new KeyDownEvent(Input.KEY_LEFT);
		ANDEvent moveLeftFree = new ANDEvent(leftDown, borderTouchLeft);
		moveLeftFree.addAction(new MoveLeftAction(STICK_SPEED));
		stick.addComponent(moveLeftFree);

		// Right Movement of stick
		Event borderTouchRight = new Event("rightBorderColide") {

			@Override
			protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {

				return colideRightBorder(stick);
			}

		};
		KeyDownEvent rightDown = new KeyDownEvent(Input.KEY_RIGHT);
		ANDEvent moveRightFree = new ANDEvent(borderTouchRight, rightDown);
		moveRightFree.addAction(new MoveRightAction(STICK_SPEED));
		stick.addComponent(moveRightFree);

		entityManager.addEntity(idState, stick);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sbg, g);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID)
				.collides(entityManager.getEntity(GAMEPLAY_STATE, LEFT_BORDER_ID)), 100, 100);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID)
				.collides(entityManager.getEntity(GAMEPLAY_STATE, RIGHT_BORDER_ID)), 100, 125);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID).getPosition().getX(), 100, 150);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX(), 100, 175);

	}

	@Override
	public int getID() {
		return idState;
	}

}

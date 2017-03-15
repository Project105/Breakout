package de.tudarmstadt.informatik.fop.breakout.ui;

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
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.SetEntityPositionAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;

public class GameplayState extends BasicGameState implements GameParameters {
	private int idState;
	private StateBasedEntityManager entityManager;

	public GameplayState(int ID) {
		idState = ID;
		entityManager = StateBasedEntityManager.getInstance();
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
		
		
		
		Stick stick= new Stick(STICK_ID); 
		stick.setPosition(new Vector2f(400,580));
		stick.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));	
		entityManager.addEntity(GAMEPLAY_STATE, stick);
		
		Entity left = new Entity("left");
		KeyDownEvent leftDown = new KeyDownEvent(Input.KEY_LEFT);
		Action moveleft=new Action(){

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int delta, Component arg3) {
				if(entityManager.getEntity(GAMEPLAY_STATE, STICK_ID) instanceof Stick){
					Stick change =(Stick) entityManager.getEntity(GAMEPLAY_STATE, STICK_ID);
					
					change.setPosition(change.getNextPosition(change.getPosition(), -STICK_SPEED, 0, delta));
				
				}
			}
			
			
		};
		
		
		leftDown.addAction(moveleft);
		left.addComponent(leftDown);
		entityManager.addEntity(idState, left);
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sbg, g);

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return idState;
	}

}

package de.tudarmstadt.informatik.fop.breakout.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;

public class GameplayState extends BasicGameState implements GameParameters {
	private int idState;
	private  StateBasedEntityManager entityManager;
	
	public GameplayState(int ID){
		idState=ID;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		
		//Setting up background entity
		Entity background= new Entity(BACKGROUND_ID);
		//setting up position
		background.setPosition(new Vector2f(WINDOW_WIDTH/2,WINDOW_HEIGHT/2));
		//adding image to entity
		background.addComponent(new ImageRenderComponent(new Image(BACKGROUND_IMAGE)));
		//adding entity to entityManager
		entityManager.addEntity(idState, background);
		
		Entity escListener = new Entity("ESC_Listener");
		KeyPressedEvent esc=new KeyPressedEvent(Input.KEY_ESCAPE);
		esc.addAction(new ChangeStateAction(MAINMENU_STATE));
		escListener.addComponent(esc);
		entityManager.addEntity(idState, escListener);
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

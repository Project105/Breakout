package de.tudarmstadt.informatik.fop.breakout.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

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
		entityManager.addEntity(GAMEPLAY_STATE, background);
		
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

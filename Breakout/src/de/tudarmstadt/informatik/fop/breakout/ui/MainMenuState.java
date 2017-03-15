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
/*
 * @Author Denis Andric
 */
public class MainMenuState extends BasicGameState implements GameParameters {

	private int idState;
	private StateBasedEntityManager entityManager;
	
	public MainMenuState(int id){
		idState=id;
		entityManager=StateBasedEntityManager.getInstance();
	}
	
	@Override
	public void init(GameContainer sc, StateBasedGame sb) throws SlickException {
		
	
		
		
		
		
		
		//Setting position and image for background entity
		Entity background=new Entity("menu");
		background.setPosition(new Vector2f(WINDOW_WIDTH/2,WINDOW_HEIGHT/2));
		background.addComponent(new ImageRenderComponent(new Image(BACK_IMAGE)));
		entityManager.addEntity(MAINMENU_STATE, background);
		
		
		//Buttons
		/*
		 * New Game Button, setting position, image, events and action
		 * 
		 * On click need to go in GameplayState
		 */
		
		Entity nGame =new Entity("New Game");
		nGame.setPosition(new Vector2f(218,190));
		
		nGame.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		
		entityManager.addEntity(MAINMENU_STATE, nGame);
		
		/*
		 * Exit Game Button, setting position, image, events and action
		 * 
		 * On click need to close the game
		 */
		
		Entity exitGame= new Entity("Exit Game");
		exitGame.setPosition(new Vector2f(218,290));
		exitGame.addComponent(new ImageRenderComponent(new Image(ENTRY_IMAGE)));
		entityManager.addEntity(MAINMENU_STATE, exitGame);
	}

	

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		entityManager.updateEntities(gc, sb, delta);
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sb, g);
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return idState;
	}

}
